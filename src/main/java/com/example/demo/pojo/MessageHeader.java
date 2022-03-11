package com.example.demo.pojo;

import lombok.Data;


@Data
public class MessageHeader {
    private String signature;
    private String actionCode;
    private String timestamp;
    private String orgCode;
    private Integer elementCount;
    private String sourceSystemCode;


}
