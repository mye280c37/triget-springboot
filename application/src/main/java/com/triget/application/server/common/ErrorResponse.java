package com.triget.application.server.common;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String errorContent;
    private List<String> messages;

    public ErrorResponse(int status, String errorContent, String messages) {
        this.status = status;
        this.errorContent = errorContent;
        this.messages = new ArrayList<>();
        this.messages.add(messages);
    }

    public ErrorResponse(int status, String errorContent, List<String> messages) {
        this.status = status;
        this.errorContent = errorContent;
        this.messages = messages;
    }
}