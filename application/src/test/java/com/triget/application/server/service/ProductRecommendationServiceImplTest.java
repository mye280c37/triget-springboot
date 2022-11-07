package com.triget.application.server.service;

import com.triget.application.server.repository.product.AccommodationRepository;
import com.triget.application.server.repository.product.AttractionRepository;
import com.triget.application.server.repository.journey.JourneyRepository;
import com.triget.application.server.repository.product.RestaurantRepository;
import com.triget.application.server.repository.journey.JourneyThemeRepository;
import com.triget.application.server.entity.ProductRecommendationRequest;
import com.triget.application.server.entity.ProductRecommendationResponse;
import com.triget.application.server.entity.CustomProductPage;
import com.triget.application.server.entity.ProductResponse;
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
        ProductRecommendationRequest productRecommendationRequest = ProductRecommendationRequest.builder()
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
        id=productListServiceImpl.saveRequestDto(productRecommendationRequest);
        System.out.print(id);
    }

    @Test
    public void setResponseTest() throws Exception {
        ProductRecommendationRequest productRecommendationRequest = ProductRecommendationRequest.builder()
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

        id = productListServiceImpl.saveRequestDto(productRecommendationRequest);
        ProductRecommendationResponse productRecommendationResponse = productListServiceImpl.setResponseDto(id);
        float margin = (float) 0.03*4000000;
        float accommodationBudget = productRecommendationResponse.getAccommodationsBudget();
        System.out.printf("min: %f ~ max: %f\n", accommodationBudget-margin, accommodationBudget+margin);
        CustomProductPage accommodationPage = productRecommendationResponse.getAccommodations();
        for(ProductResponse item : accommodationPage.getContent()){
            System.out.printf("name: %s, price:%f, rating: %f, popularity: %d\n", item.getName(), item.getPrice(), item.getRating(), item.getPopularity());
        }
    }
}
