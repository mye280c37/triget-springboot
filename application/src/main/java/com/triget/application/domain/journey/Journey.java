package com.triget.application.domain.journey;

import com.triget.application.domain.theme.JourneyTheme;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Document(collection = "journey")
public class Journey {

    @Id
    private String id;
    private String place;
    private JourneyTheme theme;
    private int peopleNum;
    @DateTimeFormat(iso = ISO.DATE)
    private Date departureDateTime;
    @DateTimeFormat(iso = ISO.DATE)
    private Date arrivalDateTime;
    private String departureAirport;
    private int budget;
    private int flightsPriority;
    private int accommodationsPriority;
    private int restaurantsPriority;
    private int attractionsPriority;
    private int flightsBudget;
    private int accommodationsBudget;
    private int restaurantsBudget;
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
