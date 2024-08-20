package com.tinqinacademy.bff.core.config.securityconfig;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationTokenInfo extends AbstractAuthenticationToken {
    private  final LoggedUserDetails loggedUserDetails;

    public AuthenticationTokenInfo(LoggedUserDetails loggedUserDetails) {
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
