package com.college.rating.ApiGateway.controller;

import com.college.rating.ApiGateway.entity.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public Mono<Void> login(@RegisteredOAuth2AuthorizedClient("okta")
                                                  OAuth2AuthorizedClient oAuth2AuthorizedClient,
                                      @AuthenticationPrincipal OidcUser user,
                                      ServerWebExchange exchange)
    {
//        logger.info("User attributes: {}", user.getAttributes());

            logger.info("User email id: {} ", user.getEmail());
            logger.info("User name is: {} ", user.getAttributes().get("name").toString());
            //creating AuthResponse object
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUserId(user.getEmail());
            authResponse.setUserName(user.getName());
            authResponse.setAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
            authResponse.setRefreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue());
            authResponse.setExpireAt(oAuth2AuthorizedClient.getAccessToken().getExpiresAt().getEpochSecond());
//            System.out.println(oAuth2AuthorizedClient.getRefreshToken().getTokenValue());
            logger.info("Access Token: {} ", oAuth2AuthorizedClient.getAccessToken().getTokenValue());
            List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
                return grantedAuthority.getAuthority();
            }).collect(Collectors.toList());

            authResponse.setAuthorities(authorities);

        /// Set the redirect URI after logging
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create("http://localhost:3000/redirect"));  // Replace with your frontend URL

        return exchange.getResponse().setComplete();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user-info")
    public  Mono<ResponseEntity<AuthResponse>> getUserInfo(@RegisteredOAuth2AuthorizedClient("okta")
                                                       OAuth2AuthorizedClient oAuth2AuthorizedClient,@AuthenticationPrincipal OidcUser user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(user.getEmail());
        authResponse.setUserName(user.getAttributes().get("name").toString());
        authResponse.setAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        authResponse.setRefreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue());
        authResponse.setExpireAt(oAuth2AuthorizedClient.getAccessToken().getExpiresAt().getEpochSecond());
        List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
            return grantedAuthority.getAuthority();
        }).collect(Collectors.toList());
        authResponse.setAuthorities(authorities);
        logger.info("User name is: {} ", user.getAttributes().get("name").toString());
        return Mono.just(ResponseEntity.ok(authResponse));
    }
}
