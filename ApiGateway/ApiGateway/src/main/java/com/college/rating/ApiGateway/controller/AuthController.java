package com.college.rating.ApiGateway.controller;

import com.college.rating.ApiGateway.entity.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public Mono<Void> login(@RegisteredOAuth2AuthorizedClient("okta")
                                                  OAuth2AuthorizedClient oAuth2AuthorizedClient,
                            @AuthenticationPrincipal OidcUser user,
                            Model model, ServerWebExchange exchange)
    {
            logger.info("User email id: {} ", user.getEmail());
            //creating AuthResponse object
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUserId(user.getEmail());
            authResponse.setAccessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue());
            authResponse.setRefreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue());
            authResponse.setExpireAt(oAuth2AuthorizedClient.getAccessToken().getExpiresAt().getEpochSecond());

            List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
                return grantedAuthority.getAuthority();
            }).collect(Collectors.toList());

            authResponse.setAuthorities(authorities);

//            return new ResponseEntity<>(authResponse, HttpStatusCode.valueOf(HttpStatus.SC_OK));
        // Redirect to frontend and include token in query parameters or via another mechanism
        String redirectUri = "http://localhost:3000/redirect"; // Frontend endpoint
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(redirectUri));
        return exchange.getResponse().setComplete(); // Complete the response
    }
}
