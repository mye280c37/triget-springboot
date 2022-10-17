package com.triget.application.server.domain.flight;

import com.triget.application.server.domain.airline.Airline;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "segment")
public class FlightSegment {
    @Field("skyscanner_id")
    private String skyScannerId;
    private int order;
    private String origin;
    private String destination;
    @Field("departure_time")
    private String departure;
    @Field("arrival_time")
    private String arrival;
    @Field("duration_in_minutes")
    private int durationInMinutes;
    @Field("flight_number")
    private String flightNumber;
    private Airline operation;

    @Builder
    public FlightSegment(String skyScannerId, int order, String origin, String destination, String departure,
                         String arrival, int durationInMinutes, String flightNumber, Airline operation)
    {
        this.skyScannerId = skyScannerId;
        this.order = order;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.durationInMinutes = durationInMinutes;
        this.flightNumber = flightNumber;
        this.operation = operation;
    }
}
