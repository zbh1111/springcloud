package com.service;

import com.config.LoadBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class orderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalance loadBalance;

    @GetMapping("/getService")
    public String getMemberUser(){
        List<ServiceInstance> instanceList = discoveryClient.getInstances("aloud-nacos-member");
       // System.out.println(instanceList.get(0));
        //String string = restTemplate.getForObject(instanceList.get(0).getUri()+"/getUser",String.class);
        ServiceInstance instance = loadBalance.getMyLoadBalance(instanceList);
        String string = restTemplate.getForObject(instance.getUri()+"/getUser",String.class);
        System.out.println(string);
        return string;
    }


    /**
     * Ribbon 实现负载均衡
     * @return
     */
    @GetMapping("/getServiceRibbon")
    public String getMemberUserRibbon(){
        String string = restTemplate.getForObject("http://aloud-nacos-member/getUser",String.class);
        System.out.println(string);
        return string;
    }
}
