package com.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LoadBalanceImp implements LoadBalance
{

    AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 自定义轮询方法
     * @param instances
     * @return
     */
    @Override
    public ServiceInstance getMyLoadBalance(List<ServiceInstance> instances) {
        Integer index = atomicInteger.incrementAndGet() % instances.size();
        return instances.get(index);
    }
}
