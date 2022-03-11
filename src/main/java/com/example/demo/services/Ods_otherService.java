package com.example.demo.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.Ods_otherMapper;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.Token;

import com.example.demo.pojo.model.Other.Ods_resource_hospbed_info;
import com.example.demo.pojo.model.Other.Ods_resource_locbed_info;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.utils.HttpsSendUtils;
import com.example.demo.utils.JavaToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Ods_otherService {
    @Autowired
    Ods_otherMapper ods_otherMapper;
    @Autowired
    RedisTemplate redisTemplate;
    public List<QueryVo> getLocbedInfo() throws Exception {
        List<Ods_resource_locbed_info> locbed_infos = ods_otherMapper.getLocbedInfo();

        List<QueryVo>queryVos = new ArrayList<>();
        locbeDataConduct(locbed_infos,queryVos,"ods_resource_locbed_info");

        return queryVos;
    }
    private void addLocbednfoMps(int size1, int size2,  List<Ods_resource_locbed_info> locbed_infos, List<Map> maps) throws Exception {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(locbed_infos.get(i));
            maps.add(stringObjectMap);
        }

    }
    /**
     * 数据处理
     * @param locbed_infos 查询表结果
     * @param queryVos 返回结果集
     * @throws Exception
     */
    private void locbeDataConduct(List<Ods_resource_locbed_info> locbed_infos, List<QueryVo> queryVos,String table) throws Exception {
        int size = locbed_infos.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        Date date = new Date();
        boolean flag = true;
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

        int size1 = 0;
        int size2 = 0;
        int number = 0;
        int count = 0;
        while (flag) {
            if (size > 501){
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addLocbednfoMps(size1, size2, locbed_infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,locbed_infos.size()-size1,date,table);
                count =500;
                size -= 500;
                number++;
            }else{
                addLocbednfoMps(size1, locbed_infos.size(), locbed_infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,locbed_infos.size()-size1,date,table);
                count=locbed_infos.size()-size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            //
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            String results = HttpsSendUtils.processingResults(access_token, table, "a", "121000004298902452", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(date);
            queryVo.setElementCount(count);
            queryVos.add(queryVo);
            maps.clear();
        }
    }

    public List<QueryVo> getHospbedInfo() throws Exception {
        List<Ods_resource_hospbed_info> hospbed_Infos = ods_otherMapper.getHospbedInfo();

        List<QueryVo>queryVos = new ArrayList<>();
        HospbedDataConduct(hospbed_Infos,queryVos,"ods_resource_hospbed_info");

        return queryVos;
    }
    private void addHospbedInfoMps(int size1, int size2,   List<Ods_resource_hospbed_info> hospbed_Infos, List<Map> maps) throws Exception {
        for (int i = size1; i < size2; i++) {
            Map<String, Object> stringObjectMap = JavaToMapUtils.objectToMap(hospbed_Infos.get(i));
            maps.add(stringObjectMap);
        }

    }
    /**
     * 数据处理
     * @param hospbed_Infos 查询表结果
     * @param queryVos 返回结果集
     * @throws Exception
     */
    private void HospbedDataConduct(List<Ods_resource_hospbed_info> hospbed_Infos, List<QueryVo> queryVos,String table) throws Exception {
        int size = hospbed_Infos.size();
        HashMap<String, List> stringObjectHashMap = new HashMap<>();
        List<Map> maps = new ArrayList<>();
        RespensBundle respensBundle = new RespensBundle();
        //存储data的参数
        Date date = new Date();
        boolean flag = true;
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

        int size1 = 0;
        int size2 = 0;
        int number = 0;
        int count = 0;
        while (flag) {
            if (size > 501){
                size1 = number * 500;
                size2 = (number + 1) * 500;
                addHospbedInfoMps(size1, size2, hospbed_Infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,hospbed_Infos.size()-size1,date,table);
                count =500;
                size -= 500;
                number++;
            }else{
                addHospbedInfoMps(size1, hospbed_Infos.size(), hospbed_Infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,hospbed_Infos.size()-size1,date,table);
                count=hospbed_Infos.size()-size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            String s = HttpsSendUtils.sendHttps(jsonString, table, access_token);
            //
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            String results = HttpsSendUtils.processingResults(access_token, table, "a", "121000004298902452", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(date);
            queryVo.setElementCount(count);
            queryVos.add(queryVo);
            maps.clear();
        }
    }
}

