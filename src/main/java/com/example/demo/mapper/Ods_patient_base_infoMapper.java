package com.example.demo.mapper;

import com.example.demo.pojo.model.Ods_ip_mrbase_info;
import com.example.demo.pojo.model.Ods_patient_base_info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Ods_patient_base_infoMapper {
    List<Ods_patient_base_info> getInfo();
}
