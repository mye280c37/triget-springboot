package com.triget.application.server.domain.flight;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "flight")
public class Flight {
    @Id
    private ObjectId _id;
    @Field("journey_id")
    private String journeyId;
    @Field("skyscanner_id")
    private String skyScannerId;
    private float price;
    private List<FlightLeg> legs;
    private float score;
    private String type;
    private int totalStopCounts;
    @Field("detail_url")
    private String detailUrl;

    @Builder
    public Flight(String journeyId, String skyScannerId, float price, List<FlightLeg> legs, float score,
                  String type, int totalStopCounts, String detailUrl)
    {
        this.journeyId = journeyId;
        this.skyScannerId = skyScannerId;
        this.price = price;
        this.legs = legs;
        this.score = score;
        this.type = type;
        this.totalStopCounts = totalStopCounts;
        this.detailUrl = detailUrl;
    }
}
