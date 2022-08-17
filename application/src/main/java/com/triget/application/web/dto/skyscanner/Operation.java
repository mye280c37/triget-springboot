package com.triget.application.web.dto.skyscanner;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class Operation {
    private String id;
    @Nullable
    private String logoUrl;
    private String name;
}
