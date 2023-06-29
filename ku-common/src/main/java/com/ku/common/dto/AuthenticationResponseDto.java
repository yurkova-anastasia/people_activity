package com.ku.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class AuthenticationResponseDto {

    @Schema(description = "Authorities", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<AuthorityDto> authorities;
}
