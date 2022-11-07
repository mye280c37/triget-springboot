package com.triget.application.server.domain.schedule;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("route")
public class Route {
    @Id
    private ObjectId _id;
    @Field("departure_time")
    private String departureTime;
    @Field("arrival_time")
    private String arrivalTime;
    @Field("transit_mode")
    private String transitMode;
    @Field("duration_minute")
    private int durationMinute;
}
