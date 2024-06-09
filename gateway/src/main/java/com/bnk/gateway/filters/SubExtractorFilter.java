package com.bnk.gateway.filters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.SecurityContext;
@Component
@Slf4j
public class SubExtractorFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("Request URL: {}", exchange.getRequest().getURI());
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth.getPrincipal() instanceof Jwt) {
                        Jwt jwt = (Jwt) auth.getPrincipal();
                        String sub = jwt.getClaim("sub");
                        // Здесь вы можете сохранить значение sub в request attribute или сделать что-то еще
                        exchange.getAttributes().put("sub", sub);
                    }
                    return chain.filter(exchange);
                });
    }
}
