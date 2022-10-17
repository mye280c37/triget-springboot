package com.triget.application.server.domain.attraction;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "attraction")
public class Attraction {
    @Id
    private ObjectId _id;
    @Field("tripadvisor_id")
    private String tripadvisorId;
    private String name;
    private String thumbnail;
    private String subcategory;
    private float longitude;
    private float latitude;
    private String city;
    private String state;
    private String country;
    private String address;
    private float rating;
    private int popularity;
    @Field("detail_url")
    private String detailUrl;
    @Field("product_website")
    private String productWebsite;
    @Field("weekday_hours")
    private String weekdayHours;
    private String neighbors;
    private List<String> keywords;

    @Builder
    public Attraction(ObjectId _id, String tripadvisorId, String name, String thumbnail, String subcategory,
                      float longitude, float latitude, String city, String state, String country,
                      String address, float rating, int popularity, String detailUrl, String productWebsite,
                      String weekdayHours, String neighbors, List<String> keywords)
    {
        this._id = _id;
        this.tripadvisorId = tripadvisorId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.subcategory = subcategory;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.rating = rating;
        this.popularity = popularity;
        this.detailUrl = detailUrl;
        this.productWebsite = productWebsite;
        this.weekdayHours = weekdayHours;
        this.neighbors = neighbors;
        this.keywords = keywords;

    }
}
