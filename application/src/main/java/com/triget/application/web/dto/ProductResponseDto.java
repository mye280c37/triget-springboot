package com.triget.application.web.dto;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductResponseDto {

    private String id;
    private String name;
    private String thumbnail;
    private String subcategory;
    private float longitude;
    private float latitude;
    private String city;
    private String state;
    @Nullable
    private String country;
    private String address;
    @Nullable
    private float price;
    @Nullable
    private String currencyCode;
    private float rating;
    private int popularity;
    private String detailUrl;
    @Nullable
    private String productWebsite;
    @Nullable
    private String weekdayHours;
    @Nullable
    private String neighbors;
    private List<String> keywords;

    @Builder
    public ProductResponseDto(String id, String name, String thumbnail, String subcategory,
                              float longitude, float latitude, String city, String state, @Nullable String country,
                              String address, float price, String currencyCode, float rating, int popularity,
                              String detailUrl, @Nullable String productWebsite, @Nullable String weekdayHours,
                              @Nullable String neighbors, List<String> keywords)
    {
        this.id = id;
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
        this.rating = rating;
        this.popularity = popularity;
        this.detailUrl = detailUrl;
        this.productWebsite = productWebsite;
        this.weekdayHours = weekdayHours;
        this.neighbors = neighbors;
        this.keywords = keywords;
    }

    public ProductResponseDto(Accommodation accommodation) {
        this.id = accommodation.get_id().toString();
        this.name = accommodation.getName();
        this.thumbnail = accommodation.getThumbnail();
        this.subcategory = accommodation.getSubcategory();
        this.longitude = accommodation.getLongitude();
        this.latitude = accommodation.getLatitude();
        this.city = accommodation.getCity();
        this.state = accommodation.getState();
        this.address = accommodation.getAddress();
        this.price = accommodation.getPrice();
        this.currencyCode = accommodation.getCurrencyCode();
        this.rating = accommodation.getRating();
        this.popularity = accommodation.getPopularity();
        this.detailUrl = accommodation.getDetailUrl();
        this.keywords = accommodation.getKeywords();
    }

    public ProductResponseDto(Restaurant restaurant) {
        this.id = restaurant.get_id().toString();
        this.name = restaurant.getName();
        this.thumbnail = restaurant.getThumbnail();
        this.subcategory = restaurant.getSubcategory();
        this.longitude = restaurant.getLongitude();
        this.latitude = restaurant.getLatitude();
        this.city = restaurant.getCity();
        this.state = restaurant.getState();
        this.country = restaurant.getCountry();
        this.address = restaurant.getAddress();
        this.price = restaurant.getPrice();
        this.currencyCode = restaurant.getCurrencyCode();
        this.rating = restaurant.getRating();
        this.popularity = restaurant.getPopularity();
        this.detailUrl = restaurant.getDetailUrl();
        this.productWebsite = restaurant.getProductWebsite();
        this.weekdayHours = restaurant.getWeekdayHours();
        this.keywords = restaurant.getKeywords();
    }

    public ProductResponseDto(Attraction attraction) {
        this.id = attraction.get_id().toString();
        this.name = attraction.getName();
        this.thumbnail = attraction.getThumbnail();
        this.subcategory = attraction.getSubcategory();
        this.longitude = attraction.getLongitude();
        this.latitude = attraction.getLatitude();
        this.city = attraction.getCity();
        this.state = attraction.getState();
        this.country = attraction.getCountry();
        this.address = attraction.getAddress();
        this.rating = attraction.getRating();
        this.popularity = attraction.getPopularity();
        this.detailUrl = attraction.getDetailUrl();
        this.productWebsite = attraction.getProductWebsite();
        this.weekdayHours = attraction.getWeekdayHours();
        this.neighbors = attraction.getNeighbors();
        this.keywords = attraction.getKeywords();
    }
}
