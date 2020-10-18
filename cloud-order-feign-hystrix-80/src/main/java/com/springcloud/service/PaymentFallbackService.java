package com.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 客户端自己的降级处理
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_ok(Integer id) {
        return "paymentInfo_ok";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "paymentInfo_Timeout";
    }
}
