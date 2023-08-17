package com.ku.devices.exception;

import lombok.Data;

@Data
public class DtoMappingException extends RuntimeException {

    public DtoMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
