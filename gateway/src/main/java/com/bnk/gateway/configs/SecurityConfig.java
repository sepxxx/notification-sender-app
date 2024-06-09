package com.bnk.gateway.configs;

import com.bnk.gateway.filters.SubExtractorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.authorizeExchange(
//                    auth -> {
//                        auth.anyExchange().authenticated();
//                    }
//                )
//                .oauth2Login(withDefaults())
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()));
////                .cors(Customizer.withDefaults());
////        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
//        return http.build();
//    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, SubExtractorFilter subExtractorFilter) {
        http.authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(withDefaults())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()))
                .addFilterAt(subExtractorFilter, SecurityWebFiltersOrder.AUTHENTICATION);
//                .cors(Customizer.withDefaults());
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

}