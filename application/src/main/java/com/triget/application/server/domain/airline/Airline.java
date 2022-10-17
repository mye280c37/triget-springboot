package com.triget.application.server.domain.airline;

import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "airline")
public class Airline {
    @Id
    private ObjectId _id;
    @Field("skyscanner_id")
    private String skyScannerId;
    private String name;
    @Field("logo_url")
    @Nullable
    private String logoUrl;

    @Builder
    public Airline(String skyScannerId,  String name, @Nullable String logoUrl) {
        this.skyScannerId = skyScannerId;
        this.name = name;
        this.logoUrl = logoUrl;
    }
}
