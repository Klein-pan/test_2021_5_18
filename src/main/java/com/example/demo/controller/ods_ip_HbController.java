package com.example.demo.controller;

import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.model.excption.UDException;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import com.example.demo.services.Ods_patient_base_infoService;
import com.example.demo.utils.JavaToMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//住院业务接口
@RestController
@RequestMapping("/upload/data")
public class ods_ip_HbController {

    @Autowired
    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 住院病案首页信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_ip_mrbase_info", method = RequestMethod.GET)
    //public List<QueryVo> getInfo(@RequestParam(value = "beginTime") String biginTime,@RequestParam(value = "endTime") String endTime,@RequestParam(value = "token")String token) throws Exception {
    public List<QueryVo> getInfo(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_ip_mrbase_infoService.getMrbaseInfo();
        }
       throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 住院就诊信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_ip_adm_info", method = RequestMethod.GET)
    public List<QueryVo> getAdmInfo(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_ip_mrbase_infoService.getAdmInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 住院诊疗费用结算记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_ip_account_info", method = RequestMethod.GET)
    public List<QueryVo> getAccountInfo(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_ip_mrbase_infoService.getAccountInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 住院诊疗费用结算明细记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_ip_account_list", method = RequestMethod.GET)
    public List<QueryVo> getAccountList(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_ip_mrbase_infoService.getAccount_list();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 手术记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_adm_operation_info", method = RequestMethod.GET)
    public List<QueryVo> getOperatoionInfo(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_ip_mrbase_infoService.getOperatoionInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }

    /**
     * 检查报告记录
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "ods_adm_pacsreport_info", method = RequestMethod.GET)
    public List<QueryVo> getPacsreportInfo(@RequestParam(value = "token")String token) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token1 = (String)valueOperations.get("token1");
        if (token1.equals(token)) {
            return ods_ip_mrbase_infoService.getPacsreportInfo();
        }
        throw new UDException(-1,"token验证失效","请检查token是否正确或联系管理员更换token");
    }
}
