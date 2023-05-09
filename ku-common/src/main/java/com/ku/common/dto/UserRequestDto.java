package com.ku.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRequestDto {

    @Schema(description = "Page number", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
    private Integer pageNumber;

    @Schema(description = "Page size", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10")
    private Integer pageSize;
}
