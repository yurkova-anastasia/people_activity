package com.ku.gateway.security.jwt;

import com.ku.common.dto.AuthenticationAuthorityDto;
import com.ku.common.dto.AuthenticationRoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Set<AuthenticationRoleDto> roles;
    private LocalDateTime insertedDateAtUtc;

    private LocalDateTime updatedDateAtUtc;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<AuthenticationAuthorityDto> authorities = new HashSet<>();
        roles.forEach(role -> authorities.addAll(role.getAuthorities()));

        return authorities.stream()
                .map(authority -> (GrantedAuthority) authority::getAuthorityName)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Object getId() {
        return id;
    }
}