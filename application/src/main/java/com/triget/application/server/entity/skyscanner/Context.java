package com.triget.application.server.entity.skyscanner;

import lombok.Getter;

@Getter
public class Context {
    private String status;
    private String sessionId;
    private int totalResults;
}
