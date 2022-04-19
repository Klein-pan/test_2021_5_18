package com.example.demo.controller;

import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.OdsOpServices;
import com.example.demo.services.Ods_otherService;
import com.example.demo.utils.JavaToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//其它业务接口
@RestController
@RequestMapping("/upload/data")
public class ods_otherControlle {
    @Autowired
    Ods_otherService ods_otherService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 上报科室床位
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_resource_locbed_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_otherService.getLocbedInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }

    /**
     * 上报全院床位
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_resource_hospbed_info", method = RequestMethod.GET)
    public List<QueryVo> getHospbedInfo(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_otherService.getHospbedInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }

    /**
     * 上报职工信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_user_base_info", method = RequestMethod.GET)
    public List<QueryVo> getBaseInfo(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_otherService.getBaseInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }

    /**
     * 上报医嘱明细
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_order_list", method = RequestMethod.GET)
    public List<QueryVo> getOrderList(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_otherService.getOrderList();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }

    /**
     * 上报科室字典
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_dim_loc", method = RequestMethod.GET)
    public List<QueryVo> getDimIoc(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_otherService.getDimIoc();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }

    /**
     * 上报收费项目明细记录表
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_chagedetail_list", method = RequestMethod.GET)
    public List<QueryVo> getChagedetailList(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_otherService.getChagedetailList();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");

    }
}
