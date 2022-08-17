package com.triget.application.web.dto.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class Context {
    private String status;
    private String sessionId;
    private int totalResults;
}
