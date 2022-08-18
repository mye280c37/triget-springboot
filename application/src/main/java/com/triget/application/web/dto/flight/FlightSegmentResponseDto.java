package com.triget.application.web.dto.flight;

import com.triget.application.domain.airline.Airline;
import com.triget.application.domain.flight.FlightSegment;
import com.triget.application.web.dto.AirlineResponseDto;
import com.triget.application.web.dto.AirportResponseDto;
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
