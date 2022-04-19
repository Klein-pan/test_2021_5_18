package com.example.demo.mapper.sqlserver;

import com.example.demo.pojo.model.odsHp.Ods_hp_account_info;
import com.example.demo.pojo.model.odsHp.Ods_hp_account_list;
import com.example.demo.pojo.model.odsHp.Ods_hp_adm_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface Ods_hpMapper {
     //List<Ods_hp_adm_info> getAmdInfo(@Param("0") String beginTime, @Param("1") String endTime);
     List<Ods_hp_adm_info> getAmdInfo();

   //  List<Ods_hp_account_info> getAccountInfo(@Param("0")String biginTime, @Param("1")String endTime);
     List<Ods_hp_account_info> getAccountInfo();

//     List<Ods_hp_account_list> getAccountList(@Param("0") String beginTime, @Param("1") String endTime);
     List<Ods_hp_account_list> getAccountList();
}
