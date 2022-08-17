package com.triget.application.web.dto.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class Segment {
    private String id;
    private TmpObject origin;
    private TmpObject destination;
    private String departure;
    private String arrival;
    private int durationInMinutes;
    private String flightNumber;
    private Operation operatingCarrier;
}
