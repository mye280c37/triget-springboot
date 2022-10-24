package com.triget.application.domain.journey;

import com.triget.application.server.repository.journey.JourneyThemeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JourneyRepositoryTest {

    @Autowired
    JourneyRepository journeyRepository;
    @Autowired
    JourneyThemeRepository journeyThemeRepository;
    private ObjectId id;

    @AfterAll
    public void deleteOne() {
        journeyRepository.deleteById(this.id);
    }

    @Test
    public void create_journey() {

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.id = journeyRepository.save(Journey.builder()
                .place("도쿄")
                .theme("relaxing")
                .peopleNum(3)
                .departureDate(dateFormat.format(today))
                .arrivalDate(dateFormat.format(today))
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

        //when
        Journey journey = journeyRepository.findById(this.id).orElse(null);

        //then
        System.out.print("\n===============execution result======================\n");
        System.out.print(journey);
        System.out.print("\n=====================================================\n");
    }
}
