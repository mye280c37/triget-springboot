package com.triget.application.web.dto;

import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.theme.JourneyTheme;
import lombok.Builder;

import java.text.ParseException;

public class EntireProductListRequestDto {

    private final String place;
    private final String theme;
    private final int peopleNum;
    private final String departureDate;
    private final String arrivalDate;
    private final String departureAirport;
    private final int budget;
    private final int flightsPrior;
    private final int accommodationsPrior;
    private final int restaurantsPrior;
    private final int attractionsPrior;

    @Builder
    public EntireProductListRequestDto(String place, String theme, int peopleNum, String departureDate, String arrivalDate,
                                       String departureAirport, int budget, int flightsPrior, int accommodationsPrior,
                                       int restaurantsPrior, int attractionsPrior) {
        this.place = place;
        this.theme = theme;
        this.peopleNum = peopleNum;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureAirport = departureAirport;
        this.budget = budget;
        this.flightsPrior = flightsPrior;
        this.accommodationsPrior = accommodationsPrior;
        this.restaurantsPrior = restaurantsPrior;
        this.attractionsPrior = attractionsPrior;
    }

    public Journey toEntity() {
        int priorSum = flightsPrior + accommodationsPrior + restaurantsPrior + attractionsPrior;
        return Journey.builder()
                .place(place)
                .theme(theme)
                .peopleNum(peopleNum)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .departureAirport(departureAirport)
                .budget(budget)
                .flightsPriority(flightsPrior)
                .accommodationsPriority(accommodationsPrior)
                .restaurantsPriority(restaurantsPrior)
                .attractionsPriority(attractionsPrior)
                .flightsBudget(((float) flightsPrior/priorSum)*budget)
                .accommodationsBudget(((float) accommodationsPrior/priorSum)*budget)
                .restaurantsBudget(((float) restaurantsPrior/priorSum)*budget)
                .attractionsBudget(((float) attractionsPrior/priorSum)*budget)
                .build();
    }
}
