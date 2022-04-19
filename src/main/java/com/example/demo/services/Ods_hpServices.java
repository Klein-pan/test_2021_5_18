package com.example.demo.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.sqlserver.Ods_hpMapper;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.Token;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.odsHp.Ods_hp_account_info;
import com.example.demo.pojo.model.odsHp.Ods_hp_account_list;
import com.example.demo.pojo.model.odsHp.Ods_hp_adm_info;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.HttpsSendUtils;
import com.example.demo.utils.JavaToMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class Ods_hpServices {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    Ods_hpMapper ods_hpMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MysqlServices mysqlServices;
    @Autowired
    RetryService retryService;
    @Autowired
    DataNotNullServices dataNotNullServices;
   // public List<QueryVo> getAmdInfo(String beginTime,String endTime) {
    public List<QueryVo> getAmdInfo() {
        List<Ods_hp_adm_info> amdInfo = null;
        try {
            amdInfo = ods_hpMapper.getAmdInfo();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (amdInfo.size()==0){
            throw new UDException(-1,"查询结果为空","ods_hp_adm_info条数为0");
        }
        amdInfo=dataNotNullServices.hpAdmInfos(amdInfo);
        List<QueryVo> queryVos = new ArrayList<>();
       amdInfoDataConduct(amdInfo, queryVos, "ods_hp_adm_info");

        return queryVos;
    }

   // public List<QueryVo> getAccountInfo(String biginTime, String endTime) {
    public List<QueryVo> getAccountInfo() {
        List<Ods_hp_account_info> accountInfo = null;
        try {
            accountInfo = ods_hpMapper.getAccountInfo();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (accountInfo.size()==0){
            throw new UDException(-1,"查询结果为空","ods_hp_account_info条数为0");
        }
        accountInfo=dataNotNullServices.hpaccountInfos(accountInfo);
        List<QueryVo> queryVos = new ArrayList<>();
        accountInfoDataConduct(accountInfo, queryVos, "ods_hp_account_info");

        return queryVos;
    }


    //public List<QueryVo> getAccountList(String beginTime,String endTime) {
    public List<QueryVo> getAccountList() {
        List<Ods_hp_account_list> accountList = null;
        try {
            accountList = ods_hpMapper.getAccountList();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (accountList.size()==0){
            throw new UDException(-1,"查询结果为空","ods_hp_account_list条数为0");
        }
        accountList=dataNotNullServices.hpaccountList(accountList);
        List<QueryVo> queryVos = new ArrayList<>();
        accountListDataConduct(accountList, queryVos, "ods_hp_account_list");

        return queryVos;
    }

    private void accountListDataConduct(List<Ods_hp_account_list> accountList, List<QueryVo> queryVos, String table) {
        int size = accountList.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        //Date date = new Date();
        boolean flag = true;
        //获取token
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        if (token1 == null) {
            retryService.getToken(redisTemplate);
            token = (String) valueOperations.get("toekn");
            token1 = JSONObject.parseObject(token, Token.class);
        }
        String access_token = token1.getAccess_token();

        int size1 = 0;
        int size2 = 0;
        int number = 0;
        int count = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501) {
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addAccountListMps(size1, size2, accountList, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addAccountListMps(size1, accountList.size(), accountList, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, accountList.size() - size1, format1, table);
                count = accountList.size() - size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            //发送数据返回数据上传结果
            if (number>num){
                retryService.getToken(redisTemplate);
                num+=100;
                token = (String) valueOperations.get("token");
                token1 = JSONObject.parseObject(token, Token.class);
            }
            log.info("开始发送第"+number+"次数据:"+table);
            //String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            String s = retryService.sendHttps(jsonString, table, token1.getAccess_token());
            int i = mysqlServices.addmysqlResponse(s, table,format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            String format = format1.substring(0, 10);
            String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            int n =  mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    private void addAccountListMps(int size1, int size2, List<Ods_hp_account_list> accountList, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(accountList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                log.info("java对象转化map失败");
                throw new UDException(-1,"java对象转化失败","java对象转化map集合失败");
            }
            maps.add(stringObjectMap);
        }
    }

    private void amdInfoDataConduct(List<Ods_hp_adm_info> amdInfo, List<QueryVo> queryVos, String table) {
        int size = amdInfo.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        //Date date = new Date();
        boolean flag = true;
        //获取token
        ValueOperations valueOperations = redisTemplate.opsForValue();
//         token = (String) valueOperations.get("token");
//         token1 = JSONObject.parseObject(token, Token.class);
            retryService.getToken(redisTemplate);
        String token = (String) valueOperations.get("toekn");
        Token token1 = JSONObject.parseObject(token, Token.class);
        String access_token = token1.getAccess_token();

        int size1 = 0;
        int size2 = 0;
        int number = 0;
        int count = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501) {
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addLocbednfoMps(size1, size2, amdInfo, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addLocbednfoMps(size1, amdInfo.size(), amdInfo, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, amdInfo.size() - size1, format1, table);
                count = amdInfo.size() - size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            if (number>num){
                retryService.getToken(redisTemplate);
                num+=100;
                token = (String) valueOperations.get("token");
                token1 = JSONObject.parseObject(token, Token.class);
            }
            log.info("开始发送第"+number+"次数据:"+table);
            //String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            String s = retryService.sendHttps(jsonString, table, token1.getAccess_token());
            int i = mysqlServices.addmysqlResponse(s, table,format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            String format = format1.substring(0, 10);
            String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    private void addLocbednfoMps(int size1, int size2, List<Ods_hp_adm_info> amdInfo, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(amdInfo.get(i));
            } catch (Exception e) {
                e.printStackTrace();
               log.info("java对象转化map失败");
               throw new UDException(-1,"java对象转化失败","java对象转化map集合失败");
            }
            maps.add(stringObjectMap);
        }

    }



    private void accountInfoDataConduct(List<Ods_hp_account_info> accountInfo, List<QueryVo> queryVos, String table) {
        int size = accountInfo.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        //Date date = new Date();
        boolean flag = true;
        //获取token
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        if (token1 == null) {
            retryService.getToken(redisTemplate);
            token = (String) valueOperations.get("toekn");
            token1 = JSONObject.parseObject(token, Token.class);
        }
        String access_token = token1.getAccess_token();

        int size1 = 0;
        int size2 = 0;
        int number = 0;
        int count = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501) {
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addAccountInfoMps(size1, size2, accountInfo, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addAccountInfoMps(size1, accountInfo.size(), accountInfo, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, accountInfo.size() - size1, format1, table);
                count = accountInfo.size() - size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            if (number>num){
                retryService.getToken(redisTemplate);
                num+=100;
                token = (String) valueOperations.get("token");
                token1 = JSONObject.parseObject(token, Token.class);
            }
            log.info("开始发送第"+number+"次数据:"+table);
            //String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            String s = retryService.sendHttps(jsonString, table, token1.getAccess_token());
            int i = mysqlServices.addmysqlResponse(s, table,format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            String format = format1.substring(0, 10);
            String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            int n =  mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    private void addAccountInfoMps(int size1, int size2, List<Ods_hp_account_info> accountInfo, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(accountInfo.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                log.info("java对象转化map失败");
                throw new UDException(-1,"java对象转化失败","java对象转化map集合失败");
            }
            maps.add(stringObjectMap);
        }
    }

}
