package com.example.demo.services;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.OdsOpMaper;
import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.Token;
import com.example.demo.pojo.model.Ods_op_adm_info;
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
public class OdsOpServices {
    @Autowired
    OdsOpMaper odsOpMaper;
    @Autowired
    RedisTemplate redisTemplate;

    public List<QueryVo> getAdmInfo() throws Exception {
        List<Ods_op_adm_info> ods_op_adm_infos = odsOpMaper.getods_op_adm_info();
        List<QueryVo> queryVos = new ArrayList<>();
        dataConduct(ods_op_adm_infos,queryVos);
        return  queryVos;
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
    private void dataConduct(List<Ods_op_adm_info> ods_op_adm_infos, List<QueryVo> queryVos) throws Exception {
        int size = ods_op_adm_infos.size();
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
                addMps(size1, size2, ods_op_adm_infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,ods_op_adm_infos.size()-size1,date,"ods_op_adm_info");
                count =500;
                size -= 500;
                number++;
            }else{
                addMps(size1, ods_op_adm_infos.size(), ods_op_adm_infos, maps);
                //加密数据
                respensBundle= JavaToMapUtils.base64AndSm4AndSm3(maps,stringObjectHashMap,ods_op_adm_infos.size()-size1,date,"ods_op_adm_info");
                count=ods_op_adm_infos.size()-size1;
                flag = false;
            }

            //消息体转化JSON
            String jsonString = JSONObject.toJSONString(respensBundle);
            //发送数据返回数据上传结果
            String s = HttpsSendUtils.sendHttps(jsonString, "ods_op_adm_info", access_token);
            //
            //获取uuid
            String id = respensBundle.getBundle().getId();
            //获取date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            String results = HttpsSendUtils.processingResults(access_token, "ods_op_adm_info", "a", "121000004298902452", id, format);
            QueryVo queryVo = JSONObject.parseObject(results, QueryVo.class);
            queryVo.setDate(date);
            queryVo.setElementCount(count);
            queryVos.add(queryVo);
            maps.clear();
        }
    }



}