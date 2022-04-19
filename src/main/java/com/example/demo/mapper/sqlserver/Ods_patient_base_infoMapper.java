package com.example.demo.mapper.sqlserver;

import com.example.demo.pojo.model.Ods_ip_mrbase_info;
import com.example.demo.pojo.model.Ods_patient_base_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface Ods_patient_base_infoMapper {
    List<Ods_patient_base_info> getInfo(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

}
