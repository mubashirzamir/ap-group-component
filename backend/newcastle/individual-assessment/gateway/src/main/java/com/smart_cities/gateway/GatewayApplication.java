package com.smart_cities.gateway;

import com.smart_cities.gateway.config.UriConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(UriConfiguration.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        return builder.routes()
                .route("provider-01-routes", r -> r
                        .path("/provider/1/**")
                        .filters(GatewayApplication.applyProviderRouteFilters())
                        .uri(uriConfiguration.getProvider01()))
                .route("provider-02-routes", r -> r
                        .path("/provider/2/**")
                        .filters(GatewayApplication.applyProviderRouteFilters())
                        .uri(uriConfiguration.getProvider02()))
                .route("provider-03-routes", r -> r
                        .path("/provider/3/**")
                        .filters(GatewayApplication.applyProviderRouteFilters())
                        .uri(uriConfiguration.getProvider03()))
                .route("city-routes", r -> r
                        .path("/city/**")
                        .filters(GatewayApplication.applyCityRouteFilters())
                        .uri(uriConfiguration.getCity())
                )
                .build();
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("Service is currently unavailable");
    }

    /**
     * Strips the prefix i.e. the two segments of the URI /provider/{id}
     * and sets the fallback URI to /fallback
     */
    public static Function<GatewayFilterSpec, UriSpec> applyProviderRouteFilters() {
        return f -> f.circuitBreaker((config -> config.setFallbackUri("forward:/fallback"))).stripPrefix(2);
    }

    /**
     * Strips the prefix i.e. the one segment of the URI /city
     * and sets the fallback URI to /fallback
     */
    public static Function<GatewayFilterSpec, UriSpec> applyCityRouteFilters() {
        return f -> f.circuitBreaker((config -> config.setFallbackUri("forward:/fallback"))).stripPrefix(1);
    }
}