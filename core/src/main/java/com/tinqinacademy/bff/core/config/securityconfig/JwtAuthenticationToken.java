package com.tinqinacademy.bff.core.config.securityconfig;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private  final LoggedUserDetails loggedUserDetails;

    public JwtAuthenticationToken(LoggedUserDetails loggedUserDetails) {
        super(loggedUserDetails.getAuthorities());
        this.loggedUserDetails = loggedUserDetails;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return loggedUserDetails;
    }
}
