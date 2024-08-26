package com.tinqinacademy.bff.core.config.securityconfig;

import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private void adminRequestsAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        request
            .requestMatchers(HttpMethod.POST, RestApiRoutes.SYSTEM_REGISTER_VISITOR).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.GET, RestApiRoutes.SYSTEM_REPORT_VISITOR_INFO).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.DELETE, RestApiRoutes.SYSTEM_DELETE_ROOM).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.POST, RestApiRoutes.SYSTEM_CREATE_ROOM).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.PATCH, RestApiRoutes.SYSTEM_UPDATE_ROOM_PARTIALLY).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.PUT, RestApiRoutes.SYSTEM_UPDATE_ROOM).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.DELETE, RestApiRoutes.SYSTEM_DELETE_COMMENT).hasRole(ADMIN_ROLE)
            .requestMatchers(HttpMethod.PUT, RestApiRoutes.SYSTEM_EDIT_COMMENT).hasRole(ADMIN_ROLE);
    }

    private void userRequestsAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        request
            .requestMatchers(HttpMethod.POST, RestApiRoutes.HOTEL_BOOK_ROOM).hasAnyRole(USER_ROLE, ADMIN_ROLE)
            .requestMatchers(HttpMethod.DELETE, RestApiRoutes.HOTEL_UNBOOK_ROOM).hasAnyRole(USER_ROLE, ADMIN_ROLE)
            .requestMatchers(HttpMethod.POST, RestApiRoutes.HOTEL_POST_COMMENT).hasAnyRole(USER_ROLE, ADMIN_ROLE)
            .requestMatchers(HttpMethod.PATCH, RestApiRoutes.HOTEL_UPDATE_COMMENT).hasAnyRole(USER_ROLE, ADMIN_ROLE);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> {
                adminRequestsAuthorization(request);
                userRequestsAuthorization(request);
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