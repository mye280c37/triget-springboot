package com.triget.application.server.service;

import com.triget.application.server.repository.airline.AirlineRepository;
import com.triget.application.server.repository.product.flight.FlightRepository;
import com.triget.application.server.domain.journey.Journey;
import com.triget.application.server.repository.journey.JourneyRepository;
import com.triget.application.server.domain.journey.JourneyTheme;
import com.triget.application.server.repository.journey.JourneyThemeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkyScannerFlightsInterfaceTest {
    
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private FlightRepository entireFlightsRepository;
    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private JourneyThemeRepository journeyThemeRepository;
    @Autowired
    private SkyScannerFlightsInterface skyScannerFlightsInterface;
    private ObjectId id;

    @Test
    public void testAddFlights() {
        JourneyTheme journeyTheme = journeyThemeRepository.findByKoreanName("테마").orElse(null);

        this.id = journeyRepository.save(Journey.builder()
                .place("도쿄 하네다")
                .theme("relaxing")
                .peopleNum(3)
                .departureDate("2022-09-01")
                .arrivalDate("2022-09-03")
                .departureAirport("ICN")
                .budget(1000000)
                .flightsPriority(5)
                .accommodationsPriority(10)
                .restaurantsPriority(20)
                .attractionsPriority(10)
                .flightsBudget(0)
                .accommodationsBudget(0)
                .restaurantsBudget(0)
                .attractionsBudget(0)
                .build()).getId();

        journeyRepository.findById(id).ifPresent(journey -> {
            try {
                skyScannerFlightsInterface.addFlights(journey);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
