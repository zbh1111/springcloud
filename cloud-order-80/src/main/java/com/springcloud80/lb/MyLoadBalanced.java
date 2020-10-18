package com.springcloud80.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义轮询
 */
@Component
public class MyLoadBalanced implements LoadBalanced{

    //原子类 初始值
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIntance(){
        int current;
        int next;
        do{
          current = this.atomicInteger.get();
          next = current >= 2147483647?0:current+1;
        }while (!atomicInteger.compareAndSet(current,next));
        System.out.println("---第几次访问-------next:"+next);
        return next;
    }

    /**
     * 得到本次轮询服务实例
     * @param serviceInstances
     * @return
     */
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        //第几次访问数字%总的微服务实例 =本次轮询服务实例的下标
        int index = getAndIntance()%serviceInstances.size();

        //返回下标服务实例
        return serviceInstances.get(index);
    }
}
