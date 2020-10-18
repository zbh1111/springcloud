package com.springcloud80.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {


   // @LoadBalanced     //负载均衡(使客服端平均访问服务器接口)
   @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    };


}
