package com.example.demo.services;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.conf.RestTemplateConfig;
import com.example.demo.pojo.client.Client;
import com.example.demo.pojo.model.excption.UDException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NoHttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RetryService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    Client client;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


//    @Retryable(value = RestClientException.class, maxAttempts = 3,
//            backoff = @Backoff(delay = 5000L,multiplier = 2))
//    public String testEntity() throws NoHttpResponseException {
//        System.out.println("发起远程API请求:" + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
//
//        String url = "http://jsonplaceholder.typicode.com/postss/1";
//        ResponseEntity<String> responseEntity
//                = restTemplate.getForEntity(url, String.class);
//        System.out.println(responseEntity.getStatusCode());
//
//return null;
    //}

    /**
     * value：当指定异常发生时会进行重试 ,HttpClientErrorException是RestClientException的子类。
     * include：和value一样，默认空。如果 exclude也为空时，所有异常都重试
     * exclude：指定异常不重试，默认空。如果 include也为空时，所有异常都重试
     * maxAttemps：最大重试次数，默认3
     * backoff：重试等待策略，默认空
     * delay：指定重试的延时时间，默认为1000毫秒
     * multiplier：指定延迟的倍数，比如设置delay=5000，multiplier=2时，第一次重试为5秒后，第二次为10(5x2)秒，第三次为20(10x2)秒。
     * @param prams
     * @param table
     * @param token
     * @return
     */
   @Retryable(value = Exception.class, maxAttempts = 3,
            backoff = @Backoff(delay = 5000L,multiplier = 2))
    public  String sendHttps(String prams,String table,String token)  {
        log.info("发起上传数据请求:" + DATE_TIME_FORMATTER.format(LocalDateTime.now())+":"+table);
        String url = "https://bdatagtmed.gt.cn/uploadapi/dataCenter/"+table;
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
    @Retryable(value = Exception.class, maxAttempts = 3,
            backoff = @Backoff(delay = 5000L,multiplier = 2))
    public  String processingResults(String token,String actionCode, String orgCode, String sourceSystemCode, String id, String uploadDate)  {
        //设置获取结果URL
        // String url = "https://10.152.160.25:61953/dataCenter/dataReport?actionCode="+actionCode+"&orgCode="+orgCode+"&sourceSystemCode="+sourceSystemCode+"&id="+id+"&uploadDate="+uploadDate;
        log.info("发起查询数据处理结果请求:" + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
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
        if (body.equals("{\"response\":{\"code\":\"-1\",\"desc\":\"未查询到数据处理结果，请检查参数是否正确或在上传数据后等待一段时间再查询\"}}")){
            for (int i = 1; i <4 ; i++) {
                long s = 5000l*i;
                try {
                    Thread.sleep(s);
                    if (i>1){
                        log.info("重试！！！发送第"+i+"次请求");
                    }else {
                        log.info("重试！！！发送第"+i+"次请求");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                results = restTemplateHttps.exchange(url, HttpMethod.GET, stringHttpEntity, String.class);
                body = results.getBody();
                if (body.equals("{\"response\":{\"code\":\"-1\",\"desc\":\"未查询到数据处理结果，请检查参数是否正确或在上传数据后等待一段时间再查询\"}}")){
                    continue;
                }else{
                    break;
                }
            }
        }
        return body;
    }
    @Retryable(value = Exception.class, maxAttempts = 3,
            backoff = @Backoff(delay = 5000L,multiplier = 2))
    public  void getToken(RedisTemplate redisTemplate){
        String url = "https://bdatagtmed.gt.cn/tokenapi/oauth2/token?grant_type=client_credentials&client_id="+client.getClientId()+"&client_secret="+client.getClientSecret();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("grant_type","client_credentials");
        httpHeaders.set("client_id",client.getClientId());
        httpHeaders.set("client_secret",client.getClientSecret());
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
}
