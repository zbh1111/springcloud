package com.springcloud80.controller;

import com.cloud.bean.CommonResult;
import com.cloud.bean.Payment;
import com.springcloud80.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderFeignController {

    @Autowired
    PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id")Long id){
        System.out.printf("---------");
        return paymentFeignService.getPayment(id);
    }
}
