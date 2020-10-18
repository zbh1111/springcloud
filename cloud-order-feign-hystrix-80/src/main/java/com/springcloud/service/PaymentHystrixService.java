package com.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVER",fallback = PaymentFallbackService.class)  //服务器宕机时，客户端会启用自己的降级处理PaymentFallbackService
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/{id}")
    public String paymentInfo_ok(@PathVariable("id")Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id")Integer id);



}
