package com.triget.application.domain.product.attraction;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "attraction")
public class AttractionProduct {
    @Id
    private String _id;
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
    private int price;
    @Field("currency_code")
    private String currencyCode;
    @Field("estimated_time")
    private int estimatedTime;
    private float rating;
    private int popularity;
    @Field("detail_url")
    private String detailUrl;
    @Field("product_website")
    private String productWebsite;
    @Field("weekday_hours")
    private String weekdayHours;
    private String neighbors;

    @Builder
    public AttractionProduct(String _id, String tripadvisorId, String name, String thumbnail, String subcategory,
                             float longitude, float latitude, String city, String state, String country,
                             String address, int price, String currencyCode, int estimatedTime, float rating,
                             int popularity, String detailUrl, String productWebsite, String weekdayHours, String neighbors)
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
        this.price = price;
        this.currencyCode = currencyCode;
        this.estimatedTime = estimatedTime;
        this.rating = rating;
        this.popularity = popularity;
        this.detailUrl = detailUrl;
        this.productWebsite = productWebsite;
        this.weekdayHours = weekdayHours;
        this.neighbors = neighbors;

    }
}
