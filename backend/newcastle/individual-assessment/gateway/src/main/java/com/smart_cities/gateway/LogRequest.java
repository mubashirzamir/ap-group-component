package com.smart_cities.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LogRequest implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(LogRequest.class);

    /**
     * Logs the incoming request.
     */
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();
        log.info("Incoming request: " + method + " " + path);

        return chain.filter(exchange);
    }
}
