package com.springcloud80.Controller;

import com.cloud.bean.CommonResult;
import com.cloud.bean.Payment;
import com.myrule.MySelfRule;
import com.springcloud80.lb.LoadBalanced;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RibbonClient(name = "CLOUD-PAYMENT-SERVER",configuration = MySelfRule.class)
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVER";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalanced loadBalanced;

    @Autowired
    DiscoveryClient discoveryClient;


    @GetMapping(value = "/consumer/add")
    public CommonResult<Payment> add(String serial){
        Payment payment = new Payment();
        payment.setSerial(serial);
        return restTemplate.postForObject(PAYMENT_URL+"/payment/add",payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id")Long id){
        System.out.printf("---------");
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping(value = "/consumer/get/lb")
    public CommonResult<Payment> getLb(){
        //得到微服务实例总数
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVER");
        if(instances==null||instances.size()<=0){
            return null;
        }
        //得到本次轮询的服务实例
        ServiceInstance serviceInstance = loadBalanced.instance(instances);
        //得到服务地址
        URI uri = serviceInstance.getUri();

        System.out.printf("---------");

        return restTemplate.getForObject(uri+"/payment/get/23",CommonResult.class);
    }
}
