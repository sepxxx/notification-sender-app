package com.bnk.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserAttributeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("UserAttributeFilter started");
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(OAuth2AuthenticationToken.class::isInstance)
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(auth -> {
                    OAuth2User oAuth2User = auth.getPrincipal();
                    String sub = oAuth2User.getAttribute("sub");
                    log.info("Request URL: {}", exchange.getRequest().getURI());
                    log.info("sub: {}", sub);
                    return chain.filter(exchange.mutate()
                            .request(r -> r.header("sub", sub))
                            .build());
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}