package com.triget.application.server.controller.dto.flight;

import com.mongodb.lang.Nullable;
import com.triget.application.server.domain.product.flight.FlightLeg;
import com.triget.application.server.controller.dto.AirlineResponse;
import com.triget.application.server.controller.dto.AirportResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class FlightLegResponse {
    private AirportResponse origin;
    private AirportResponse destination;
    private String departure;
    private String arrival;
    private int durationInMinutes;
    private int timeDeltaInDays;
    private int stopCount;
    private Boolean isSmallestStops;
    private List<AirlineResponse> operations;
    @Nullable
    private List<String> airportChangeIn;
    @Nullable
    private List<FlightSegmentResponse> segments;

    @Builder
    public FlightLegResponse(FlightLeg leg, AirportResponse origin, AirportResponse destination,
                             List<AirlineResponse> operations, List<FlightSegmentResponse> flightSegments){
        this.origin = origin;
        this.destination = destination;
        this.departure = leg.getDeparture();
        this.arrival = leg.getArrival();
        this.durationInMinutes = leg.getDurationInMinutes();
        this.timeDeltaInDays = leg.getTimeDeltaInDays();
        this.stopCount = leg.getStopCount();
        this.isSmallestStops = leg.getIsSmallestStops();
        this.operations = operations;
        this.airportChangeIn = leg.getAirportChangeIn();
        this.segments = flightSegments;
    }
}
