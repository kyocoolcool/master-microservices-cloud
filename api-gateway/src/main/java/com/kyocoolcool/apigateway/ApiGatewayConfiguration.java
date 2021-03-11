package com.kyocoolcool.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/3/11 11:33 AM
 */
@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator getwayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param","MyValue"))
                        .uri("http://httpbin.org:80"))
                .route(p->p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-new/**")
                        .filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                        .build();
    }
}
