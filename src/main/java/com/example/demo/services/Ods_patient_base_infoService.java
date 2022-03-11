package com.example.demo.services;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.demo.conf.RestTemplateConfig;
import com.example.demo.mapper.Ods_patient_base_infoMapper;
import com.example.demo.pojo.*;
import com.example.demo.pojo.model.Ods_patient_base_info;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.SMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class Ods_patient_base_infoService {
    @Autowired
    Ods_patient_base_infoMapper ods_patient_base_infoMapper;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    Date date = new Date();

    public List<QueryVo> getInfo() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, UnsupportedEncodingException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        Ods_patient_base_info ods_patient_base_info = new Ods_patient_base_info();
        List<com.example.demo.pojo.model.Ods_patient_base_info> info = ods_patient_base_infoMapper.getInfo();
        HashMap<String, String> stringStringHashMap = new LinkedHashMap<>();
        List<QueryVo> listRelust = new ArrayList<>();
        getToken();
        HashMap<String, List> stringMapHashMap = new LinkedHashMap<>();
        RespensBundle respensBundle = new RespensBundle();
        List<Map> maps = new LinkedList<>();
        int size = info.size();
        int i = size;//自减逻辑
        //如果ListSize大于500
        int number = 1;//自增逻辑
        int number2 = 0;//自增逻辑
        boolean flag = true;//while循环判断依据
        int size1 = 0;//用于初始遍历的开始
        int size2 = 0;//用于if结构遍历的结束。
        int couont = 0;
        while (flag) {
            if (i > 501) {
                size2= size>500*number?500*number:size;
                //遍历数据
                addMap1(size1,size2,stringStringHashMap,info,maps);
                //Too 加密数据
                 respensBundle = base64AndSm4AndSm3(maps,stringMapHashMap,stringStringHashMap,size2-size1);
                size2+=500;
                 number++;
                 number2++;
                size1= number2*500;
                i-=500;
                couont = 500;
            } else {

                //遍历数据
                addMap2(size1,info.size(), stringStringHashMap, info,maps);
                //Too 加密数据
                 respensBundle = base64AndSm4AndSm3(maps, stringMapHashMap, stringStringHashMap,info.size()-size1);
                number2++;
                couont = info.size()-size1;
                flag = false;
            }
            //发送数据
            String s = JSONObject.toJSONString(respensBundle);
            System.out.println("s:"+s);
            //调用方法发送数据
            String s1 = sendHttps(s);
            maps.clear();
            System.out.println("发送数据成功");
            //获取上传数据结果
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);

           String results = processingResults("ods_patient_base_info","a","121000004298902452",respensBundle.getBundle().getId(),format);
            Date date1 = new Date();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format1 = sdf2.format(date1);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setElementCount(couont);
            queryVo.setDate(new Date());
            System.out.println(queryVo.toString());
            listRelust.add(queryVo);
        }
        return listRelust;
    }

    private String processingResults(String actionCode, String orgCode, String sourceSystemCode, String id, String uploadDate) {
        //设置获取结果URL
        String url = "https://10.152.160.25:61953/dataCenter/dataReport?actionCode="+actionCode+"&orgCode="+orgCode+"&sourceSystemCode="+sourceSystemCode+"&id="+id+"&uploadDate="+uploadDate;
        HttpHeaders httpHeaders = new HttpHeaders();
        //添加token
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        httpHeaders.set("Authorization","Bearer "+token1.getAccess_token());
        httpHeaders.set("actionCode",actionCode);
        httpHeaders.set("sourceSystemCode",sourceSystemCode);
        httpHeaders.set("id",id);
        httpHeaders.set("uploadDate",uploadDate);

        //设置发送参数
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(new String(),httpHeaders);
        //开始发送请求
        RestTemplate restTemplateHttps = null;
        try {
            restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (NoSuchAlgorithmException e) {
            System.out.print("获取上传数据处理结果失败：");
            e.printStackTrace();
        } catch (KeyManagementException e) {
            System.out.print("获取上传数据处理结果失败：");
            e.printStackTrace();
        } catch (KeyStoreException e) {
            System.out.print("获取上传数据处理结果失败：");
            e.printStackTrace();
        }

        //请求返回结果
        ResponseEntity<String> results = restTemplateHttps.exchange(url, HttpMethod.GET, stringHttpEntity, String.class);
        String body = results.getBody();
        return body;
    }

    //else逻辑
    private void addMap2(int size1, int size2, HashMap<String, String> stringStringHashMap, List<Ods_patient_base_info> info, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            stringStringHashMap.put("patientinfo_01", info.get(i).getPatientinfo_01());
            stringStringHashMap.put("patientinfo_02", info.get(i).getPatientinfo_02());
            stringStringHashMap.put("patientinfo_03", info.get(i).getPatientinfo_03());
            stringStringHashMap.put("patientinfo_04", info.get(i).getPatientinfo_04());
            stringStringHashMap.put("patientinfo_05", info.get(i).getPatientinfo_05());
            stringStringHashMap.put("patientinfo_06", info.get(i).getPatientinfo_06());
            stringStringHashMap.put("patientinfo_07", info.get(i).getPatientinfo_07());
            stringStringHashMap.put("patientinfo_08", info.get(i).getPatientinfo_08());
            stringStringHashMap.put("patientinfo_09", info.get(i).getPatientinfo_09());
            stringStringHashMap.put("patientinfo_10", info.get(i).getPatientinfo_10());
            stringStringHashMap.put("patientinfo_11", info.get(i).getPatientinfo_11());
            stringStringHashMap.put("patientinfo_12", info.get(i).getPatientinfo_12());
            stringStringHashMap.put("patientinfo_13", info.get(i).getPatientinfo_13());
            stringStringHashMap.put("patientinfo_14", info.get(i).getPatientinfo_14());
            stringStringHashMap.put("patientinfo_15", info.get(i).getPatientinfo_15());
            stringStringHashMap.put("patientinfo_16", info.get(i).getPatientinfo_16());
            stringStringHashMap.put("patientinfo_17", info.get(i).getPatientinfo_17());
            stringStringHashMap.put("patientinfo_18", info.get(i).getPatientinfo_18());
            stringStringHashMap.put("patientinfo_19", info.get(i).getPatientinfo_19());
            stringStringHashMap.put("patientinfo_20", info.get(i).getPatientinfo_20());
            stringStringHashMap.put("patientinfo_21", info.get(i).getPatientinfo_21());
            stringStringHashMap.put("patientinfo_22", info.get(i).getPatientinfo_22());
            stringStringHashMap.put("patientinfo_23", info.get(i).getPatientinfo_23());
            stringStringHashMap.put("patientinfo_24", info.get(i).getPatientinfo_24());
            stringStringHashMap.put("patientinfo_25", info.get(i).getPatientinfo_25());
            stringStringHashMap.put("patientinfo_26", info.get(i).getPatientinfo_26());
            stringStringHashMap.put("patientinfo_27", info.get(i).getPatientinfo_27());
            stringStringHashMap.put("patientinfo_28", info.get(i).getPatientinfo_28());
            stringStringHashMap.put("patientinfo_29", info.get(i).getPatientinfo_29());
            stringStringHashMap.put("patientinfo_30", info.get(i).getPatientinfo_30());
            stringStringHashMap.put("patientinfo_31", info.get(i).getPatientinfo_31());
            stringStringHashMap.put("patientinfo_32", info.get(i).getPatientinfo_32());
            stringStringHashMap.put("patientinfo_33", info.get(i).getPatientinfo_33());
            stringStringHashMap.put("patientinfo_34", info.get(i).getPatientinfo_34());
            stringStringHashMap.put("patientinfo_35", info.get(i).getPatientinfo_35());
            stringStringHashMap.put("patientinfo_36", info.get(i).getPatientinfo_36());
            stringStringHashMap.put("patientinfo_37", info.get(i).getPatientinfo_37());
            stringStringHashMap.put("patientinfo_38", info.get(i).getPatientinfo_38());
            stringStringHashMap.put("patientinfo_39", info.get(i).getPatientinfo_39());
            stringStringHashMap.put("patientinfo_40", info.get(i).getPatientinfo_40());
            stringStringHashMap.put("patientinfo_41", info.get(i).getPatientinfo_41());
            stringStringHashMap.put("patientinfo_42", info.get(i).getPatientinfo_42());
            stringStringHashMap.put("patientinfo_43", info.get(i).getPatientinfo_43());
            stringStringHashMap.put("patientinfo_44", info.get(i).getPatientinfo_44());
            stringStringHashMap.put("patientinfo_45", info.get(i).getPatientinfo_45());
            stringStringHashMap.put("patientinfo_46", info.get(i).getPatientinfo_46());
            stringStringHashMap.put("patientinfo_47", info.get(i).getPatientinfo_47());
            stringStringHashMap.put("patientinfo_48", info.get(i).getPatientinfo_48());
            stringStringHashMap.put("patientinfo_49", info.get(i).getPatientinfo_49());
            stringStringHashMap.put("patientinfo_50", info.get(i).getPatientinfo_50());
            stringStringHashMap.put("patientinfo_51", info.get(i).getPatientinfo_51());
            stringStringHashMap.put("patientinfo_52", info.get(i).getPatientinfo_52());
            stringStringHashMap.put("patientinfo_53", info.get(i).getPatientinfo_53());
            stringStringHashMap.put("patientinfo_54", info.get(i).getPatientinfo_54());
            maps.add(stringStringHashMap);

        }
    }

    private RespensBundle base64AndSm4AndSm3(List<Map> maps, HashMap<String, List> stringMapHashMap, HashMap<String, String> stringStringHashMap,Integer count) throws UnsupportedEncodingException {


        stringMapHashMap.put("data", maps);
        //添加过滤器
        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object obj, String s, Object v) {
                if (v == null)
                    return "";
                return v;
            }
        };
        String s2 = JSONObject.toJSONString(stringMapHashMap, filter, SerializerFeature.DisableCircularReferenceDetect);

        System.out.println("s2" + s2);
        String base64 = encodeBese(s2);
        String sm4 = SMUtils.toSM4Str(base64, "p71fpgwczpa4vlwv");
        System.out.println("sm4:" + sm4);
        String sm3 = SMUtils.toSM3Str(sm4);
        System.out.println(sm3);
        Bundle bundle = new Bundle();
        RespensBundle respensBundle = new RespensBundle();
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setSignature(sm3);
        messageHeader.setActionCode("ods_patient_base_info");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageHeader.setTimestamp(sdf.format(date));
        messageHeader.setElementCount(count);//和sql查询条数同步。
        messageHeader.setOrgCode("a");
        messageHeader.setSourceSystemCode("121000004298902452");
        bundle.setMessageHeader(messageHeader);
        bundle.setId(UUID.randomUUID().toString());
        bundle.setMessageBody(sm4);
        respensBundle.setBundle(bundle);

        return respensBundle;
    }

    private void addMap1(int size1, int size2, HashMap<String, String> stringStringHashMap, List<Ods_patient_base_info> info, List<Map> maps) {
        for (int i = size1; i <size2; i++) {
            stringStringHashMap.put("patientinfo_01", info.get(i).getPatientinfo_01());
            stringStringHashMap.put("patientinfo_02", info.get(i).getPatientinfo_02());
            stringStringHashMap.put("patientinfo_03", info.get(i).getPatientinfo_03());
            stringStringHashMap.put("patientinfo_04", info.get(i).getPatientinfo_04());
            stringStringHashMap.put("patientinfo_05", info.get(i).getPatientinfo_05());
            stringStringHashMap.put("patientinfo_06", info.get(i).getPatientinfo_06());
            stringStringHashMap.put("patientinfo_07", info.get(i).getPatientinfo_07());
            stringStringHashMap.put("patientinfo_08", info.get(i).getPatientinfo_08());
            stringStringHashMap.put("patientinfo_09", info.get(i).getPatientinfo_09());
            stringStringHashMap.put("patientinfo_10", info.get(i).getPatientinfo_10());
            stringStringHashMap.put("patientinfo_11", info.get(i).getPatientinfo_11());
            stringStringHashMap.put("patientinfo_12", info.get(i).getPatientinfo_12());
            stringStringHashMap.put("patientinfo_13", info.get(i).getPatientinfo_13());
            stringStringHashMap.put("patientinfo_14", info.get(i).getPatientinfo_14());
            stringStringHashMap.put("patientinfo_15", info.get(i).getPatientinfo_15());
            stringStringHashMap.put("patientinfo_16", info.get(i).getPatientinfo_16());
            stringStringHashMap.put("patientinfo_17", info.get(i).getPatientinfo_17());
            stringStringHashMap.put("patientinfo_18", info.get(i).getPatientinfo_18());
            stringStringHashMap.put("patientinfo_19", info.get(i).getPatientinfo_19());
            stringStringHashMap.put("patientinfo_20", info.get(i).getPatientinfo_20());
            stringStringHashMap.put("patientinfo_21", info.get(i).getPatientinfo_21());
            stringStringHashMap.put("patientinfo_22", info.get(i).getPatientinfo_22());
            stringStringHashMap.put("patientinfo_23", info.get(i).getPatientinfo_23());
            stringStringHashMap.put("patientinfo_24", info.get(i).getPatientinfo_24());
            stringStringHashMap.put("patientinfo_25", info.get(i).getPatientinfo_25());
            stringStringHashMap.put("patientinfo_26", info.get(i).getPatientinfo_26());
            stringStringHashMap.put("patientinfo_27", info.get(i).getPatientinfo_27());
            stringStringHashMap.put("patientinfo_28", info.get(i).getPatientinfo_28());
            stringStringHashMap.put("patientinfo_29", info.get(i).getPatientinfo_29());
            stringStringHashMap.put("patientinfo_30", info.get(i).getPatientinfo_30());
            stringStringHashMap.put("patientinfo_31", info.get(i).getPatientinfo_31());
            stringStringHashMap.put("patientinfo_32", info.get(i).getPatientinfo_32());
            stringStringHashMap.put("patientinfo_33", info.get(i).getPatientinfo_33());
            stringStringHashMap.put("patientinfo_34", info.get(i).getPatientinfo_34());
            stringStringHashMap.put("patientinfo_35", info.get(i).getPatientinfo_35());
            stringStringHashMap.put("patientinfo_36", info.get(i).getPatientinfo_36());
            stringStringHashMap.put("patientinfo_37", info.get(i).getPatientinfo_37());
            stringStringHashMap.put("patientinfo_38", info.get(i).getPatientinfo_38());
            stringStringHashMap.put("patientinfo_39", info.get(i).getPatientinfo_39());
            stringStringHashMap.put("patientinfo_40", info.get(i).getPatientinfo_40());
            stringStringHashMap.put("patientinfo_41", info.get(i).getPatientinfo_41());
            stringStringHashMap.put("patientinfo_42", info.get(i).getPatientinfo_42());
            stringStringHashMap.put("patientinfo_43", info.get(i).getPatientinfo_43());
            stringStringHashMap.put("patientinfo_44", info.get(i).getPatientinfo_44());
            stringStringHashMap.put("patientinfo_45", info.get(i).getPatientinfo_45());
            stringStringHashMap.put("patientinfo_46", info.get(i).getPatientinfo_46());
            stringStringHashMap.put("patientinfo_47", info.get(i).getPatientinfo_47());
            stringStringHashMap.put("patientinfo_48", info.get(i).getPatientinfo_48());
            stringStringHashMap.put("patientinfo_49", info.get(i).getPatientinfo_49());
            stringStringHashMap.put("patientinfo_50", info.get(i).getPatientinfo_50());
            stringStringHashMap.put("patientinfo_51", info.get(i).getPatientinfo_51());
            stringStringHashMap.put("patientinfo_52", info.get(i).getPatientinfo_52());
            stringStringHashMap.put("patientinfo_53", info.get(i).getPatientinfo_53());
            stringStringHashMap.put("patientinfo_54", info.get(i).getPatientinfo_54());
            maps.add(stringStringHashMap);
        }
    }

    private String encodeBese (String JSON) throws UnsupportedEncodingException {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            byte[] jsonBytes = JSON.getBytes("UTF-8");
            //编码
            String jsonBase64 = encoder.encodeToString(jsonBytes);
//            System.out.println("转码:" + jsonBase64);
//            //解码
//            byte[] decode = decoder.decode(jsonBase64);
//            System.out.println("解码：" + new String(decode, "UTF-8"));
            return jsonBase64;
        }

    private String sendHttps(String prams) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        //String url = "https://10.152.160.25:61953/dataCenter/ods_patient_base_info";
        //请求token
        String url = "https://10.152.160.25:61953/dataCenter/ods_patient_base_info";
        HttpHeaders httpHeaders = new HttpHeaders();
        ValueOperations valueOperations = redisTemplate.opsForValue();
       // Token token1 = JSONObject.parseObject(json.toJSONString(), Token.class);
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        httpHeaders.set("Authorization","Bearer "+token1.getAccess_token());
        httpHeaders.set("dataCenter","ods_patient_base_info");
        httpHeaders.set("Content-Type","application/json");
        //设置发送参数
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(prams,httpHeaders);
        //开始发送上传数据请求
        RestTemplate restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        //请求返回结果
        ResponseEntity<String> results = restTemplateHttps.exchange(url,HttpMethod.POST, stringHttpEntity, String.class);
        JSONObject json = JSON.parseObject(results.getBody());
        System.out.println("返回上传数据结果："+json);
        return json.toString();
//        ResponseEntity<Token> exchange = restTemplateHttps.exchange(url, HttpMethod.POST, stringHttpEntity, Token.class);
//        Token body = exchange.getBody();
//        System.out.println(body.toString());
      //  return null;
    }
    private void getToken(){
        String url = "https://10.152.160.25:62749/oauth2/token?grant_type=client_credentials&client_id=WLE_zvrsuc_015745&client_secret=rp8q24myj3";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("grant_type","client_credentials");
        httpHeaders.set("client_id","WLE_zvrsuc_015745");
        httpHeaders.set("client_secret","rp8q24myj3");
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(null,httpHeaders);
        RestTemplate restTemplateHttps = null;
        try {
            System.out.println("开始获取token"+new Date());
            restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取token失败"+new Date());
        }
        ResponseEntity<String> results = restTemplateHttps.exchange(url, HttpMethod.POST, stringHttpEntity, String.class);
        JSONObject json = JSON.parseObject(results.getBody());
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        System.out.println("获取token成功,token缓存到Redis 有效期100分钟"+new Date());
        valueOperations.set("token",json.toString(),100, TimeUnit.MINUTES);
//        String token = valueOperations.get("token");
//        System.out.println(token);
//        Token token1 = JSONObject.parseObject(json.toJSONString(), Token.class);
    }

}

