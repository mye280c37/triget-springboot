package com.triget.application.server.web.dto.flight;

import com.triget.application.server.domain.flight.FlightSegment;
import com.triget.application.server.web.dto.AirlineResponseDto;
import com.triget.application.server.web.dto.AirportResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FlightSegmentResponseDto {
    private int order;
    private AirportResponseDto origin;
    private AirportResponseDto destination;
    private String departure;
    private String arrival;
    private int durationInMinutes;
    private String flightNumber;
    private AirlineResponseDto operation;

    @Builder
    public FlightSegmentResponseDto(FlightSegment segment, AirportResponseDto origin, AirportResponseDto destination,
                                    AirlineResponseDto operation) {
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
