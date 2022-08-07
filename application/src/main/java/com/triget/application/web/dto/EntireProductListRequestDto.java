package com.triget.application.web.dto;

import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.theme.JourneyTheme;
import lombok.Builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public String getTheme() {
        return theme;
    }

    public Journey toEntity(JourneyTheme theme) throws ParseException {
        int priorSum = flightsPrior + accommodationsPrior + restaurantsPrior + attractionsPrior;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Journey.builder()
                .place(place)
                .theme(theme)
                .peopleNum(peopleNum)
                .departureDateTime(dateFormat.parse(departureDate))
                .arrivalDateTime(dateFormat.parse(arrivalDate))
                .departureAirport(departureAirport)
                .budget(budget)
                .flightsPriority(flightsPrior)
                .accommodationsPriority(accommodationsPrior)
                .restaurantsPriority(restaurantsPrior)
                .attractionsPriority(attractionsPrior)
                .flightsBudget((float) (flightsPrior/priorSum)*budget)
                .accommodationsBudget((float) (accommodationsPrior/priorSum)*budget)
                .restaurantsBudget((float) (restaurantsPrior/priorSum)*budget)
                .attractionsBudget((float) (attractionsPrior/priorSum)*budget)
                .build();
    }
}
