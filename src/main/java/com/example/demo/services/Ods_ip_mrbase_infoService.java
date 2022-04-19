package com.example.demo.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.sqlserver.Ods_ip_mrbase_infoMaper;
import com.example.demo.pojo.*;
import com.example.demo.pojo.model.*;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_127;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_128;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_129;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_130;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.ods_adm_operation_info.Operation_76;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.HttpsSendUtils;
import com.example.demo.utils.JavaToMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class Ods_ip_mrbase_infoService {
 private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 @Autowired
 private Ods_ip_mrbase_infoMaper ods_ip_mrbase_infoMaper;
 @Autowired
 MysqlServices mysqlServices;
 @Autowired
 RestTemplate restTemplate;
 @Autowired
 RedisTemplate redisTemplate;
 @Autowired
 RetryService retryService;
 @Autowired
 DataNotNullServices dataNotNullServices;
 //public List<QueryVo> getMrbaseInfo(String beginTime,String endTime) throws Exception {
 public List<QueryVo> getMrbaseInfo() throws Exception {
  List<Ods_ip_mrbase_info> mrbaseInfo = null;
  try {
   mrbaseInfo = ods_ip_mrbase_infoMaper.getMrbaseInfo();
  } catch (Exception e) {
   throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
  }
  //创建返回参数
  if (mrbaseInfo.size()==0){
   throw new UDException(-1,"查询结果为空","Ods_ip_mrbase_info条数为零");
  }
  //mrbaseInfo=dataNotNullServices.ipMrbaseInfosNotNull(mrbaseInfo);
  List<QueryVo> resultList = new ArrayList<>();
  dataconduct(mrbaseInfo, resultList,"ods_ip_mrbase_info");
  return resultList;
 }
 public List<QueryVo> getAdmInfo() throws Exception {
  List<Ods_ip_adm_info> admInfo = null;
  try {
   admInfo = ods_ip_mrbase_infoMaper.getAdmInfo();
  } catch (Exception e) {
   throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
  }
  //创建返回参数
  if (admInfo.size()==0){
   throw new UDException(-1,"查询结果为空","Ods_ip_adm_info条数为零");
  }
  List<QueryVo> resultList = new ArrayList<>();
  dataConductAmdInfo(admInfo,resultList,"ods_ip_adm_info");
  return resultList;
 }
 public List<QueryVo> getAccountInfo() {
  //TOO DO  暂未填写sql；
  List<Ods_ip_account_info> account_infos= null;
  try {
   account_infos = ods_ip_mrbase_infoMaper.getAccountInfo();
  } catch (Exception e) {
   throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
  }
  //创建返回参数
  if (account_infos.size()==0){
   throw new UDException(-1,"查询结果为空","Ods_ip_account_info条数为零");
  }
  List<QueryVo> resultList = new ArrayList<>();
  dataconductAccountInfo(account_infos,resultList,"ods_ip_account_info");
  return resultList;
 }
 public List<QueryVo> getAccount_list() {
  //TOO DO  暂未填写sql；
  List<Ods_ip_account_list> account_lists = null;
  try {
   account_lists = ods_ip_mrbase_infoMaper.getAccount_list();
  } catch (Exception e) {
   throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
  }
  if (account_lists.size()==0){
   throw new UDException(-1,"查询结果为空","Ods_ip_account_list条数为零");
  }
  //创建返回参数
  List<QueryVo> resultList = new ArrayList<>();
  dataconductAccountList(account_lists,resultList,"ods_ip_account_list");
  return resultList;
 }
 public List<QueryVo> getOperatoionInfo() {
        //暂未填写SQL
  List<Ods_adm_operation_info> operation_infos = null;
  try {
   operation_infos = ods_ip_mrbase_infoMaper.getOperatoionInfo();
  } catch (Exception e) {
   throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
  }
  if (operation_infos.size()==0){
   throw new UDException(-1,"查询结果为空","Ods_adm_operation_info条数为零");
  }
        //创建返回参数
        List<QueryVo> resultList = new ArrayList<>();
      dataConductOperationInfos(operation_infos,resultList,"ods_adm_operation_info");
        return resultList;
 }
 public List<QueryVo> getPacsreportInfo() {
  List<Ods_adm_pacsreport_info> mrbaseInfo = null;
  try {
   mrbaseInfo = ods_ip_mrbase_infoMaper.getPacsreportInfo();
  } catch (Exception e) {
   throw new UDException(-1,"查询数据库错误",e.getCause().getMessage());
  }
  //创建返回参数
  if (mrbaseInfo.size()==0){
   throw new UDException(-1,"查询结果为空","Ods_adm_pacsreport_info条数为零");
  }
  List<QueryVo> resultList = new ArrayList<>();
  dataconductPacsreportInfo(mrbaseInfo, resultList,"ods_adm_pacsreport_info");
  return resultList;
 }
 private void addMps1(int size1, int size2, List<Ods_ip_mrbase_info> mrbaseInfo, ArrayList<Map> maps) throws Exception {
  for (int j = size1; j < size2; j++) {
   Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(mrbaseInfo.get(j));
   String mrbase_01 = (String) stringObjectMap.get("mrbase_01");
   String mrbase_04 = (String) stringObjectMap.get("mrbase_04");
   Set<String> strings = stringObjectMap.keySet();


   List<Mrbase_127> mrbase127byId = ods_ip_mrbase_infoMaper.getMrbase127byId(mrbase_01,mrbase_04);
   stringObjectMap.replace("mrbase_127_s", mrbase127byId);
   List<Mrbase_128> mrbase128byId = ods_ip_mrbase_infoMaper.getMrbase128byId(mrbase_01,mrbase_04);

   stringObjectMap.replace("mrbase_128_s", mrbase128byId);

   List<Mrbase_129> mrbase129byId = ods_ip_mrbase_infoMaper.getMrbase129byId(mrbase_01,mrbase_04);
   stringObjectMap.replace("mrbase_129_s", mrbase129byId);

   List<Mrbase_130> mrbase130byId = ods_ip_mrbase_infoMaper.getMrbase130byId(mrbase_01,mrbase_04);
   stringObjectMap.replace("mrbase_130_s", mrbase130byId);

   maps.add(stringObjectMap);
  }
 }
 private void dataconduct(List<Ods_ip_mrbase_info> mrbaseInfo, List<QueryVo> resultList,String table) throws Exception {
  HashMap<String, List> stringListHashMap = new HashMap<>();
  //存储data的参数

  //获取token
  ValueOperations valueOperations = redisTemplate.opsForValue();
  String token = (String) valueOperations.get("token");
  Token token1 = JSONObject.parseObject(token, Token.class);
  if (token1==null){
  retryService.getToken(redisTemplate);
   token = (String) valueOperations.get("toekn");
   token1 = JSONObject.parseObject(token, Token.class);
  }
  String access_token = token1.getAccess_token();
  ArrayList<Map> maps = new ArrayList<>();
  boolean flag = true;
  int size = mrbaseInfo.size();
  int size1 = 0;
  int size2 = 0;
  int number = 0;
  int count = 0;
  RespensBundle respensBundle = null;
  int num=100;
  while (flag) {
//   long l = System.currentTimeMillis();
//   Date date = new Date(l);
   String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
   if (size >= 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据
    //处理JSON数据
    addMps1(size1, size2, mrbaseInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, format1, table);
    count = 500;
    //size自减
    size -= 500;
    number++;
   } else {
    size1 = number * 500;
    //处理JSON数据
    addMps1(size1, mrbaseInfo.size(), mrbaseInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, mrbaseInfo.size() - size1, format1, table);

    count = mrbaseInfo.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String s = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据
   if (number>num){
   retryService.getToken(redisTemplate);
    num+=100;
   }
    token = (String) valueOperations.get("token");
    token1 = JSONObject.parseObject(token, Token.class);
   log.info("开始发送第"+number+"次数据");
   String s1 = retryService.sendHttps(s, table, token1.getAccess_token());
   //String s1 = HttpsSendUtils.sendHttps(s, table, access_token);
   int i = mysqlServices.addmysqlResponse(s1, table, format1);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//   String format = sdf.format(date);
   String format = format1.substring(0, 10);
   //获取上传数据结果
   String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(format1);
   queryVo.setElementCount(count);
   mysqlServices.addmysqlResult(queryVo);
   resultList.add(queryVo);
   maps.clear();
  }
 }
private void dataConductAmdInfo(List<Ods_ip_adm_info> admInfo,List<QueryVo> resultList,String table) throws Exception {
 int size = admInfo.size();
 HashMap<String, List> stringObjectHashMap = new HashMap<>();
 List<Map> maps = new ArrayList<>();
 RespensBundle respensBundle = new RespensBundle();
 //存储data的参数

 boolean flag = true;
 //获取token
 ValueOperations valueOperations = redisTemplate.opsForValue();
 String token = (String) valueOperations.get("token");
 Token token1 = JSONObject.parseObject(token, Token.class);
 if (token1==null){
 retryService.getToken(redisTemplate);
  token = (String) valueOperations.get("toekn");
  token1 = JSONObject.parseObject(token, Token.class);
 }
 String access_token = token1.getAccess_token();

 int size1 = 0;
 int size2 = 0;
 int number = 0;
 int count = 0;
 int num =100;
 while (flag) {
//  long l = System.currentTimeMillis();
//  Date date = new Date(l);
  String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
  if (size >= 501){
   size1 = number * 500;
   size2 = (number + 1) * 500;
   addAdmInfoMps(size1, size2, admInfo, maps);
   //加密数据
   respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,500,format1,table);
   count =500;
   size -= 500;
   number++;
  }else{
   size1 = number * 500;
   addAdmInfoMps(size1, admInfo.size(), admInfo, maps);
   //加密数据
   respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,admInfo.size()-size1,format1,table);
   count=admInfo.size()-size1;
   flag = false;
  }

  //消息体转化JSON
  String jsonString = JSONObject.toJSONString(respensBundle);
  //发送数据返回数据上传结果
  if (number>num){
  retryService.getToken(redisTemplate);
   num+=100;
  }
  token = (String) valueOperations.get("token");
  token1 = JSONObject.parseObject(token, Token.class);
  log.info("开始发送第"+number+"次数据");
  String s = retryService.sendHttps(jsonString, table, token1.getAccess_token());
  int i = mysqlServices.addmysqlResponse(s, table, format1);
  //获取uuid
  String id = respensBundle.getBundle().getId();
  //获取date
//  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//  String format = sdf.format(date);
  String format = format1.substring(0, 10);
  String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
  QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
  queryVo.setDate(format1);
  queryVo.setElementCount(count);
  int i1 = mysqlServices.addmysqlResult(queryVo);
  resultList.add(queryVo);
  maps.clear();
 }
}
 /**
  *  处理数据并添加到LIST集合
  * @param size1 遍历开始索引
  * @param size2 便利结束索引
  * @param admInfo 查询表结果
  * @param maps  用于添加的集合
  * @throws Exception
  */
 private void addAdmInfoMps(int size1, int size2, List<Ods_ip_adm_info> admInfo, List<Map> maps) throws Exception {
  for (int i = size1; i <size2 ; i++) {
   Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(admInfo.get(i));
   maps.add(stringObjectMap);
  }
 }
 private void dataconductAccountInfo(List<Ods_ip_account_info> account_infos, List<QueryVo> resultList,String table) {
  HashMap<String, List> stringListHashMap = new HashMap<>();
  //存储data的参数
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
  ArrayList<Map> maps = new ArrayList<>();
  boolean flag = true;
  int size = account_infos.size();
  int size1 = 0;
  int size2 = 0;
  int number = 0;
  int count = 0;
  int num = 100;
  RespensBundle respensBundle = null;
  while (flag) {
//   long l = System.currentTimeMillis();
//   Date date = new Date(l);
   String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
   if (size >= 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据
    //处理JSON数据
    addAccountInfoMps(size1, size2, account_infos, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, format1, table);
    count = 500;
    //size自减
    size -= 500;
    number++;
   } else {
    size1 = number * 500;
    //处理JSON数据
    addAccountInfoMps(size1, account_infos.size(), account_infos, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, account_infos.size() - size1, format1, table);

    count = account_infos.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String JsonString = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据

   if (number>num){
   retryService.getToken(redisTemplate);
    num+=100;
   }
   token = (String) valueOperations.get("token");
   token1 = JSONObject.parseObject(token, Token.class);
   log.info("开始发送第"+number+"次数据");
   String s = retryService.sendHttps(JsonString, table, token1.getAccess_token());
   int i = mysqlServices.addmysqlResponse(s, table, format1);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//   String format = sdf.format(date);
   String format = format1.substring(0, 10);
   //获取上传数据结果
   String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(format1);
   queryVo.setElementCount(count);
   int i1 = mysqlServices.addmysqlResult(queryVo);
   resultList.add(queryVo);
   maps.clear();

  }
 }
 private void addAccountInfoMps(int size1, int size2, List<Ods_ip_account_info> account_infos, List<Map> maps) {
  for (int i = size1; i <size2 ; i++) {
   Map<String, Object> stringObjectMap = null;
   try {
    stringObjectMap = JavaToMapUtils.objectToMap(account_infos.get(i));
   } catch (Exception e) {
    e.printStackTrace();
    throw new UDException(-1,"对象转化错误","对象转map发送错误");
   }
   maps.add(stringObjectMap);
  }
 }
 private void dataconductAccountList(List<Ods_ip_account_list> account_lists, List<QueryVo> resultList,String table) {
  HashMap<String, List> stringListHashMap = new HashMap<>();

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
  ArrayList<Map> maps = new ArrayList<>();
  boolean flag = true;
  int size = account_lists.size();
  int size1 = 0;
  int size2 = 0;
  int number = 0;
  int count = 0;
  int num = 100;
  RespensBundle respensBundle = null;
  while (flag) {
//   long l = System.currentTimeMillis();
//   Date date = new Date(l);
   String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
   if (size >= 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据
    //处理JSON数据
    addAccountListMaps(size1, size2, account_lists, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, format1, table);
    count = 500;
    //size自减
    size -= 500;
    number++;
   } else {
    size1 = number * 500;
    //处理JSON数据
    addAccountListMaps(size1, account_lists.size(), account_lists, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, account_lists.size() - size1, format1, table);
    count = account_lists.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String s = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据
   if (number>num){
   retryService.getToken(redisTemplate);
    num+=100;
    token = (String) valueOperations.get("token");
    token1 = JSONObject.parseObject(token, Token.class);
   }
   log.info("开始发送第"+number+"次数据");
   //String s1 = HttpsSendUtils.sendHttps(s, table, token1.);
   String s1 = retryService.sendHttps(s, table, token1.getAccess_token());
   int i = mysqlServices.addmysqlResponse(s1, table, format1);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//   String format = sdf.format(date);
   String format = format1.substring(0, 10);
   //获取上传数据结果
   String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(format1);
   queryVo.setElementCount(count);
   int i1 = mysqlServices.addmysqlResult(queryVo);
   resultList.add(queryVo);
   maps.clear();

  }

 }
 private void addAccountListMaps(int size1, int size2,  List<Ods_ip_account_list> account_lists, List<Map> maps) {
  for (int i = size1; i <size2 ; i++) {
   Map<String, Object> stringObjectMap = null;
   try {
    stringObjectMap = JavaToMapUtils.objectToMap(account_lists.get(i));
   } catch (Exception e) {
    e.printStackTrace();
    throw new UDException(-1,"对象转化错误","对象转map发送错误");
   }
   maps.add(stringObjectMap);
  }
 }
 private void dataConductOperationInfos( List<Ods_adm_operation_info> operation_infos ,List<QueryVo> resultList,String table){
  HashMap<String, List> stringListHashMap = new HashMap<>();
  //存储data的参数
 // Date date = new Date();
  //获取token
  ValueOperations valueOperations = redisTemplate.opsForValue();
  String token = (String) valueOperations.get("token");
  Token token1 = JSONObject.parseObject(token, Token.class);
  if (token1==null){
  retryService.getToken(redisTemplate);
   token = (String) valueOperations.get("toekn");
   token1 = JSONObject.parseObject(token, Token.class);
  }
  String access_token = token1.getAccess_token();
  ArrayList<Map> maps = new ArrayList<>();
  boolean flag = true;
  int size = operation_infos.size();
  int size1 = 0;
  int size2 = 0;
  int number = 0;
  int count = 0;
  int num = 100;
  RespensBundle respensBundle = null;
  while (flag) {
//   long l = System.currentTimeMillis();
//   Date date = new Date(l);
   String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
   if (size >= 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据

    //处理JSON数据
    addOperationInfosMaps(size1, size2, operation_infos, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, format1, table);
    count = 500;
    //size自减
    size -= 500;
    number++;
   } else {
    size1 = number * 500;
    //处理JSON数据
    addOperationInfosMaps(size1, operation_infos.size(), operation_infos, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, operation_infos.size() - size1, format1, table);

    count = operation_infos.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String s = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据
   if (number>num){
   retryService.getToken(redisTemplate);
    num+=100;
    token = (String) valueOperations.get("token");
    token1 = JSONObject.parseObject(token, Token.class);
   }
   log.info("开始发送第"+number+"次数据");
   //String s1 = HttpsSendUtils.sendHttps(s, table, token1.);
   String s1 = retryService.sendHttps(s, table, token1.getAccess_token());
   //String s1 = HttpsSendUtils.sendHttps(s, table, access_token);
   int i = mysqlServices.addmysqlResponse(s1, table, format1);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//   String format = sdf.format(date);
   String format = format1.substring(0, 10);
   //获取上传数据结果
   String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(format1);
   queryVo.setElementCount(count);
   int i1 = mysqlServices.addmysqlResult(queryVo);
   resultList.add(queryVo);
   maps.clear();
  }
 }
 private void addOperationInfosMaps(int size1, int size2,List<Ods_adm_operation_info> operation_infos, List<Map> maps){
  for (int j = size1; j < size2; j++) {
   Map<String, Object> stringObjectMap = null;
   try {
    stringObjectMap = JavaToMapUtils.objectToMap(operation_infos.get(j));
   } catch (Exception e) {
    e.printStackTrace();
    throw new UDException(-1,"java转化失败","java转化map集合失败");
   }
   String operation_01 = (String) stringObjectMap.get("operation_01");
   String operation_03 = (String) stringObjectMap.get("operation_03");

   Set<String> strings = stringObjectMap.keySet();
   List<Operation_76> operation_76byId = ods_ip_mrbase_infoMaper.getOperation_76(operation_01,operation_03);
   stringObjectMap.replace("operation_76_s", operation_76byId);
   maps.add(stringObjectMap);
  }
 }
 private void dataconductPacsreportInfo(List<Ods_adm_pacsreport_info> mrbaseInfo, List<QueryVo> resultList, String table) {
  HashMap<String, List> stringListHashMap = new HashMap<>();
  //存储data的参数
  //Date date = new Date();
  //获取token
  ValueOperations valueOperations = redisTemplate.opsForValue();
  String token = (String) valueOperations.get("token");
  Token token1 = JSONObject.parseObject(token, Token.class);
  if (token1==null){
  retryService.getToken(redisTemplate);
   token = (String) valueOperations.get("toekn");
   token1 = JSONObject.parseObject(token, Token.class);
  }
  String access_token = token1.getAccess_token();
  ArrayList<Map> maps = new ArrayList<>();
  boolean flag = true;
  int size = mrbaseInfo.size();
  int size1 = 0;
  int size2 = 0;
  int number = 0;
  int count = 0;
  int num = 100;
  RespensBundle respensBundle = null;
  while (flag) {
//   long l = System.currentTimeMillis();
//   Date date = new Date(l);
   String format1 = DATE_TIME_FORMATTER.format(LocalDateTime.now());
    if (size >= 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据

    //处理JSON数据
    addPacsreportInfoMaps(size1, size2, mrbaseInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, format1, table);
    count = 500;
    //size自减
    size -= 500;
    number++;
   } else {
    size1 = number * 500;
    //处理JSON数据
    addPacsreportInfoMaps(size1, mrbaseInfo.size(), mrbaseInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, mrbaseInfo.size() - size1, format1, table);

    count = mrbaseInfo.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String s = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据

   if (number>num){
   retryService.getToken(redisTemplate);
    num+=100;
    token = (String) valueOperations.get("token");
    token1 = JSONObject.parseObject(token, Token.class);
   }
   log.info("开始发送第"+number+"次数据");
   //String s1 = HttpsSendUtils.sendHttps(s, table, token1.);
   String s1 = retryService.sendHttps(s, table, token1.getAccess_token());
   int i = mysqlServices.addmysqlResponse(s1, table, format1);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
//   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//   String format = sdf.format(date);
   String format = format1.substring(0, 10);
   //获取上传数据结果
   String results = retryService.processingResults(access_token, table, "121000004298902452", "1", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(format1);
   queryVo.setElementCount(count);
   int i1 = mysqlServices.addmysqlResult(queryVo);
   resultList.add(queryVo);
   maps.clear();
  }
 }
 private void addPacsreportInfoMaps(int size1, int size2, List<Ods_adm_pacsreport_info> mrbaseInfo, ArrayList<Map> maps) {

  for (int i = size1; i <size2 ; i++) {
   Map<String, Object> stringObjectMap = null;
   try {
    stringObjectMap = JavaToMapUtils.objectToMap(mrbaseInfo.get(i));
   } catch (Exception e) {
    e.printStackTrace();
    throw new UDException(-1,"对象转化错误","对象转map发送错误");
   }
   maps.add(stringObjectMap);
  }
 }
}
