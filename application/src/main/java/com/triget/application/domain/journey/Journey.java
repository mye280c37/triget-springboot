package com.triget.application.domain.journey;

import com.triget.application.domain.journey.theme.JourneyTheme;

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
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date departureDateTime;
    @DateTimeFormat(iso = ISO.DATE_TIME)
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

}
