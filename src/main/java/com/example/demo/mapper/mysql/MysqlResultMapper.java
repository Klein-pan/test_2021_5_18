package com.example.demo.mapper.mysql;

import com.example.demo.pojo.mysqlresult.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MysqlResultMapper {

    int addResult(@Param("id") String id, @Param("resultCode") String resultCode, @Param("resultDesc") String resultDesc,@Param("format") String format,@Param("elementcount")String elementcount);

    int addResponse( @Param("id") String id, @Param("code") String code, @Param("desc") String desc,@Param("format") String format,@Param("table")String table);

    int addUpTimeStart(@Param("startTime") String startTime, @Param("table") String table);

    int addUpTimeEnd(@Param("startTime")String startTime, @Param("table")String table, @Param("endTime") String endTime, @Param("elementcount") String count);
}
