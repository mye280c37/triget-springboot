package com.triget.application.web.dto;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor
public class EntireProductListResponseDto {
    private ObjectId journeyId;
    private float flightsBudget;
    private float accommodationsBudget;
    private float restaurantsBudget;
    private float attractionsBudget;
    private Page<Accommodation> accommodations;
    private Page<Restaurant> restaurants;
    private Page<Attraction> attractions;

    @Builder
    public EntireProductListResponseDto(ObjectId journeyId, float flightsBudget, float accommodationsBudget,
                                        float restaurantsBudget, float attractionsBudget, Page<Accommodation> accommodations,
                                        Page<Restaurant> restaurants, Page<Attraction> attractions)
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
