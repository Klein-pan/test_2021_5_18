package com.example.demo.controller;

import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.OdsOpServices;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//门诊业务
@RestController
@RequestMapping("/upload/data")
public class ods_OpController {
    //门急诊就诊
    @Autowired
    OdsOpServices odsOpServices;
    @RequestMapping(value = "ods_op_adm_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo() throws Exception {
        return odsOpServices.getAdmInfo();
    }

//    @RequestMapping(value = "ods_ip_adm_info", method = RequestMethod.GET)
//    public List<QueryVo> getInfo() throws Exception {
//        return odsOpServices.getAdmInfo();
//    }
}
