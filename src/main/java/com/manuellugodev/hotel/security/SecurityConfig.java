package com.manuellugodev.hotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {


    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer.requestMatchers(HttpMethod.POST, "/appointment").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/appointment").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/appointment/guest/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/rooms").hasRole("EMPLOYEE")
        );

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
