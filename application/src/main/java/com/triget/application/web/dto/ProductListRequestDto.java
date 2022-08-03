package com.triget.application.web.dto;

import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.theme.JourneyTheme;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ProductListRequestDto {

    private String place;
    private String theme;
    private int peopleNum;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date departureDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date arrivalDateTime;
    private String departureAirport;
    private int budget;
    private int flightsPriority;
    private int accommodationsPriority;
    private int restaurantsPriority;
    private int attractionsPriority;

    @Builder
    public ProductListRequestDto(String place, String theme, int peopleNum, Date departureDateTime, Date arrivalDateTime,
                                 String departureAirport, int budget, int flightsPriority, int accommodationsPriority,
                                 int restaurantsPriority, int attractionsPriority) {
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
    }

    public String getTheme() {
        return theme;
    }

    public Journey toEntity(JourneyTheme theme){
        return Journey.builder()
                .place(place)
                .theme(theme)
                .peopleNum(peopleNum)
                .departureDateTime(departureDateTime)
                .arrivalDateTime(arrivalDateTime)
                .departureAirport(departureAirport)
                .budget(budget)
                .flightsPriority(flightsPriority)
                .accommodationsPriority(accommodationsPriority)
                .restaurantsPriority(restaurantsPriority)
                .attractionsPriority(attractionsPriority)
                .flightsBudget(0)
                .accommodationsBudget(0)
                .restaurantsBudget(0)
                .attractionsBudget(0)
                .build();
    }
}
