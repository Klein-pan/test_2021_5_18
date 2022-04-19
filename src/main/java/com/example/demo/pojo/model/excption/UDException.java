package com.example.demo.pojo.model.excption;

import lombok.Data;

@Data
public class UDException extends RuntimeException{
    private int code;  //异常状态码
    private String message;  //异常信息
    private String descinfo;   //描述
    public UDException(int code, String message,String descinfo) {
        this.code = code;
        this.message = message;
        this.descinfo = descinfo;
    }

}
