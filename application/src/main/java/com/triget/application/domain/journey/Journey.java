package com.triget.application.domain.journey;

import com.triget.application.domain.theme.JourneyTheme;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Document(collection = "journey")
public class Journey {

    @Id
    private ObjectId id;
    private String place;
    private JourneyTheme theme;
    @Field("people_num")
    private int peopleNum;
    @DateTimeFormat(iso = ISO.DATE)
    @Field("departure_date")
    private Date departureDateTime;
    @DateTimeFormat(iso = ISO.DATE)
    @Field("arrival_date")
    private Date arrivalDateTime;
    @Field("departure_airport")
    private String departureAirport;
    private int budget;
    @Field("flights_prior")
    private int flightsPriority;
    @Field("accommodations_prior")
    private int accommodationsPriority;
    @Field("restaurants_prior")
    private int restaurantsPriority;
    @Field("attractions_prior")
    private int attractionsPriority;
    @Field("flights_budget")
    private int flightsBudget;
    @Field("accommodations_budget")
    private int accommodationsBudget;
    @Field("restaurants_budget")
    private int restaurantsBudget;
    @Field("attractions_budget")
    private int attractionsBudget;

    @Builder
    public Journey(String place, JourneyTheme theme, int peopleNum, Date departureDateTime,
                   Date arrivalDateTime, String departureAirport, int budget, int flightsPriority,
                   int accommodationsPriority, int restaurantsPriority, int attractionsPriority,
                   int flightsBudget, int accommodationsBudget, int restaurantsBudget, int attractionsBudget){
        this.place = place;
        this.theme = theme;
        this.peopleNum = peopleNum;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.departureAirport = departureAirport;
        this.budget = budget;
        this.flightsPriority = flightsPriority;
        this.accommodationsPriority = accommodationsPriority;
        this.restaurantsPriority = restaurantsPriority;
        this.attractionsPriority = attractionsPriority;
        this.flightsBudget = flightsBudget;
        this.accommodationsBudget = accommodationsBudget;
        this.restaurantsBudget = restaurantsBudget;
        this.attractionsBudget = attractionsBudget;
    }

}
