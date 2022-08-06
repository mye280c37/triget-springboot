package com.triget.application.domain.restaurant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testFindAllByCity() {
        String city = "Tokyo";
        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );

        Page<Restaurant> restaurantPage = restaurantRepository.findAllByCity(city, pageRequest);
        int pageSize = restaurantPage.getNumberOfElements();

        System.out.printf("\n\nThe Number of Elements: %d\n\n", pageSize);
        for(Restaurant item : restaurantPage){
            System.out.printf("name: %s, rating: %f, popularity: %d\n", item.getName(), item.getRating(), item.getPopularity());
        }

    }
}
