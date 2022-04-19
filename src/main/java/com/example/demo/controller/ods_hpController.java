package com.example.demo.controller;

import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.Ods_hpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//体检业务
@RestController
@RequestMapping("/upload/data")
public class ods_hpController {
    @Autowired
    Ods_hpServices ods_hpServices;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 体检就诊信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_hp_adm_info", method = RequestMethod.GET)
    public List<QueryVo> getAmdInfo( @RequestParam(value = "token")String token) throws Exception {
//    public List<QueryVo> getAmdInfo(@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_hpServices.getAmdInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 体检费用结算记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_hp_account_info", method = RequestMethod.GET)
    public List<QueryVo> getAccountInfo( @RequestParam(value = "token")String token) throws Exception {
    //public List<QueryVo> getAccountInfo(@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_hpServices.getAccountInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 体检费用结算明细记录
     * 体检费用结算明细记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_hp_account_list", method = RequestMethod.GET)
   // public List<QueryVo> getAccountList(@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,@RequestParam(value = "token")String token) throws Exception {
    public List<QueryVo> getAccountList(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_hpServices.getAccountList();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }
}
