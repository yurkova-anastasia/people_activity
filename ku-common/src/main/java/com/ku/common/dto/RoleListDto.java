package com.ku.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RoleListDto {

    @Schema(description = "Role id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long id;

    @Schema(description = "Role name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Admin")
    private String role_name;

    @Schema(description = "Inserted date at utc", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-01-01 00:00:00.000")
    private LocalDateTime insertedDateAtUtc;

    @Schema(description = "Updated date at utc", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-01-01 00:00:00.000")
    private LocalDateTime  updatedDateAtUtc;
}
