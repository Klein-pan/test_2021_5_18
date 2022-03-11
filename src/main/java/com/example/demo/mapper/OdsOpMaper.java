package com.example.demo.mapper;

import com.example.demo.pojo.model.Ods_ip_mrbase_info;

import com.example.demo.pojo.model.Ods_op_adm_info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OdsOpMaper {

        List<Ods_op_adm_info> getods_op_adm_info();

}
