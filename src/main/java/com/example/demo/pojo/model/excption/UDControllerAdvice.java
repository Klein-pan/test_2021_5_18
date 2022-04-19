package com.example.demo.pojo.model.excption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //控制器增强 配合ExceptionHandler实现全局捕抓异常
@Slf4j
public class UDControllerAdvice {
    @ExceptionHandler(value = UDException.class)
    @ResponseBody
    public Map exceptionHandler (UDException ud){
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("code",ud.getCode());
        map.put("message",ud.getMessage());
        map.put("descinfo",ud.getDescinfo());
        //打印日志信息
        log.info("捕抓到异常信息->>"+ud.getDescinfo());
        return map;
    }
}
