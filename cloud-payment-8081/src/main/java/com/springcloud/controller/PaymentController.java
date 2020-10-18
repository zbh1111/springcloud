package com.springcloud.controller;

import com.cloud.bean.CommonResult;
import com.cloud.bean.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@EnableEurekaClient
@EnableDiscoveryClient
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id){
        Payment payment = paymentService.queryById(id);
        if(payment==null){
            return new CommonResult(444,"查询信息错误,端口号："+serverPort,null);
        }else {
            return new CommonResult(200,"查询信息成功,端口号："+serverPort,payment);
        }
    }

    @PostMapping(value = "/payment/add")
    public CommonResult addPayment(@RequestBody Payment payment){
        int count = paymentService.addPayment(payment);
        if(count==0){
            return new CommonResult(444,"添加信息错误,端口号："+serverPort,null);
        }else {
            return new CommonResult(200,"添加信息成功,端口号："+serverPort,payment);
        }
    }

    @GetMapping(value = "/payment/lb/{id}")
    public CommonResult lb(@PathVariable("id") Long id){
        Payment payment = paymentService.queryById(id);
        if(payment==null){
            return new CommonResult(444,"查询信息错误,端口号："+serverPort,null);
        }else {
            return new CommonResult(200,"查询信息成功,端口号："+serverPort,payment);
        }
    }

    /**
     * 服务发现Discovery
     * @return
     */
    @GetMapping(value = "/payment/discovery")
    public Object getDiscovery(){
        List<String> services = discoveryClient.getServices();
        for (String name:services){
            log.info("**********服务名："+name);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVER");
        for(ServiceInstance instance:instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;

    }
}
