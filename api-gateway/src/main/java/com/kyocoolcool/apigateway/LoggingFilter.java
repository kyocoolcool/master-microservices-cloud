package com.kyocoolcool.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/3/11 2:19 PM
 */
@Component
public class LoggingFilter implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Path of the request received -> {}",
                exchange.getRequest().getPath());
        return chain.filter(exchange);
    }
}
