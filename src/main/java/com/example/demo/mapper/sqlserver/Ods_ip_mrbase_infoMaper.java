package com.example.demo.mapper.sqlserver;

import com.example.demo.pojo.model.*;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_127;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_128;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_129;
import com.example.demo.pojo.model.Ods_ip_mrbase_info_1.Mrbase_130;

import com.example.demo.pojo.model.ods_adm_operation_info.Operation_76;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface Ods_ip_mrbase_infoMaper {
       // List<Ods_ip_mrbase_info> getMrbaseInfo(@Param("0")String beginTime,@Param("1")String endTime);
        List<Ods_ip_mrbase_info> getMrbaseInfo();
        //根据id查询127表
        List<Mrbase_127> getMrbase127byId(@Param("0") String mrbase_01, @Param("1") String mrbase_04);
        //根据id查询128表
        List<Mrbase_128> getMrbase128byId(@Param("0") String mrbase_01, @Param("1") String mrbase_04);
        //根据id查询129表
        List<Mrbase_129> getMrbase129byId(@Param("0") String mrbase_01, @Param("1") String mrbase_04);
        //根据id查询130表
        List<Mrbase_130> getMrbase130byId(@Param("0") String mrbase_01, @Param("1") String mrbase_04);

        List<Ods_ip_adm_info>  getAdmInfo();

        List<Ods_ip_account_info> getAccountInfo();//Too do暂未填写sql;

        List<Ods_ip_account_list> getAccount_list();

        List<Ods_adm_operation_info> getOperatoionInfo();

        List<Operation_76> getOperation_76(@Param("0") String operation_01,@Param("1")  String operation_03);

        List<Ods_adm_pacsreport_info> getPacsreportInfo();
}
