package com.ku.gateway.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.AuthenticationUserDto;
import com.ku.gateway.security.jwt.JwtUser;
import com.ku.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private String usersUrl;

    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var stringUser = userService.findByUsername(username);
        try {
            var user = objectMapper.readValue(stringUser, AuthenticationUserDto.class);
            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }
            JwtUser jwtUser = JwtUser.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles()).build();
            log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
            return jwtUser;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Value("${ku-gateway.users-url}")
    public void setUsersUrl(String usersUrl) {
        this.usersUrl = usersUrl;
    }
}
