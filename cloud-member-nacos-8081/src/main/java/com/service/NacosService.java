package com.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosService {

    @Value("${server.port}")
    private String post;

    @GetMapping("/getUser")
    public String getUser(){
        return "nocas 学习"+post;
    }
}
