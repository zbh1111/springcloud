package com.springcloud.service;

import com.cloud.bean.Payment;
import com.springcloud.dao.PaymentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    PaymentDao paymentDao;

    @Override
    public Payment queryById(Long id) {
        return paymentDao.queryById(id);
    }

    @Override
    public int addPayment(Payment payment) {
        return paymentDao.addPayment(payment);
    }
}
