package com.college.rating.ApiGateway.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.net.URI;

@Component
public class CustomAuth2LoginSuccesHandler implements ServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Log user details and attributes
            System.out.println("User Name: " + oauthToken.getName());
            System.out.println("Authorities: " + oauthToken.getAuthorities());
            oauthToken.getPrincipal().getAttributes().forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });

        }

        // Set the redirect URI after logging
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create("http://localhost:3000/redirect"));  // Replace with your frontend URL

        return exchange.getResponse().setComplete();
    }
}
