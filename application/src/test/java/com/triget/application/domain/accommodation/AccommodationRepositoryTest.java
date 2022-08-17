package com.triget.application.domain.accommodation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationRepositoryTest {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    public void testCityAndPriceBetween() {
        String city = "Tokyo";
        float priceFrom = 9700;
        float priceTo = 12000;
        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );

        Page<Accommodation> accommodationPage = accommodationRepository.findByCityAndPriceBetween(city, priceFrom, priceTo, pageRequest);
        int pageSize = accommodationPage.getNumberOfElements();

        System.out.printf("\n\nThe Number of Elements: %d\n\n", pageSize);
        for(Accommodation item : accommodationPage){
            System.out.printf("price: %5.3f, rating: %f, popularity: %d\n", item.getPrice(), item.getRating(), item.getPopularity());
        }

    }

    private Sort sortByPrice() {
        return Sort.by(Sort.DEFAULT_DIRECTION, "price");
    }

    @Test
    public void testFindAllByCity() {
        String city = "Tokyo";
        List<Accommodation> accommodationList = accommodationRepository.findAllByCity(
                city,
                sortByPrice());
        for(Accommodation item : accommodationList){
            System.out.printf("name: %s, rating: %f, popularity: %d\n", item.getName(), item.getRating(), item.getPopularity());
        }
    }

    @Test
    public void testFindByCityAndKeywordAndPriceLess() {
        String city = "Tokyo";
        float priceTo = 10000;
        String theme = "relaxing";
        List<Accommodation> accommodationList = accommodationRepository.findByCityAndKeywordsAndPriceLess(
                city,
                priceTo,
                theme
        );
        for(Accommodation item : accommodationList){
            System.out.printf("name: %s, rating: %f, popularity: %d\n", item.getName(), item.getRating(), item.getPopularity());
        }
    }
}
