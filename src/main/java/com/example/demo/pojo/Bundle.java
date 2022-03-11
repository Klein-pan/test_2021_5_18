package com.example.demo.pojo;

import lombok.Data;

@Data
public class Bundle {
    private String id;
    private MessageHeader messageHeader;
    private String messageBody;
}
