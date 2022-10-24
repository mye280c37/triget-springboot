package com.triget.application.server.controller.dto.flight;

import com.mongodb.lang.Nullable;
import com.triget.application.server.domain.flight.FlightLeg;
import com.triget.application.server.controller.dto.AirlineResponseDto;
import com.triget.application.server.controller.dto.AirportResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class FlightLegResponseDto {
    private AirportResponseDto origin;
    private AirportResponseDto destination;
    private String departure;
    private String arrival;
    private int durationInMinutes;
    private int timeDeltaInDays;
    private int stopCount;
    private Boolean isSmallestStops;
    private List<AirlineResponseDto> operations;
    @Nullable
    private List<String> airportChangeIn;
    @Nullable
    private List<FlightSegmentResponseDto> segments;

    @Builder
    public FlightLegResponseDto(FlightLeg leg,AirportResponseDto origin, AirportResponseDto destination,
                                List<AirlineResponseDto> operations, List<FlightSegmentResponseDto> flightSegments){
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
