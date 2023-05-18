package com.ku.gateway.controller;

import com.ku.common.dto.AuthenticationRequestDto;
import com.ku.gateway.security.jwt.JwtTokenProvider;
import com.ku.gateway.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto request) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = (JwtUser) authenticate.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenProvider.generateAccessToken(user))
                .body(true);
    }
}