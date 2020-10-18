package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springcloud.service.PaymentHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@EnableFeignClients
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")  //设置全局的fallback
public class OrderHystrinxController {

    @Autowired
    PaymentHystrixService paymentHystrixService;


    @GetMapping("/consumer/payment/hystrix/{id}")
    public String paymentInfo_ok(@PathVariable("id")Integer id){
        String result = paymentHystrixService.paymentInfo_ok(id);
        return result;
    }

    /**
     * 3秒之后，给客户端响应
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    /*@HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandle",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })*/
    @HystrixCommand
    public String paymentInfo_Timeout(@PathVariable("id")Integer id){
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String result = paymentHystrixService.paymentInfo_ok(id);
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_Timeout,id:"+id+" 0(n_n) 耗时"+timeNumber+"秒"+result;
    }


    /**
     * 服务降级处理fallback
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandle(@PathVariable("id") Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_TimeOutHandle,id:"+id+"\t 耗时报错"+"0(n_n)---";
    }

    /**
     * 全部Fallback
     * @return
     */
    public String payment_Global_FallbackMethod(){
        return "全局Fallback";
    }



}
