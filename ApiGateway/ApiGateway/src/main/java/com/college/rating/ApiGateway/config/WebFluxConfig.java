package com.college.rating.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // Specific frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify methods
                .allowedHeaders("Content-Type", "Authorization") // Specify headers
                .allowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", "http://localhost:3000");  // Specific frontend origin
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Specify methods
            headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");  // Specify headers
            headers.add("Access-Control-Allow-Credentials", "true");  // Allow credentials
            return chain.filter(exchange);
        };
    }
}

