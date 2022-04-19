package com.example.demo.services;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.sqlserver.OdsOpMaper;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.Token;
import com.example.demo.pojo.model.Ods_op_account_info;
import com.example.demo.pojo.model.Ods_op_account_list;
import com.example.demo.pojo.model.Ods_op_adm_info;
import com.example.demo.pojo.model.excption.UDException;
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
public class OdsOpServices {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
   private OdsOpMaper odsOpMaper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MysqlServices mysqlServices;
    @Autowired
    RetryService retryService;
    @Autowired
    DataNotNullServices  dataNotNullServices;
    /**
     * 门诊就诊
     * @return
     * @throws Exception
     */
   // public List<QueryVo> getAdmInfo(/*String biginTime, String endTime*/) throws Exception {
    public List<QueryVo> getAdmInfo() throws Exception {
        List<Ods_op_adm_info> ods_op_adm_infos = null;
        try {
            ods_op_adm_infos = odsOpMaper.getods_op_adm_info();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if (ods_op_adm_infos.size()==0){
            throw new UDException(-1,"查询数据库值为空","Ods_op_adm_info查询为空");
        }
        try {
            ods_op_adm_infos = dataNotNullServices.opAdmInfosNotNull(ods_op_adm_infos);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new UDException(-1,"类加载失败","Ods_op_account_list未找到");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        dataConduct(ods_op_adm_infos,queryVos,"ods_op_adm_info");
        return  queryVos;
}
    public List<QueryVo> getAccountList(/*String biginTime, String endTime*/) {
   // public List<QueryVo> getAccountList() {
        List<Ods_op_account_list> accountLists = null;
        try {
           // accountLists = odsOpMaper.getods_op_account_list(biginTime,endTime);
            accountLists = odsOpMaper.getods_op_account_list();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if(accountLists.size()==0){
            throw new UDException(-1,"查询数据库值为空","Ods_op_account_list查询为空");
        }
        try {
            accountLists = dataNotNullServices.opAccountListNotNull(accountLists);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new UDException(-1,"类加载失败","Ods_op_account_list未找到");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
           // throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        dataConductAccouuntLists(accountLists,queryVos,"ods_op_account_list");
        return queryVos;
    }
    //public List<QueryVo> getAccountInfo(/*String biginTime, String endTime*/) {
    public List<QueryVo> getAccountInfo() {
        List<Ods_op_account_info>account_infos = null;
        try {
            //account_infos = odsOpMaper.getAccountInfo(biginTime,endTime);
            account_infos = odsOpMaper.getAccountInfo();
        } catch (Exception e) {
            throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
        }
        if(account_infos.size()==0){
            throw new UDException(-1,"查询数据库值为空","Ods_op_account_list查询为空");
        }
        try {
            account_infos = dataNotNullServices.opAccountInfosNotNull(account_infos);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new UDException(-1,"类加载失败","Ods_op_account_list未找到");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // throw new UDException(-1,"Field加载失败","Ods_op_account_list属性未找到");
        }
        List<QueryVo> queryVos = new ArrayList<>();
        dataConductAccouuntInfos(account_infos,queryVos,"ods_op_account_info");
        return queryVos;
    }
    private void dataConductAccouuntInfos(List<Ods_op_account_info> adm_infos, List<QueryVo> queryVos, String table) {
        int size = adm_infos.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
       // Date date = new Date();
        boolean flag = true;
        ValueOperations valueOperations = redisTemplate.opsForValue();
       retryService.getToken(redisTemplate);
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        String access_token = token1.getAccess_token();
        int size1 = 0;
        int size2 = 0;
        int number = 0;
        Integer count = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501){
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addMpsAcountInfos(size1, size2, adm_infos, maps);
                //加密数据
                count =500;
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,count,format1,table);

                size -= 500;
                number++;
            }else{
                size1 = number * 500;
                addMpsAcountInfos(size1, adm_infos.size(), adm_infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,adm_infos.size()-size1,format1,table);
                count=adm_infos.size()-size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            //调用方法发送数据
            if (number>num){
               retryService.getToken(redisTemplate);
                num+=100;
                token = (String) valueOperations.get("token");
                token1 = JSONObject.parseObject(token, Token.class);
            }
            log.info("开始发送第"+number+"次数据:"+table);
            //String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            String s = retryService.sendHttps(jsonString, table, token1.getAccess_token());
            mysqlServices.addmysqlResponse(s,table,format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            //String results = HttpsSendUtils.processingResults(access_token, table, "121000004298902452", "1", id, format);
            String format = format1.substring(0,10);
            String results = retryService.processingResults(token1.getAccess_token(),table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
     }
    private void addMpsAcountInfos(int size1, int size2, List<Ods_op_account_info> adm_infos, List<Map> maps) {
        for (int i = size1; i <size2 ; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(adm_infos.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                throw new UDException(-1,"java对象转化失败","java对象转化map失败");
            }
            maps.add(stringObjectMap);
        }
    }
    /**
     *  处理数据并添加到LIST集合
     * @param size1 遍历开始索引
     * @param size2 便利结束索引
     * @param ods_op_adm_infos 查询表结果
     * @param maps  用于添加的集合
     * @throws Exception
     */
    private void addMps(int size1, int size2, List<Ods_op_adm_info> ods_op_adm_infos, List<Map> maps) throws Exception {
        for (int i = size1; i <size2 ; i++) {
                Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(ods_op_adm_infos.get(i));
                maps.add(stringObjectMap);
        }
    }
    /**
     * 数据处理
     * @param ods_op_adm_infos 查询表结果
     * @param queryVos 返回结果集
     * @throws Exception
     */
    private void dataConduct(List<Ods_op_adm_info> ods_op_adm_infos, List<QueryVo> queryVos,String table) throws Exception {
        int size = ods_op_adm_infos.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        //Date date = new Date();
        boolean flag = true;
        //获取token
        ValueOperations valueOperations = redisTemplate.opsForValue();
       retryService.getToken(redisTemplate);
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        String access_token = token1.getAccess_token();
        int size1 = 0;
        int size2 = 0;
        int number = 0;
        Integer count = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501){
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addMps(size1, size2, ods_op_adm_infos, maps);
                //加密数据
                count =500;
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,count,format1,table);

                size -= 500;
                number++;
            }else{
                size1 = number * 500;
                addMps(size1, ods_op_adm_infos.size(), ods_op_adm_infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,ods_op_adm_infos.size()-size1,format1,table);
                count=ods_op_adm_infos.size()-size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            log.info("开始发送第"+(number+1)+"次数据:"+table);
            //String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            String s = retryService.sendHttps(jsonString, table, token1.getAccess_token());
            mysqlServices.addmysqlResponse(s,table,format1);
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String format = sdf.format(date);
            String format = format1.substring(0, 10);
            String results = retryService.processingResults(token1.getAccess_token(),table, "121000004298902452", "1", id, format);
            // String results = HttpsSendUtils.processingResults(access_token, table, "121000004298902452", "1", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(format1);
            queryVo.setElementCount(count);
            mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }
    private void dataConductAccouuntLists(List<Ods_op_account_list> accountLists, List<QueryVo> queryVos, String table) {
        int size = accountLists.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        //Date date = new Date();
        boolean flag = true;
        //获取token
        ValueOperations valueOperations = redisTemplate.opsForValue();
       retryService.getToken(redisTemplate);
        String token = (String) valueOperations.get("token");
        Token token1 = JSONObject.parseObject(token, Token.class);
        String access_token = token1.getAccess_token();
        int size1 = 0;
        int size2 = 0;
        int number = 0;
        Integer count = 0;
        int num = 100;
        while (flag) {
//            long l = System.currentTimeMillis();
//            Date date = new Date(l);
            String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
            if (size >= 501){
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addMpsAcountLists(size1, size2, accountLists, maps);
                //加密数据
                count =500;
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,count,format1,table);

                size -= 500;
                number++;
            }else{
                size1 = number * 500;
                addMpsAcountLists(size1, accountLists.size(), accountLists, maps);
                for (Map map : maps) {
                    Set set = map.keySet();
                    Object o = map.get(set);
                    if (o instanceof String){
                        map.replace(set,"-");
                    }
                }
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,accountLists.size()-size1,format1,table);
                count=accountLists.size()-size1;
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
            mysqlServices.addmysqlResponse(s,table,format1);
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
            mysqlServices.addmysqlResult(queryVo);
            queryVos.add(queryVo);
            maps.clear();
        }
    }
    private void addMpsAcountLists(int size1, int size2, List<Ods_op_account_list> accountLists, List<Map> maps) {
        for (int i = size1; i <size2 ; i++) {
            Map<String, Object> stringObjectMap = null;
            try {
                stringObjectMap = JavaToMapUtils.objectToMap(accountLists.get(i));
            } catch (Exception e) {
                throw new UDException(-1,"实体类转化失败","实体类转化map集合失败");
            }
            maps.add(stringObjectMap);
        }
    }

}