package com.example.demo.pojo.mysqlresult;

import lombok.Data;

@Data
public class Result {
    private String id;
    private String resultCode;
    private String resultDesc;
    private String data;
}
