package com.triget.application.web.dto;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class EntireProductListResponseDto {
    private String journeyId;
    private float flightsBudget;
    private float accommodationsBudget;
    private float restaurantsBudget;
    private float attractionsBudget;
    private List<Accommodation> accommodations;
    private List<Restaurant> restaurants;
    private List<Attraction> attractions;

    @Builder
    public EntireProductListResponseDto(String journeyId, float flightsBudget, float accommodationsBudget,
                                        float restaurantsBudget, float attractionsBudget, List<Accommodation> accommodations,
                                        List<Restaurant> restaurants, List<Attraction> attractions)
    {
        this.journeyId = journeyId;
        this.flightsBudget = flightsBudget;
        this.accommodationsBudget = accommodationsBudget;
        this.restaurantsBudget = restaurantsBudget;
        this.attractionsBudget = attractionsBudget;
        this.accommodations = accommodations;
        this.restaurants = restaurants;
        this.attractions = attractions;
    }
}
