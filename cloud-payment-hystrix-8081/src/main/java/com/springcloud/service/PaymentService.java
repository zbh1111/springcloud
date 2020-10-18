package com.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_ok(Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ok,id:"+id+"\t"+"0(n_n)";

    }

    /**
     * 3秒之后，给客户端响应
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandle",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id){
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_Timeout,id:"+id+" 0(n_n) 耗时"+timeNumber+"秒";
    }

    /**
     * 降级处理
     * @param id
     * @return
     */
    public String paymentInfo_TimeOutHandle(Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_TimeOutHandle,id:"+id+"\t 耗时报错"+"0(n_n)---";
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),   //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  //请求次数
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "20"), //失败率达到多少次宕机
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "6000")  //时间窗口期
    })
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        if(id<0){
            throw new RuntimeException("*********id 不能为负数");
        }
        String seialNum = IdUtil.simpleUUID();

        return "线程池："+Thread.currentThread().getName()+" paymentInfo_Timeout,流水号seialNum:"+seialNum;
    }

    /**
     * 服务熔断处理fallback
     * @param id
     * @return
     */
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数-----------id:"+id;
    }
}
