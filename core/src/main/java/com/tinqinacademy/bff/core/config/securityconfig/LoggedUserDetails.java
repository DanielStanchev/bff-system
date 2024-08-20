package com.tinqinacademy.bff.core.config.securityconfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
public class LoggedUserDetails implements UserDetails {
    private UserAuthority userAuthority;
    private String id;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(userAuthority);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return id;
    }
}
