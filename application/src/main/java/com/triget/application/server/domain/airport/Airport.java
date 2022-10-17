package com.triget.application.server.domain.airport;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "airport")
public class Airport {
    @Id
    private ObjectId _id;
    private String iata;
    private String name;
    private float longitude;
    private float latitude;
    private String cityInEnglish;

    @Builder
    public Airport(String iata, String name, float longitude, float latitude) {
        this.iata = iata;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
