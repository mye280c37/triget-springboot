package com.triget.application.service;

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

import java.text.ParseException;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductListServiceImplTest {

    @Mock
    private JourneyRepository journeyRepository;
    @Mock
    private JourneyThemeRepository journeyThemeRepository;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private AttractionRepository attractionRepository;
    @InjectMocks
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
        productListServiceImpl.saveRequestDto(entireProductListRequestDto);
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
        System.out.print(entireProductListResponseDto);
    }
}
