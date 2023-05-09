package com.ku.common.dto;

import com.ku.common.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserResponseDto {

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Anastasia")
    private String name;

    @Schema(description = "Surname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yurkova")
    private String surname;

    @Schema(description = "Age", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer age;

    @Schema(description = "Gender", requiredMode = Schema.RequiredMode.REQUIRED, example = "FEMALE")
    private Gender gender;

    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED, example = "nst.yrk")
    private String username;

    @Schema(description = "Inserted date at utc", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2022-01-01 00:00:00.000")
    private LocalDateTime insertedDateAtUtc;
}
