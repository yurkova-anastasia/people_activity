package com.ku.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
