package com.example.demo.controller;

import com.example.demo.pojo.model.vo.QueryVo;
import com.example.demo.services.OdsOpServices;
import com.example.demo.services.Ods_otherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//其它业务接口
@RestController
@RequestMapping("/upload/data")
public class ods_otherControlle {
    @Autowired
    Ods_otherService ods_otherService;
    @RequestMapping(value = "ods_resource_locbed_info", method = RequestMethod.GET)
    public List<QueryVo> getInfo() throws Exception {
        return ods_otherService.getLocbedInfo();
    }
}
