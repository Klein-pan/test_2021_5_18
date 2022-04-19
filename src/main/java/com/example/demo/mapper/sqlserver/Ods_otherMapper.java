package com.example.demo.mapper.sqlserver;

import com.example.demo.pojo.model.Ods_chagedetail_list;
import com.example.demo.pojo.model.Ods_dim_loc;
import com.example.demo.pojo.model.Ods_order_list;
import com.example.demo.pojo.model.Ods_user_base_info;
import com.example.demo.pojo.model.Other.Ods_resource_hospbed_info;
import com.example.demo.pojo.model.Other.Ods_resource_locbed_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface Ods_otherMapper {

     List<Ods_chagedetail_list> getChagedetailList();

    List<Ods_resource_locbed_info> getLocbedInfo();

    List<Ods_resource_hospbed_info> getHospbedInfo();
    //职工信息表
    List<Ods_user_base_info> getBaseInfo();
    //暂未sql
    //字典表
    List<Ods_dim_loc> getDimIoc();

    List<Ods_order_list> getOrderList();
}
