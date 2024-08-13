package com.tinqinacademy.bff.core.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
public class LoggedUserDetails implements UserDetails {
    private String id;
    private String role;

    public LoggedUserDetails(String id,String role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserAuthority userAuthority = UserAuthority.builder()
            .authority(role)
            .build();
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
