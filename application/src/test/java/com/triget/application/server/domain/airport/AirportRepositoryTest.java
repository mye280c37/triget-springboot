package com.triget.application.server.domain.airport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AirportRepositoryTest {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void testFindByContainsPlace() {
        String place = "도쿄";

        System.out.print(airportRepository.findByNameContainsString(place).size());
    }
}
