package com.triget.application.web.dto;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class EntireProductListResponseDto {
    private String journeyId;
    private float flightsBudget;
    private float accommodationsBudget;
    private float restaurantsBudget;
    private float attractionsBudget;
    private List<Flight> flights;
    private List<Accommodation> accommodations;
    private List<Restaurant> restaurants;
    private List<Attraction> attractions;

    @Builder
    public EntireProductListResponseDto(String journeyId, float flightsBudget, float accommodationsBudget,
                                        float restaurantsBudget, float attractionsBudget,
                                        List<Flight> flights, List<Accommodation> accommodations,
                                        List<Restaurant> restaurants, List<Attraction> attractions)
    {
        this.journeyId = journeyId;
        this.flightsBudget = flightsBudget;
        this.accommodationsBudget = accommodationsBudget;
        this.restaurantsBudget = restaurantsBudget;
        this.attractionsBudget = attractionsBudget;
        this.flights = flights;
        this.accommodations = accommodations;
        this.restaurants = restaurants;
        this.attractions = attractions;
    }
}
