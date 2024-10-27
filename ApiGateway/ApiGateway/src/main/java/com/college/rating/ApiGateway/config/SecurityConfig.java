package com.college.rating.ApiGateway.config;

import com.college.rating.ApiGateway.controller.CustomAuth2LoginSuccesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        http
                .authorizeExchange((authorize)->authorize
                        .anyExchange()
                        .authenticated()

                )
                .csrf((csrf)->csrf
                        .disable())
//                .oauth2Login((oath2)-> oath2
//                        .authenticationSuccessHandler(customAuth2LoginSuccesHandler))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer((oath2)->oath2
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
}
