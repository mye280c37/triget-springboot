package com.triget.application.server.domain.place;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    public void testFindByDisplayName() {
        String place = "도쿄";
        String searchName = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse("");
        System.out.print(searchName);
    }
}
