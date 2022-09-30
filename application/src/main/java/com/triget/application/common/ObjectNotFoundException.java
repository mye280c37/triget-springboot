package com.triget.application.common;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {
    ErrorCode errorCode = ErrorCode.OBJ_NOT_FOUND;
    public ObjectNotFoundException(String message){
        super(message);
    }
}
