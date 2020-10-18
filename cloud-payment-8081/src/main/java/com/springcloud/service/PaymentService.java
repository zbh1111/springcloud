package com.springcloud.service;

import com.cloud.bean.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    Payment queryById(@Param("id") Long id);

    int addPayment(Payment payment);
}
