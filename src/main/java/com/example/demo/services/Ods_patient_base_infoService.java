package com.example.demo.services;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.demo.conf.RestTemplateConfig;
import com.example.demo.mapper.sqlserver.Ods_patient_base_infoMapper;
import com.example.demo.pojo.*;
import com.example.demo.pojo.client.Client;
import com.example.demo.pojo.model.Ods_patient_base_info;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.HttpsSendUtils;
import com.example.demo.utils.JavaToMapUtils;
import com.example.demo.utils.SMUtils;
import lombok.extern.slf4j.Slf4j;
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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class Ods_patient_base_infoService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private Ods_patient_base_infoMapper ods_patient_base_infoMapper;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MysqlServices mysqlServices;
    @Autowired
    RetryService retryService;
    @Autowired
    Client client;

    //Date date = new Date();
    //??????????????????
    public List<QueryVo> getInfo(String beginTime, String endTime) throws Exception {
        List<Ods_patient_base_info> info = null;
        try {
            info = ods_patient_base_infoMapper.getInfo(beginTime,endTime);
        } catch (Exception e) {
            throw new UDException(-1,"?????????????????????",e.getCause().getMessage());
        }
        if (info.size()==0){
            throw new UDException(-1,"??????????????????","Ods_patient_base_info?????????0");
        }
        HashMap<String, String> stringStringHashMap = new LinkedHashMap<>();
        List<QueryVo> listRelust = new ArrayList<>();
        getToken();
        HashMap<String, List> stringMapHashMap = new LinkedHashMap<>();
        RespensBundle respensBundle = new RespensBundle();
        List<Map> maps = new LinkedList<>();
        int size = info.size();
        int i = size;//????????????
        //??????ListSize??????500
        int number = 1;//????????????
        int number2 = 0;//????????????
        boolean flag = true;//while??????????????????
        int size1 = 0;//???????????????????????????
        int size2 = 0;//??????if????????????????????????
        int couont = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (i >= 501) {
                size2= size>500*number?500*number:size;
                //????????????

                addMap1(size1,size2,stringStringHashMap,info,maps);
                //Too ????????????
                 respensBundle = base64AndSm4AndSm3(maps,stringMapHashMap,stringStringHashMap,size2-size1,format1);
                size2+=500;
                 number++;
                 number2++;
                size1= number2*500;
                i-=500;
                couont = 500;
            } else {

                //????????????
                addMap2(size1,info.size(), stringStringHashMap, info,maps);
                //Too ????????????
                 respensBundle = base64AndSm4AndSm3(maps, stringMapHashMap, stringStringHashMap,info.size()-size1,format1);
                number2++;
                couont = info.size()-size1;
                flag = false;
            }
            //????????????
            String s = JSONObject.toJSONString(respensBundle);
           if (number2>num){
               getToken();
               num+=100;
           }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String token = (String) valueOperations.get("token");
            Token token1 = JSONObject.parseObject(token, Token.class);
            log.info("???????????????"+number2+"?????????");
            String s1 = retryService.sendHttps(s, "ods_patient_base_info", token1.getAccess_token());
            //????????????????????????

            mysqlServices.addmysqlResponse(s1,"ods_patient_base_info",format1);

            maps.clear();

            //????????????????????????

            String format = format1.substring(0, 10);
            String results = HttpsSendUtils.processingResults(token1.getAccess_token(),"ods_patient_base_info","121000004298902452","1",respensBundle.getBundle().getId(),format);
           // processingResults("ods_patient_base_info","121000004298902452","1",respensBundle.getBundle().getId(),format);
            Date date1 = new Date();
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String format1 = sdf2.format(date1);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setElementCount(couont);
            queryVo.setDate(format1);
            mysqlServices.addmysqlResult(queryVo);
           // System.out.println(queryVo.toString());
            listRelust.add(queryVo);
        }
        return listRelust;
    }

    private String processingResults(String actionCode, String orgCode, String sourceSystemCode, String id, String uploadDate) {
        //??????????????????URL
        String url = "https://bdatagtmed.gt.cn/uploadapi/dataCenter/dataReport?actionCode="+actionCode+"&orgCode="+orgCode+"&sourceSystemCode="+sourceSystemCode+"&id="+id+"&uploadDate="+uploadDate;
        HttpHeaders httpHeaders = new HttpHeaders();
        //??????token
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        httpHeaders.set("Authorization","Bearer "+token1.getAccess_token());
        httpHeaders.set("actionCode",actionCode);
        httpHeaders.set("sourceSystemCode",sourceSystemCode);
        httpHeaders.set("id",id);
        httpHeaders.set("uploadDate",uploadDate);

        //??????????????????
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(new String(),httpHeaders);
        //??????????????????
        RestTemplate restTemplateHttps = null;
        try {
            restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (NoSuchAlgorithmException e) {
            System.out.print("???????????????????????????????????????");
            e.printStackTrace();
        } catch (KeyManagementException e) {
            System.out.print("???????????????????????????????????????");
            e.printStackTrace();
        } catch (KeyStoreException e) {
            System.out.print("???????????????????????????????????????");
            e.printStackTrace();
        }

        //??????????????????
        ResponseEntity<String> results = restTemplateHttps.exchange(url, HttpMethod.GET, stringHttpEntity, String.class);
        String body = results.getBody();
        return body;
    }

    //else??????
    private void addMap2(int size1, int size2, HashMap<String, String> stringStringHashMap, List<Ods_patient_base_info> info, List<Map> maps) throws Exception {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(info.get(i));
            maps.add(stringObjectMap);
        }

        }


    private RespensBundle base64AndSm4AndSm3(List<Map> maps, HashMap<String, List> stringMapHashMap, HashMap<String, String> stringStringHashMap,Integer count,String format) throws UnsupportedEncodingException {


        stringMapHashMap.put("data", maps);
        //???????????????
        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object obj, String s, Object v) {
                if (v == null)
                    return "";
                return v;
            }
        };
        String s2 = JSONObject.toJSONString(stringMapHashMap, filter, SerializerFeature.DisableCircularReferenceDetect);
        String base64 = encodeBese(s2);
        String sm4 = SMUtils.toSM4Str(base64, client.getKey());

        String sm3 = SMUtils.toSM3Str(sm4);

        Bundle bundle = new Bundle();
        RespensBundle respensBundle = new RespensBundle();
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setSignature(sm3);
        messageHeader.setActionCode("ods_patient_base_info");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        messageHeader.setTimestamp(sdf.format(date));
        messageHeader.setTimestamp(format);
        messageHeader.setElementCount(count);//???sql?????????????????????
        messageHeader.setOrgCode("121000004298902452");
        messageHeader.setSourceSystemCode("1");
        bundle.setMessageHeader(messageHeader);
        bundle.setId(UUID.randomUUID().toString());
        bundle.setMessageBody(sm4);
        respensBundle.setBundle(bundle);

        return respensBundle;
    }

    private void addMap1(int size1, int size2, HashMap<String, String> stringStringHashMap, List<Ods_patient_base_info> info, List<Map> maps) throws Exception {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(info.get(i));
            maps.add(stringObjectMap);
        }
        }

    private String encodeBese (String JSON) throws UnsupportedEncodingException {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            byte[] jsonBytes = JSON.getBytes("UTF-8");
            //??????
            String jsonBase64 = encoder.encodeToString(jsonBytes);
//            System.out.println("??????:" + jsonBase64);
//            //??????
//            byte[] decode = decoder.decode(jsonBase64);
//            System.out.println("?????????" + new String(decode, "UTF-8"));
            return jsonBase64;
        }

    private String sendHttps(String prams) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException { ;
        //??????token
        String url = "https://bdatagtmed.gt.cn/uploadapi/dataCenter/ods_patient_base_info";
        HttpHeaders httpHeaders = new HttpHeaders();
        ValueOperations valueOperations = redisTemplate.opsForValue();
       // Token token1 = JSONObject.parseObject(json.toJSONString(), Token.class);
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        httpHeaders.set("Authorization","Bearer "+token1.getAccess_token());
        httpHeaders.set("dataCenter","ods_patient_base_info");
        httpHeaders.set("Content-Type","application/json");
        //??????????????????
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(prams,httpHeaders);
        //??????????????????????????????
        RestTemplate restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        //??????????????????
        ResponseEntity<String> results = restTemplateHttps.exchange(url,HttpMethod.POST, stringHttpEntity, String.class);
        JSONObject json = JSON.parseObject(results.getBody());
       // System.out.println("???????????????????????????"+json);
        return json.getString("response");
//        ResponseEntity<Token> exchange = restTemplateHttps.exchange(url, HttpMethod.POST, stringHttpEntity, Token.class);
//        Token body = exchange.getBody();
//        System.out.println(body.toString());
      //  return null;
    }
    private void getToken(){
        String url = "https://bdatagtmed.gt.cn/tokenapi/oauth2/token?grant_type=client_credentials&client_id="+client.getClientId()+"&client_secret="+client.getClientSecret();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("grant_type","client_credentials");
        httpHeaders.set("client_id",client.getClientId());
        httpHeaders.set("client_secret",client.getClientSecret());
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(null,httpHeaders);
        RestTemplate restTemplateHttps = null;
        try {
            log.info("????????????token"+new Date());
            restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("??????token??????,token?????????Redis ?????????100??????"+new Date());
        }
        ResponseEntity<String> results = restTemplateHttps.exchange(url, HttpMethod.POST, stringHttpEntity, String.class);
        JSONObject json = JSON.parseObject(results.getBody());
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        log.info("??????token??????,token?????????Redis ?????????100??????"+new Date());
        valueOperations.set("token",json.toString(),100, TimeUnit.MINUTES);
//        String token = valueOperations.get("token");
//        System.out.println(token);
//        Token token1 = JSONObject.parseObject(json.toJSONString(), Token.class);
    }

}

