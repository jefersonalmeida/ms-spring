package com.jefersonalmeida.discovery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityBasicConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .httpBasic(withDefaults())
                .build();
    }
}
