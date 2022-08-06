package com.triget.application.domain.airline;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Airline {
    @Id
    private ObjectId _id;
    private String name;
    @Field("logo_url")
    private String logoUrl;

    @Builder
    public Airline(ObjectId id, String name, String logoUrl) {
        this._id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }
}
