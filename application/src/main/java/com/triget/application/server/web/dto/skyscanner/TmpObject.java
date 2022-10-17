package com.triget.application.server.web.dto.skyscanner;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
public class TmpObject {
    @Nullable
    private String flightPlaceId;
    @Nullable
    private String id;
    @Nullable
    private String displayCode;
    @Nullable
    private float raw;
    @Nullable
    private List<Operation> marketing;
}
