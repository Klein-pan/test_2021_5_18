package com.example.demo.services;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.mysql.MysqlResultMapper;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.pojo.model.vo.ResultData;
import com.example.demo.pojo.mysqlresult.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MysqlServices {
    @Autowired
    MysqlResultMapper mysqlResultMapper;
    /**
     * 存入上传返回结果集
     */
    public int addmysqlResponse(String response,String table,String format){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//       String format = sdf.format(date);
       Response response1 = JSONObject.parseObject(response, Response.class);
        int b = mysqlResultMapper.addResponse(response1.getId(), response1.getCode(), response1.getDesc(), format,table);
        return b;
    }

    public int addmysqlResult(QueryVo queryVo) {
        String format = queryVo.getDate();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String format = sdf.format(date);
        if (queryVo.getResponse().getCode().equals("0")){
            ResultData resultData = queryVo.getResponse().getData().get(0);
            int elementCount = queryVo.getElementCount();
            String elementCountString = elementCount+"";
            int b = mysqlResultMapper.addResult(resultData.getId(), resultData.getResultCode(), resultData.getResultDesc(),format,elementCountString);
            return b;
        }
        return 0;
    }
    public int addUpTimeStart (String startTime,String table){


        return mysqlResultMapper.addUpTimeStart(startTime,table);
    }
    public int addUpTimeEnd (String startTime,String table,String endTime,int elementcount){

        return mysqlResultMapper.addUpTimeEnd(startTime,table,endTime,elementcount+"");
    }
}
