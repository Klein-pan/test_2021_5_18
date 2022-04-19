package com.example.demo.pojo.model.vo;

import lombok.Data;

import java.util.Date;


@Data
public class QueryVo {
    private Respense response;
    private String date;
    private int elementCount;
    private String result;
}
