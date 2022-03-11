package com.example.demo.pojo.model.vo;

import lombok.Data;

import java.util.Date;


@Data
public class QueryVo {
    private Respense response;
    private Date date;
    private int elementCount;
}
