package com.manuellugodev.hotel.security;

import com.manuellugodev.hotel.services.AppointmentService;
import com.manuellugodev.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    DataSource dataSource;


    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public OwnerValidationFilter ownerValidationFilter() {
        return new OwnerValidationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsManager(dataSource));
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                // Public endpoints
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST,"/user/register").permitAll()

                                // Appointment endpoints - EMPLOYEE & ADMIN
                                .requestMatchers(HttpMethod.POST, "/appointment").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.GET, "/appointment").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.GET, "/appointment/guest/**").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/appointment").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/appointment").hasAnyRole("EMPLOYEE","ADMIN")

                                // Room endpoints - View: EMPLOYEE & ADMIN, Modify: ADMIN only
                                .requestMatchers(HttpMethod.GET, "/rooms").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.GET, "/rooms/all").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.GET, "/rooms/{id}").hasAnyRole("EMPLOYEE","ADMIN")
                                .requestMatchers(HttpMethod.POST, "/rooms").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/rooms/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/rooms/{id}").hasRole("ADMIN")

                                // User endpoints - ADMIN only for listing, EMPLOYEE & ADMIN for individual
                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("EMPLOYEE","ADMIN")
                                /*.requestMatchers(
                                        "/swagger-ui/**",        // Swagger UI static resources
                                        "/v3/api-docs/**",       // OpenAPI documentation
                                        "/swagger-resources/**", // Additional Swagger resources
                                        "/webjars/**"            // Static webjars for Swagger UI
                                ).permitAll()*/

                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(ownerValidationFilter(), JwtAuthenticationFilter.class);

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
