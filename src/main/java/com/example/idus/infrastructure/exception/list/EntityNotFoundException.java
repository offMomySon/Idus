package com.example.idus.infrastructure.exception.list;


import com.example.idus.infrastructure.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}