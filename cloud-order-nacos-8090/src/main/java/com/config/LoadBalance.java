package com.config;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalance {

    ServiceInstance getMyLoadBalance(List<ServiceInstance> instances);
}
