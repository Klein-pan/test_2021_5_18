package com.example.demo.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.sqlserver.Ods_otherMapper;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.Token;

import com.example.demo.pojo.model.Ods_chagedetail_list;
import com.example.demo.pojo.model.Ods_dim_loc;
import com.example.demo.pojo.model.Ods_order_list;
import com.example.demo.pojo.model.Ods_user_base_info;
import com.example.demo.pojo.model.Other.Ods_resource_hospbed_info;
import com.example.demo.pojo.model.Other.Ods_resource_locbed_info;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.HttpsSendUtils;
import com.example.demo.utils.JavaToMapUtils;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
public class Ods_otherService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
     private Ods_otherMapper ods_otherMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MysqlServices mysqlServices;
    @Autowired
    RetryService retryService;

    public List<QueryVo> getLocbedInfo() throws Exception {
        List<Ods_resource_locbed_info> locbed_infos = null;
        try {
            locbed_infos = ods_otherMapper.getLocbedInfo();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (locbed_infos.size()==0){
            throw new UDException(-1,"查询结果为空","Ods_resource_locbed_info条数为0");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        locbeDataConduct(locbed_infos, queryVos, "ods_resource_locbed_info");

        return queryVos;
    }

    public List<QueryVo> getDimIoc() {
        List<Ods_dim_loc> dim_locs = null;
        try {
            dim_locs = ods_otherMapper.getDimIoc();
        } catch (Exception e) {
          throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (dim_locs.size()==0){
            throw new UDException(-1,"查询结果为空","Ods_dim_loc条数为0");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        dimIocDataConduct(dim_locs, queryVos, "ods_dim_loc");
        return queryVos;
    }

    public List<QueryVo> getBaseInfo() {
        //sql暂未填写
        List<Ods_user_base_info> base_infos = null;
        try {
            base_infos = ods_otherMapper.getBaseInfo();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (base_infos.size()==0){
            throw new UDException(-1,"查询结果为空","Ods_user_base_info条数为0");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        BaseDataConduct(base_infos, queryVos, "ods_user_base_info");
        return queryVos;
    }
    public List<QueryVo> getChagedetailList() {
        List<Ods_chagedetail_list> chagedetail_lists = null;
        try {
            chagedetail_lists = ods_otherMapper.getChagedetailList();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (chagedetail_lists.size()==0){
            throw new UDException(-1,"查询结果为空","Ods_chagedetail_list条数为0");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        dataCountChagedetailList(chagedetail_lists, queryVos, "ods_chagedetail_list");
        return queryVos;
    }
    public List<QueryVo> getHospbedInfo() throws Exception {
        List<Ods_resource_hospbed_info> hospbed_Infos = null;
        try {
            hospbed_Infos = ods_otherMapper.getHospbedInfo();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (hospbed_Infos.size()==0){
            throw new UDException(-1,"查询结果为空","Ods_resource_hospbed_info条数为0");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        HospbedDataConduct(hospbed_Infos, queryVos, "ods_resource_hospbed_info");

        return queryVos;
    }

    public List<QueryVo> getOrderList() {
        List<Ods_order_list> ods_order_lists = null;
        try {
            ods_order_lists = ods_otherMapper.getOrderList();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (ods_order_lists.size()==0){
            throw new UDException(-1,"查询结果为空","Ods_order_list条数为0");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        dataConductOrderList(ods_order_lists,queryVos,"ods_order_list");
        return queryVos;
    }

    private void addLocbednfoMps(int size1, int size2, List<Ods_resource_locbed_info> locbed_infos, List<Map> maps) throws Exception {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(locbed_infos.get(i));
            maps.add(stringObjectMap);
        }

    }

    /**
     * 数据处理
     *
     * @param locbed_infos 查询表结果
     * @param queryVos     返回结果集
     * @throws Exception
     */
    private void locbeDataConduct(List<Ods_resource_locbed_info> locbed_infos, List<QueryVo> queryVos, String table) throws Exception {
        int size = locbed_infos.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
       // Date date = new Date();
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
        int num=100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501) {
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addLocbednfoMps(size1, size2, locbed_infos, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, locbed_infos.size() - size1, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addLocbednfoMps(size1, locbed_infos.size(), locbed_infos, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, locbed_infos.size() - size1, format1, table);
                count = locbed_infos.size() - size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
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
            //String results = HttpsSendUtils.processingResults(access_token, table, "121000004298902452", "1", id, format);
            String results = retryService.processingResults(token1.getAccess_token(),table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            int n =  mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }



    private void addHospbedInfoMps(int size1, int size2, List<Ods_resource_hospbed_info> hospbed_Infos, List<Map> maps) throws Exception {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(hospbed_Infos.get(i));
            maps.add(stringObjectMap);
        }

    }

    private void addBaseInfoMps(int size1, int size2, List<Ods_user_base_info> base_infos, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(base_infos.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                throw new UDException(-1, "java转化失败", "java对象转化map失败");
            }
            maps.add(stringObjectMap);
        }
    }

    /**
     * 数据处理
     *
     * @param hospbed_Infos 查询表结果
     * @param queryVos      返回结果集
     * @throws Exception
     */
    private void HospbedDataConduct(List<Ods_resource_hospbed_info> hospbed_Infos, List<QueryVo> queryVos, String table) throws Exception {
        int size = hospbed_Infos.size();
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
                addHospbedInfoMps(size1, size2, hospbed_Infos, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addHospbedInfoMps(size1, hospbed_Infos.size(), hospbed_Infos, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, hospbed_Infos.size() - size1, format1, table);
                count = hospbed_Infos.size() - size1;
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
            //
            int i = mysqlServices.addmysqlResponse(s, table,format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            String format = format1.substring(0,10);
           // String results = HttpsSendUtils.processingResults(access_token, table, "121000004298902452", "1", id, format);
            String results = retryService.processingResults(token1.getAccess_token(),table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setElementCount(count);
            queryVo.setDate(format1);
             mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }


    private void BaseDataConduct(List<Ods_user_base_info> base_infos, List<QueryVo> queryVos, String table) {
        int size = base_infos.size();
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
                addBaseInfoMps(size1, size2, base_infos, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addBaseInfoMps(size1, base_infos.size(), base_infos, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, base_infos.size() - size1, format1, table);
                count = base_infos.size() - size1;
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
            String results = retryService.processingResults(token1.getAccess_token(),table, "121000004298902452", "1", id, format);
            // String results = HttpsSendUtils.processingResults(access_token, table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            int n =  mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }


    private void dimIocDataConduct(List<Ods_dim_loc> dim_locs, List<QueryVo> queryVos, String table) {
        int size = dim_locs.size();
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
                addDimIocMps(size1, size2, dim_locs, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addDimIocMps(size1, dim_locs.size(), dim_locs, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, dim_locs.size() - size1, format1, table);
                count = dim_locs.size() - size1;
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
            int i = mysqlServices.addmysqlResponse(s, table, format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            String format = format1.substring(0, 10);
            String results = HttpsSendUtils.processingResults(access_token, table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            int n =  mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    private void addDimIocMps(int size1, int size2, List<Ods_dim_loc> dim_locs, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(dim_locs.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                throw new UDException(-1, "java转化失败", "java对象转化map失败");
            }
            maps.add(stringObjectMap);
        }
    }



    private void dataCountChagedetailList(List<Ods_chagedetail_list> chagedetail_lists, List<QueryVo> queryVos, String table) {
        int size = chagedetail_lists.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
       // Date date = new Date();
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
                addChagedetailList(size1, size2, chagedetail_lists, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addChagedetailList(size1, chagedetail_lists.size(), chagedetail_lists, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, chagedetail_lists.size() - size1, format1, table);
                count = chagedetail_lists.size() - size1;
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
            int i = mysqlServices.addmysqlResponse(s, table, format1);
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
            int i1 = mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    private void addChagedetailList(int size1, int size2, List<Ods_chagedetail_list> chagedetail_lists, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(chagedetail_lists.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                throw new UDException(-1, "java转化失败", "java对象转化map失败");
            }
            maps.add(stringObjectMap);
        }
    }



    private void dataConductOrderList(List<Ods_order_list> ods_order_lists, List<QueryVo> queryVos, String table) {
        int size = ods_order_lists.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
       // Date date = new Date();
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
                addOrderListList(size1, size2, ods_order_lists, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, 500, format1, table);
                count = 500;
                size -= 500;
                number++;
            } else {
                size1 = number * 500;
                addOrderListList(size1, ods_order_lists.size(), ods_order_lists, maps);
                //加密数据
                respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringObjectHashMap, ods_order_lists.size() - size1, format1, table);
                count = ods_order_lists.size() - size1;
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
            int i = mysqlServices.addmysqlResponse(s, table, format1);
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
            int i1 = mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    private void addOrderListList(int size1, int size2, List<Ods_order_list> ods_order_lists, List<Map> maps) {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(ods_order_lists.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                throw new UDException(-1, "java转化失败", "java对象转化map失败");
            }
            maps.add(stringObjectMap);
        }
    }
}

