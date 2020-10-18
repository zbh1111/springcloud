package com.springcloud.dao;

import com.cloud.bean.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {

    Payment queryById(@Param("id") Long id);

    int addPayment(Payment payment);

}
