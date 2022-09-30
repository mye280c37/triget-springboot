package com.triget.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    OBJ_NOT_FOUND(404,"OBJ_NOT_FOUND"),
    VALIDATION_FAILURE(400, "VALIDATION_FAILURE")
    ;

    private int status;
    private String errorContent;
}