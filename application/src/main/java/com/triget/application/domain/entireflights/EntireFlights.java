package com.triget.application.domain.entireflights;

import com.triget.application.domain.flight.Flight;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class EntireFlights {
    @Id
    private ObjectId _id;
    @Field("skyscanner_id")
    private String skyScannerId;
    private int price;
    private List<Flight> legs;
    private float score;
    private ProductType type;
    @Field("detail_url")
    private String detailUrl;

    @Builder
    public EntireFlights(ObjectId _id, String skyScannerId, int price, List<Flight> legs, float score,
                         ProductType type, String detailUrl)
    {
        this._id = _id;
        this.skyScannerId = skyScannerId;
        this.price = price;
        this.legs = legs;
        this.score = score;
        this.type = type;
        this.detailUrl = detailUrl;
    }
}
