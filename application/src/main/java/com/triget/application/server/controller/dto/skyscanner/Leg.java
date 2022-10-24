package com.triget.application.server.controller.dto.skyscanner;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;
@Getter
public class Leg {
    private String id;
    private TmpObject origin;
    private TmpObject destination;
    private int durationInMinutes;
    private int stopCount;
    private Boolean isSmallestStops;
    private String departure;
    private String arrival;
    private int timeDeltaInDays;
    @Nullable
    private List<String> airportChangeIn;
    private TmpObject carriers;
    private List<Segment> segments;
}
