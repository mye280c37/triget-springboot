package com.triget.application.server.entity;

import com.mongodb.lang.Nullable;
import com.triget.application.server.entity.flight.CustomFlightPage;
import com.triget.application.server.entity.flight.FlightResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
public class ProductRecommendationResponse {
    private String journeyId;
    private float flightsBudget;
    private float accommodationsBudget;
    private float restaurantsBudget;
    private float attractionsBudget;
    private CustomFlightPage flights;
    private CustomProductPage accommodations;
    private CustomProductPage restaurants;
    private CustomProductPage attractions;

    @Builder
    public ProductRecommendationResponse(String journeyId, float flightsBudget, float accommodationsBudget,
                                         float restaurantsBudget, float attractionsBudget,
                                         @Nullable CustomFlightPage flights, CustomProductPage accommodations,
                                         CustomProductPage restaurants, CustomProductPage attractions)
    {
        this.journeyId = journeyId;
        this.flightsBudget = flightsBudget;
        this.accommodationsBudget = accommodationsBudget;
        this.restaurantsBudget = restaurantsBudget;
        this.attractionsBudget = attractionsBudget;
        //this.flights = flights;
        this.flights = CustomFlightPage.builder()
                .content(new ArrayList<FlightResponse>())
                .last(true)
                .numberOfElements(0)
                .empty(true)
                .build();
        this.accommodations = accommodations;
        this.restaurants = restaurants;
        this.attractions = attractions;
    }
}
