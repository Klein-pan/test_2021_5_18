package com.example.demo.mapper.sqlserver;

import com.example.demo.pojo.model.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OdsOpMaper {


    //List<Ods_op_adm_info> getods_op_adm_info(@Param("0") String biginTime, @Param("1")String endTime);
    List<Ods_op_adm_info> getods_op_adm_info();
   // List<Ods_op_account_list> getods_op_account_list(@Param("0") String biginTime, @Param("1")String endTime);


    List<Ods_op_account_list> getods_op_account_list();
   // List<Ods_op_account_info> getAccountInfo(@Param("0") String biginTime, @Param("1")String endTime);

    List<Ods_op_account_info> getAccountInfo();
}
