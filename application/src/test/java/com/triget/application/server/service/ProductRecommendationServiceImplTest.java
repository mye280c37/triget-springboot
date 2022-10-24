package com.triget.application.server.service;

import com.triget.application.server.domain.accommodation.AccommodationRepository;
import com.triget.application.server.domain.attraction.AttractionRepository;
import com.triget.application.server.domain.journey.JourneyRepository;
import com.triget.application.server.domain.restaurant.RestaurantRepository;
import com.triget.application.server.domain.theme.JourneyThemeRepository;
import com.triget.application.server.controller.dto.EntireProductListRequestDto;
import com.triget.application.server.controller.dto.EntireProductListResponseDto;
import com.triget.application.server.controller.dto.ProductPageResponseDto;
import com.triget.application.server.controller.dto.ProductResponseDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRecommendationServiceImplTest {

    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private JourneyThemeRepository journeyThemeRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AttractionRepository attractionRepository;
    @Autowired
    private ProductRecommendationService productListServiceImpl;
    private String id;

    @AfterAll
    public void deleteAll() {
        journeyRepository.deleteById(new ObjectId(this.id));
    }

    @Test
    public void testSaveRequestDto() throws ParseException {
        EntireProductListRequestDto entireProductListRequestDto = EntireProductListRequestDto.builder()
                .place("Tokyo")
                .theme("테마")
                .peopleNum(2)
                .departureDate("2022-09-01")
                .arrivalDate("2022-09-03")
                .budget(4000000)
                .flightsPrior(2)
                .accommodationsPrior(5)
                .restaurantsPrior(4)
                .attractionsPrior(3)
                .build();
        id=productListServiceImpl.saveRequestDto(entireProductListRequestDto);
        System.out.print(id);
    }

    @Test
    public void setResponseTest() throws Exception {
        EntireProductListRequestDto entireProductListRequestDto = EntireProductListRequestDto.builder()
                .place("Tokyo")
                .theme("테마")
                .peopleNum(2)
                .departureDate("2022-09-01")
                .arrivalDate("2022-09-03")
                .budget(4000000)
                .flightsPrior(2)
                .accommodationsPrior(5)
                .restaurantsPrior(4)
                .attractionsPrior(3)
                .build();

        id = productListServiceImpl.saveRequestDto(entireProductListRequestDto);
        EntireProductListResponseDto entireProductListResponseDto = productListServiceImpl.setResponseDto(id);
        float margin = (float) 0.03*4000000;
        float accommodationBudget = entireProductListResponseDto.getAccommodationsBudget();
        System.out.printf("min: %f ~ max: %f\n", accommodationBudget-margin, accommodationBudget+margin);
        ProductPageResponseDto accommodationPage = entireProductListResponseDto.getAccommodations();
        for(ProductResponseDto item : accommodationPage.getContent()){
            System.out.printf("name: %s, price:%f, rating: %f, popularity: %d\n", item.getName(), item.getPrice(), item.getRating(), item.getPopularity());
        }
    }
}
