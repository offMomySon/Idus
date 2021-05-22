package com.example.idus.infrastructure.exception.list;

import com.example.idus.infrastructure.exception.ErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}