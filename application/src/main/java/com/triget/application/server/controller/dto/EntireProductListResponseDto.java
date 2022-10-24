package com.triget.application.server.controller.dto;

import com.mongodb.lang.Nullable;
import com.triget.application.server.controller.dto.flight.FlightPageResponseDto;
import com.triget.application.server.controller.dto.flight.FlightResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
public class EntireProductListResponseDto {
    private String journeyId;
    private float flightsBudget;
    private float accommodationsBudget;
    private float restaurantsBudget;
    private float attractionsBudget;
    private FlightPageResponseDto flights;
    private ProductPageResponseDto accommodations;
    private ProductPageResponseDto restaurants;
    private ProductPageResponseDto attractions;

    @Builder
    public EntireProductListResponseDto(String journeyId, float flightsBudget, float accommodationsBudget,
                                        float restaurantsBudget, float attractionsBudget,
                                        @Nullable FlightPageResponseDto flights, ProductPageResponseDto accommodations,
                                        ProductPageResponseDto restaurants, ProductPageResponseDto attractions)
    {
        this.journeyId = journeyId;
        this.flightsBudget = flightsBudget;
        this.accommodationsBudget = accommodationsBudget;
        this.restaurantsBudget = restaurantsBudget;
        this.attractionsBudget = attractionsBudget;
        //this.flights = flights;
        this.flights = FlightPageResponseDto.builder()
                .content(new ArrayList<FlightResponseDto>())
                .last(true)
                .numberOfElements(0)
                .empty(true)
                .build();
        this.accommodations = accommodations;
        this.restaurants = restaurants;
        this.attractions = attractions;
    }
}
