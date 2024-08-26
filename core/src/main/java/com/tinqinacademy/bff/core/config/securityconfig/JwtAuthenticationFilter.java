package com.tinqinacademy.bff.core.config.securityconfig;

import com.tinqinacademy.authentication.api.operations.authenticateuser.AuthenticateUserInput;
import com.tinqinacademy.authentication.api.operations.authenticateuser.AuthenticateUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
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

        String token = authHeader.substring(7);

        try {
            AuthenticateUserInput userToken = AuthenticateUserInput.builder().token(token).build();

            AuthenticateUserOutput authenticateUser= authenticationRestExport.authenticate(userToken);

            // i am using this for test the authentication
//            try{
//                log.info("userToken: {}", userToken);
//                authenticateUser = authenticationRestExport.authenticate(userToken);
//
//            } catch (FeignException e){
//                log.error(e.getMessage());
//                log.error(Arrays.toString(e.getStackTrace()));
//                throw new RuntimeException("asd");
//            }

            UserAuthority userAuthority = UserAuthority.builder().authority(authenticateUser.getRole().toUpperCase()).build();

            LoggedUserDetails loggedUserDetails = LoggedUserDetails.builder()
                .userAuthority(userAuthority)
                .id(authenticateUser.getId())
                .build();

            Authentication authenticationTokenInfo = new AuthenticationTokenInfo(loggedUserDetails);

            SecurityContextHolder.getContext().setAuthentication(authenticationTokenInfo);

        } catch (Exception ignored) {}

        filterChain.doFilter(request, response);
    }
}
