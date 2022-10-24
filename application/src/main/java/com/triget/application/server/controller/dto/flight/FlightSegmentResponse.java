package com.triget.application.server.controller.dto.flight;

import com.triget.application.server.domain.product.flight.FlightSegment;
import com.triget.application.server.controller.dto.AirlineResponse;
import com.triget.application.server.controller.dto.AirportResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FlightSegmentResponse {
    private int order;
    private AirportResponse origin;
    private AirportResponse destination;
    private String departure;
    private String arrival;
    private int durationInMinutes;
    private String flightNumber;
    private AirlineResponse operation;

    @Builder
    public FlightSegmentResponse(FlightSegment segment, AirportResponse origin, AirportResponse destination,
                                 AirlineResponse operation) {
        this.order = segment.getOrder();
        this.origin = origin;
        this.destination = destination;
        this.departure = segment.getDeparture();
        this.arrival = segment.getArrival();
        this.durationInMinutes = segment.getDurationInMinutes();
        this.flightNumber = segment.getFlightNumber();
        this.operation = operation;
    }
}
