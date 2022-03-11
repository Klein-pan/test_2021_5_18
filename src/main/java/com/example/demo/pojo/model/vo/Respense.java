package com.example.demo.pojo.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class Respense {
    private String code;
    private String desc;
    private List<ResultData> data;
}
