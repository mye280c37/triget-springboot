package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.accommodation.AccommodationRepository;
import com.triget.application.domain.attraction.AttractionRepository;
import com.triget.application.domain.journey.JourneyRepository;
import com.triget.application.domain.restaurant.RestaurantRepository;
import com.triget.application.domain.theme.JourneyThemeRepository;
import com.triget.application.web.dto.EntireProductListRequestDto;
import com.triget.application.web.dto.EntireProductListResponseDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductListServiceImplTest {

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
    private ProductListServiceImpl productListServiceImpl;
    private ObjectId id;

    @AfterAll
    public void deleteAll() {
        journeyRepository.deleteById(this.id);
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
    public void setResponseTest() throws ParseException {
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
        List<Accommodation> accommodationPage = entireProductListResponseDto.getAccommodations();
        for(Accommodation item : accommodationPage){
            System.out.printf("name: %s, price:%f, rating: %f, popularity: %d\n", item.getName(), item.getPrice(), item.getRating(), item.getPopularity());
        }
    }
}
