package com.example.demo.pojo.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("client.prod")
public class Client {
    private String ClientId;
    private String ClientSecret;
    private String key;
}
