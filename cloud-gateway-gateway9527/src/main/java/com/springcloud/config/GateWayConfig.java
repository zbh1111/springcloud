package com.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway 路由配置 的两种方式：1，代码如下，2：yml配置
 */
@Configuration
public class GateWayConfig {

    /**
     * 当访问http://localhost:9527/guonei 会转发到 http://news.baidu.com/guonei
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder){

        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("path_route_judy",r ->r.path("/guonei").uri("http://news.baidu.com/guonei")).build(); //配置里的id，predicates，uri

        return routes.build();
    }
}
