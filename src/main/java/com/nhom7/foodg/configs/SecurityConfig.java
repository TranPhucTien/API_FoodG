package com.nhom7.foodg.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .oauth2Login()
                .defaultSuccessUrl("/userAttributes", true)
                //.loginPage("/oauth2/authorization/google")
                //.loginProcessingUrl("/loginGG")
                .and()
                .formLogin();
        return httpSecurity.build();
    }
}
