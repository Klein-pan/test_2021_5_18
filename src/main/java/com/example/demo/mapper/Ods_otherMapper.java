package com.example.demo.mapper;

import com.example.demo.pojo.model.Other.Ods_resource_hospbed_info;
import com.example.demo.pojo.model.Other.Ods_resource_locbed_info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Ods_otherMapper {
    List<Ods_resource_locbed_info> getLocbedInfo();

    List<Ods_resource_hospbed_info> getHospbedInfo();
}
