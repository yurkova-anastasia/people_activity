package com.ku.gateway.security.jwt;

import com.ku.common.dto.AuthenticationRoleDto;
import com.ku.common.dto.AuthenticationUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(AuthenticationUserDto user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getInsertedDateAtUtc(),
                user.getUpdatedDateAtUtc()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<AuthenticationRoleDto> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
