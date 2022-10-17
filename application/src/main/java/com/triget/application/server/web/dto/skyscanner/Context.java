package com.triget.application.server.web.dto.skyscanner;

import lombok.Getter;

@Getter
public class Context {
    private String status;
    private String sessionId;
    private int totalResults;
}
