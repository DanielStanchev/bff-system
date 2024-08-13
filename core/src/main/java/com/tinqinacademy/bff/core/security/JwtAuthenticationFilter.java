package com.tinqinacademy.bff.core.security;

import com.tinqinacademy.authentication.api.operations.authenticateuser.AuthenticateUserInput;
import com.tinqinacademy.authentication.api.operations.authenticateuser.AuthenticateUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationRestExport authenticationRestExport;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
        throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        try {
            AuthenticateUserInput input = AuthenticateUserInput.builder()
                .token(jwt)
                .build();

            AuthenticateUserOutput output = authenticationRestExport.authenticate(input);

            UserAuthority userAuthority = UserAuthority.builder()
                .authority(output.getRole().toUpperCase())
                .build();

            LoggedUserDetails loggedUserDetails = LoggedUserDetails.builder()
                .role(userAuthority.getAuthority())
                .id(output.getUsername()).build();

            Authentication authenticationToken = new JwtAuthenticationToken(loggedUserDetails);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception ignored) {}

        filterChain.doFilter(request, response);
    }
}
