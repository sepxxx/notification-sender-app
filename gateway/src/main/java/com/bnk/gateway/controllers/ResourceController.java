package com.bnk.gateway.controllers;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ResourceController {
    @GetMapping(value = "/token")
    public Mono<String> getHome(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return Mono.just(authorizedClient.getAccessToken().getTokenValue());
    }

    @GetMapping(value = "/myone")
    public Mono<Object> getMyOne(OAuth2AuthenticationToken authentication) {
//        System.out.println(authentication.getPrincipal().getAttributes().get("sub"));
        return Mono.just(authentication.getPrincipal().getAttributes().get("sub"));
    }

}