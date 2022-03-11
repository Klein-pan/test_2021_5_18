package com.example.demo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {
    private String access_token;
    private String scope;
    private String token_type;
    private Integer expires_in;
}
