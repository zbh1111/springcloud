package com.springcloud80.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalanced {

    ServiceInstance instance(List<ServiceInstance> serviceInstances);

}
