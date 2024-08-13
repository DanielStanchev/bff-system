package com.tinqinacademy.bff.core.config;

import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    private final String[] ADMIN_URLS = {
//        RestApiRoutes.SYSTEM_REGISTER_VISITOR,
//        RestApiRoutes.SYSTEM_REPORT_VISITOR_INFO,
//        RestApiRoutes.SYSTEM_CREATE_ROOM,
//        RestApiRoutes.SYSTEM_UPDATE_ROOM,
//        RestApiRoutes.SYSTEM_UPDATE_ROOM_PARTIALLY,
//        RestApiRoutes.SYSTEM_DELETE_ROOM
    };
    private final String[] USER_URLS = {
//        RestApiRoutes.HOTEL_UNBOOK_ROOM,
//        RestApiRoutes.HOTEL_BOOK_ROOM
    };

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> {
                request.requestMatchers(HttpMethod.GET,RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS).hasAnyAuthority("USER");
//                request.requestMatchers(ADMIN_URLS).hasAuthority("ADMIN");
//                request.requestMatchers(USER_URLS).hasAnyAuthority("USER", "ADMIN");
                request.anyRequest().permitAll();
            })
            .sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}