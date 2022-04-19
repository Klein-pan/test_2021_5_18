package com.example.demo.controller;

import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.OdsOpServices;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import com.example.demo.utils.JavaToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//门诊业务
@RestController
@RequestMapping("/upload/data")
public class ods_OpController {
    //门急诊就诊
    @Autowired
    OdsOpServices odsOpServices;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 门急诊就诊信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_op_adm_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo(@RequestParam("token")String token) throws Exception {
   // public List<QueryVo> getInfo(/*@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,*/@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return odsOpServices.getAdmInfo();
            //return odsOpServices.getAdmInfo(/*biginTime,endTime*/);
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 门急诊诊疗费用结算明细记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_op_account_list", method = RequestMethod.GET)
    public List<QueryVo> getAccountList(@RequestParam("token")String token) throws Exception {
   // public List<QueryVo> getAccountList(@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            //return odsOpServices.getAccountList(biginTime,endTime);
            return odsOpServices.getAccountList();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 门急诊诊疗费用结算记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_op_account_info", method = RequestMethod.GET)
    //public List<QueryVo> getAccountInfo(/*@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,*/@RequestParam("token")String token) throws Exception {
    public List<QueryVo> getAccountInfo(@RequestParam("token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            //return odsOpServices.getAccountInfo(/*biginTime,endTime*/);
            return odsOpServices.getAccountInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

//    @RequestMapping(value = "ods_ip_adm_info", method = RequestMethod.GET)
//    public List<QueryVo> getInfo() throws Exception {
//        return odsOpServices.getAdmInfo();
//    }
}
