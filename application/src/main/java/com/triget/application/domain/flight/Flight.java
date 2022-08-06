package com.triget.application.domain.flight;

import com.mongodb.lang.Nullable;
import com.triget.application.domain.airline.Airline;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

public class Flight {
    @Id
    private ObjectId _id;
    @Field("skyscanner_id")
    private String skyScannerId;
    private String origin;
    private String destination;
    @Field("departure_time")
    private Date departureTime;
    @Field("arrival_time")
    private Date arrivalTime;
    @Field("duration_in_minutes")
    private int durationInMinutes;
    @Field("time_delta_in_days")
    private int timeDeltaInDays;
    @Field("stop_count")
    private int stopCount;
    @Nullable
    @Field("is_smallest_stops")
    private Boolean isSmallestStops;
    private List<Airline> operatings;
    @Nullable
    @Field("airport_change_in")
    private List<String> airportChangeIn;
    @Nullable
    private List<Flight> segments;

    @Builder
    public Flight(ObjectId _id, String skyScannerId, String origin, String destination, Date departureTime,
                  Date arrivalTime, int durationInMinutes, int timeDeltaInDays, int stopCount,
                  @Nullable Boolean isSmallestStops, List<Airline> operatings, @Nullable List<String> airportChangeIn,
                  @Nullable List<Flight> segments)
    {
        this._id = _id;
        this.skyScannerId = skyScannerId;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationInMinutes = durationInMinutes;
        this.timeDeltaInDays = timeDeltaInDays;
        this.stopCount = stopCount;
        this.isSmallestStops = isSmallestStops;
        this.operatings = operatings;
        this.airportChangeIn = airportChangeIn;
        this.segments = segments;
    }
}
