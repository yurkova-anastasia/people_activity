package com.ku.devices.exception;

import lombok.Data;

@Data
public class ConsumerException extends RuntimeException {

    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
