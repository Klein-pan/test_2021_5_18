package com.example.demo.controller;

import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.Ods_ip_mrbase_infoService;
import com.example.demo.services.Ods_patient_base_infoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/upload/data")
public class ods_patient_base_infoController {
    @Autowired
    Ods_patient_base_infoService ods_patient_base_infoService;
    @Autowired
    Ods_ip_mrbase_infoService ods_ip_mrbase_infoService;
    @RequestMapping(value = "ods_patient_base_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo() throws Exception {
        return ods_patient_base_infoService.getInfo();
    }

}
