package com.tinqinacademy.bff.core.config.securityconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@ToString
@Builder
public class UserAuthority implements GrantedAuthority {

    private String authority;

    public UserAuthority(String authority) {
        this.authority = "ROLE_"+ authority.toUpperCase();}


    @Override
    public String getAuthority() {
        return authority;
    }
}
