package com.triget.application.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
        System.out.print("objectNotFound");
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(
                new ErrorResponse(
                        ex.getErrorCode().getStatus(),
                        ex.getErrorCode().getErrorContent(),
                        ex.getMessage())
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILURE;
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        errorCode.getStatus(),
                        errorCode.getErrorContent(),
                        e.getMessage())
        );
    }
}
