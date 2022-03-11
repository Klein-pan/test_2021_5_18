package com.example.demo.controller;

import com.example.demo.pojo.RespensBundle;
import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import com.example.demo.services.Ods_patient_base_infoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//住院业务接口
@RestController
@RequestMapping("/upload/data")
public class ods_ip_HbController {

    @Autowired
    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;
    @RequestMapping(value = "ods_ip_mrbase_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo() throws Exception {
        return ods_ip_mrbase_infoService.getMrbaseInfo();
    }

    @RequestMapping(value = "ods_ip_adm_info", method = RequestMethod.GET)
    public List<QueryVo> getAdmInfo() throws Exception {
        return ods_ip_mrbase_infoService.getAdmInfo();
    }
}
