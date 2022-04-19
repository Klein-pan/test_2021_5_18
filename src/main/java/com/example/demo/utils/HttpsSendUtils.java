package com.example.demo.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.conf.RestTemplateConfig;
import com.example.demo.pojo.Token;
import com.example.demo.pojo.client.Client;
import com.example.demo.pojo.model.excption.UDException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
@Slf4j
public class HttpsSendUtils {

    /**
     *
     * @param prams 传递参数 JSON格式
     * @param table 具体上传表名
     * @param token
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static String sendHttps(String prams,String table,String token)  {
        //请求token
        String url = "https://10.152.160.25:61953/dataCenter/"+table;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer "+token);
        httpHeaders.set("dataCenter", table);
        httpHeaders.set("Content-Type", "application/json");
        //设置发送参数
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(prams, httpHeaders);
        //开始发送上传数据请求
        RestTemplate restTemplateHttps = null;
        try {
            restTemplateHttps = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UDException(-1,"上传数据发生错误","加密算法错误");
        } catch (KeyManagementException e) {
            e.printStackTrace();
            throw new UDException(-1,"上传数据发生错误","加密算法错误");
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new UDException(-1,"上传数据发生错误","无ssl认证资格");
        }
        //请求返回结果
        ResponseEntity<String> results = restTemplateHttps.exchange(url, HttpMethod.POST, stringHttpEntity, String.class);
        JSONObject json = JSON.parseObject(results.getBody());
        String response1 = json.getString("response");
        return response1;
    }
    public static String processingResults(String token,String actionCode, String orgCode, String sourceSystemCode, String id, String uploadDate)  {
        //设置获取结果URL
       // String url = "https://10.152.160.25:61953/dataCenter/dataReport?actionCode="+actionCode+"&orgCode="+orgCode+"&sourceSystemCode="+sourceSystemCode+"&id="+id+"&uploadDate="+uploadDate;
        String url = "https://bdatagtmed.gt.cn/uploadapi/dataCenter/dataReport?actionCode="+actionCode+"&orgCode="+orgCode+"&sourceSystemCode="+sourceSystemCode+"&id="+id+"&uploadDate="+uploadDate;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer "+token);
        httpHeaders.set("actionCode",actionCode);
        httpHeaders.set("sourceSystemCode",sourceSystemCode);
        httpHeaders.set("orgCode",orgCode);
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
        if (body.equals("{\"response\":{\"code\":\"-1\",\"desc\":\"未查询到数据处理结果，请检查参数是否正确\"}}")){
            for (int i = 1; i <4 ; i++) {
                long s = 5000l*i;
                try {
                    Thread.sleep(s);
                    if (i>1){
                        log.info("重试！！！发送第"+i+"次请求");
                    }else {
                        log.info("发送第"+i+"次请求");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                results = restTemplateHttps.exchange(url, HttpMethod.GET, stringHttpEntity, String.class);
                 body = results.getBody();
                 if (body.equals("{\"response\":{\"code\":\"-1\",\"desc\":\"未查询到数据处理结果，请检查参数是否正确\"}}")){
                     continue;
                 }else{
                     break;
                 }
            }
        }
        return body;
    }
    public static void getToken(RedisTemplate redisTemplate){
        String url = "https://bdatagtmed.gt.cn/tokenapi/oauth2/token?grant_type=client_credentials&client_id=JWB_lxdnsw_373459&client_secret=t270279bf8";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("grant_type","client_credentials");
        httpHeaders.set("client_id","JWB_lxdnsw_373459");
        httpHeaders.set("client_secret","t270279bf8");
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
    }
//    public static String  getToken2(RedisTemplate redisTemplate){
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        getToken(redisTemplate);
//           String token = (String) valueOperations.get("toekn");
//           Token token1 = JSONObject.parseObject(token, Token.class);
//            return token1.getAccess_token();
//        }
}

