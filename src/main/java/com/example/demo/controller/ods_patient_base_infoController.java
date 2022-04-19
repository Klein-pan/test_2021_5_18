package com.example.demo.controller;

import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import com.example.demo.services.Ods_patient_base_infoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/upload/data")
public class ods_patient_base_infoController {
    @Autowired
    Ods_patient_base_infoService ods_patient_base_infoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;

    /**
     * 上报患者基本信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_patient_base_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_patient_base_infoService.getInfo(beginTime, endTime);
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }

}
