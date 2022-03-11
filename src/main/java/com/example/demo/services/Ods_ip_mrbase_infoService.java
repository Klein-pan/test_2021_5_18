package com.example.demo.services;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.demo.conf.RestTemplateConfig;
import com.example.demo.mapper.Ods_ip_mrbase_infoMaper;
import com.example.demo.pojo.Bundle;
import com.example.demo.pojo.MessageHeader;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.Token;
import com.example.demo.pojo.model.Ods_ip_adm_info;
import com.example.demo.pojo.model.Ods_ip_mrbase_info;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_127;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_128;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_129;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_130;
import com.example.demo.pojo.model.Ods_op_adm_info;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.HttpsSendUtils;
import com.example.demo.utils.JavaToMapUtils;
import com.example.demo.utils.SMUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Ods_ip_mrbase_infoService {

 @Autowired
 Ods_ip_mrbase_infoMaper ods_ip_mrbase_infoMaper;


 @Autowired
 RestTemplate restTemplate;
 @Autowired
 RedisTemplate redisTemplate;

 public List<QueryVo> getMrbaseInfo() throws Exception {
  List<Ods_ip_mrbase_info> mrbaseInfo = ods_ip_mrbase_infoMaper.getMrbaseInfo();
  //创建返回参数
  List<QueryVo> resultList = new ArrayList<>();
  dataconduct(mrbaseInfo, resultList);
  return resultList;
 }

// private void addMps2(int size1, int size2, List<Ods_ip_mrbase_info> mrbaseInfo, ArrayList<Map> maps) throws Exception {
//  for (int j= size1; j <size2 ; j++) {
//   Map<String, Object> stringObjectMap = objectToMap(mrbaseInfo.get(j));
//
//   String id = (String) stringObjectMap.get("mrbase_04");
//
//   List<Mrbase_127> mrbase127byId = ods_ip_mrbase_infoMaper.getMrbase127byId(id);
//   stringObjectMap.replace("mrbase_127_s",mrbase127byId);
//
//   List<Mrbase_128> mrbase128byId = ods_ip_mrbase_infoMaper.getMrbase128byId(id);
//   stringObjectMap.replace("mrbase_128_s",mrbase128byId);
//
//   List<Mrbase_129> mrbase129byId = ods_ip_mrbase_infoMaper.getMrbase129byId(id);
//   stringObjectMap.replace("mrbase_129_s",mrbase129byId);
//
//   List<Mrbase_130> mrbase130byId = ods_ip_mrbase_infoMaper.getMrbase130byId(id);
//   stringObjectMap.replace("mrbase_130_s",mrbase130byId);
//
//   maps.add(stringObjectMap);
//  }
// }

 private void addMps1(int size1, int size2, List<Ods_ip_mrbase_info> mrbaseInfo, ArrayList<Map> maps) throws Exception {
  for (int j = size1; j < size2; j++) {
   Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(mrbaseInfo.get(j));
   String mrbase_01 = (String) stringObjectMap.get("mrbase_01");
   String mrbase_04 = (String) stringObjectMap.get("mrbase_04");

   Set<String> strings = stringObjectMap.keySet();
   for (String string : strings) {
    if (stringObjectMap.get(string)==null||Objects.isNull(stringObjectMap.get(string))){
     stringObjectMap.replace(string,"null");
    }
   }
//   if (mrbase_11==null|| StringUtils.isEmpty(mrbase_11)){
//    stringObjectMap.replace("mrbase_11","null");
//   }
//   if ((String)stringObjectMap.get("mrbase_45")==null||StringUtils.isEmpty((String)stringObjectMap.get("mrbase_45"))){
//    stringObjectMap.replace("mrbase_45","null");
//   }
//   if (stringObjectMap.get("mrbase_47")==null){
//    stringObjectMap.replace("mrbase_47","null");
//   }
//   if (stringObjectMap.get("mrbase_49")==null){
//    stringObjectMap.replace("mrbase_49","null");
//   }
//   if ((String)stringObjectMap.get("mrbase_55")==null||StringUtils.isEmpty((String)stringObjectMap.get("mrbase_55"))){
//    stringObjectMap.replace("mrbase_55","null");
//   }
//   if ((String)stringObjectMap.get("mrbase_75")==null||StringUtils.isEmpty((String)stringObjectMap.get("mrbase_75"))){
//    stringObjectMap.replace("mrbase_75","null");
//   }

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


 private void dataconduct(List<Ods_ip_mrbase_info> mrbaseInfo, List<QueryVo> resultList) throws Exception {
  HashMap<String, List> stringListHashMap = new HashMap<>();
  //存储data的参数
  Date date = new Date();
  //获取token
  ValueOperations valueOperations = redisTemplate.opsForValue();
  String token = (String) valueOperations.get("token");
  Token token1 = JSONObject.parseObject(token, Token.class);
  if (token1==null){
   HttpsSendUtils.getToken(redisTemplate);
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
  while (flag) {

   if (size > 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据

    //处理JSON数据
    addMps1(size1, size2, mrbaseInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, date, "ods_ip_mrbase_info");
    count = 500;
    //size自减
    size -= 500;
   } else {
    //处理JSON数据
    addMps1(size1, mrbaseInfo.size(), mrbaseInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, mrbaseInfo.size() - size1, date, "ods_ip_mrbase_info");

    count = mrbaseInfo.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String s = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据
   String s1 = HttpsSendUtils.sendHttps(s, "ods_ip_mrbase_info", access_token);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   String format = sdf.format(date);
   //获取上传数据结果
   String results = HttpsSendUtils.processingResults(access_token, "ods_ip_mrbase_info", "a", "121000004298902452", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(new Date());
   queryVo.setElementCount(count);
   resultList.add(queryVo);
   maps.clear();
  }
 }

 public List<QueryVo> getAdmInfo() throws Exception {
  List<Ods_ip_adm_info> admInfo = ods_ip_mrbase_infoMaper.getAdmInfo();
  //创建返回参数
  List<QueryVo> resultList = new ArrayList<>();
  HashMap<String, List> stringListHashMap = new HashMap<>();
  //存储data的参数
  Date date = new Date();
  //获取token
  ValueOperations valueOperations = redisTemplate.opsForValue();
  String token = (String) valueOperations.get("token");
  Token token1 = JSONObject.parseObject(token, Token.class);
  if (token1==null){
   HttpsSendUtils.getToken(redisTemplate);
   token = (String) valueOperations.get("toekn");
   token1 = JSONObject.parseObject(token, Token.class);
  }
  String access_token = token1.getAccess_token();
  ArrayList<Map> maps = new ArrayList<>();
  boolean flag = true;
  int size = admInfo.size();
  int size1 = 0;
  int size2 = 0;
  int number = 0;
  int count = 0;
  RespensBundle respensBundle = null;
  while (flag) {

   if (size > 501) {
    size1 = number * 500;
    size2 = (number + 1) * 500;
    //完善数据

    //处理JSON数据
    addAdmInfoMps(size1, size2, admInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, 500, date, "ods_ip_adm_info");
    count = 500;
    //size自减
    size -= 500;
   } else {
    //处理JSON数据
    addAdmInfoMps(size1, admInfo.size(), admInfo, maps);
    //Too 加密数据
    respensBundle = JavaToMapUtils.base64AndSm4AndSm3(maps, stringListHashMap, admInfo.size() - size1, date, "ods_ip_adm_info");

    count = admInfo.size() - size1;
    flag = false;
   }
   //消息体转化JSON
   String s = JSONObject.toJSONString(respensBundle);
   //调用方法发送数据
   String s1 = HttpsSendUtils.sendHttps(s, "ods_ip_adm_info", access_token);
   //获取uuid
   String id = respensBundle.getBundle().getId();
   //获取date
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   String format = sdf.format(date);
   //获取上传数据结果
   String results = HttpsSendUtils.processingResults(access_token, "ods_ip_adm_info", "a", "121000004298902452", id, format);
   QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
   queryVo.setDate(new Date());
   queryVo.setElementCount(count);
   resultList.add(queryVo);
   maps.clear();
  }
 return resultList;
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
}
