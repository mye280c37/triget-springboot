package com.triget.application.domain.accommodation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationRepositoryTest {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    public void returnListPriceBetween() {
        int priceFrom = 9700;
        int priceTo = 9800;
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Accommodation> accommodationPage = accommodationRepository.findByPriceBetween(priceFrom, priceTo, pageRequest);
        int pageSize = accommodationPage.getNumberOfElements();

        System.out.print(String.format("\n\nThe Number of Elements: %d\n\n", pageSize));
    }
}
