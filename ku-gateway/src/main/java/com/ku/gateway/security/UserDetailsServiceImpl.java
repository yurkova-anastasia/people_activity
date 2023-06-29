package com.ku.gateway.security;

import com.ku.gateway.security.jwt.UserDetailsImpl;
import com.ku.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username);
        UserDetailsImpl jwtUser = UserDetailsImpl.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRoles()).build();
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
