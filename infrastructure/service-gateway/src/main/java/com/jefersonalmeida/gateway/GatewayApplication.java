package com.jefersonalmeida.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/customers/**").uri("lb://service-customer"))
                .route(r -> r.path("/cards/**").uri("lb://service-card"))
                .route(r -> r.path("/evaluators/**").uri("lb://service-evaluator"))
                .build();
    }
}
