package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.accommodation.AccommodationRepository;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.attraction.AttractionRepository;
import com.triget.application.domain.entireflights.EntireFlights;
import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.journey.JourneyRepository;
import com.triget.application.domain.restaurant.Restaurant;
import com.triget.application.domain.restaurant.RestaurantRepository;
import com.triget.application.domain.theme.JourneyTheme;
import com.triget.application.domain.theme.JourneyThemeRepository;
import com.triget.application.web.dto.EntireProductListRequestDto;
import com.triget.application.web.dto.EntireProductListResponseDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductListServiceImpl implements ProductListService {

    private final JourneyRepository journeyRepository;
    private final JourneyThemeRepository journeyThemeRepository;
    private final AccommodationRepository accommodationRepository;
    private final RestaurantRepository restaurantRepository;
    private final AttractionRepository attractionRepository;

    @Autowired
    public ProductListServiceImpl(JourneyRepository journeyRepository,
                                  JourneyThemeRepository journeyThemeRepository,
                                  AccommodationRepository accommodationRepository,
                                  RestaurantRepository restaurantRepository,
                                  AttractionRepository attractionRepository)
    {
        this.journeyRepository = journeyRepository;
        this.journeyThemeRepository = journeyThemeRepository;
        this.accommodationRepository = accommodationRepository;
        this.restaurantRepository = restaurantRepository;
        this.attractionRepository = attractionRepository;
    }

    @Transactional
    @Override
    public ObjectId saveRequestDto(EntireProductListRequestDto dto) throws ParseException, NullPointerException {
        JourneyTheme journeyTheme = journeyThemeRepository.findByKoreanName(dto.getTheme()).orElse(null);
        System.out.print("테마");
        System.out.print(journeyThemeRepository.findByKoreanName("테마"));
        return journeyRepository.save(dto.toEntity(journeyTheme)).getId();
    }

    private Sort sortByPrice() {
        return Sort.by(Sort.DEFAULT_DIRECTION, "price");
    }

    private void computePriors(Journey journey) {
        String city = journey.getPlace();

        int accommodationsPrior = journey.getAccommodationsPriority();
        int restaurantsPrior = journey.getRestaurantsPriority();
        int attractionsPrior = journey.getAttractionsPriority();
        int priorSum = accommodationsPrior+restaurantsPrior+attractionsPrior;

        int peopleNum = journey.getPeopleNum();

        float remainBudget = journey.getBudget();
        float flightsBudget = journey.getFlightsBudget();
        float accommodationsBudget = journey.getAccommodationsBudget();
        float restaurantsBudget = journey.getRestaurantsBudget();
        float attractionsBudget = journey.getAttractionsBudget();
        float margin = (float) (remainBudget*0.03);

        float minFlightsPrice = flightsBudget-margin;
        if (minFlightsPrice < flightsBudget-margin){
            flightsBudget = minFlightsPrice+margin;
            remainBudget -= flightsBudget;
            accommodationsBudget = (float) (accommodationsPrior/priorSum)*remainBudget;
            restaurantsBudget = (float) (restaurantsPrior/priorSum)*remainBudget;
            attractionsBudget = (float) (attractionsPrior/priorSum)*remainBudget;
        }

        List<Accommodation> accommodationList = accommodationRepository.findByCityAndPriceBetween(
                city,
                (accommodationsBudget-margin)/peopleNum,
                (accommodationsBudget+margin)/peopleNum
        );
        if (accommodationList.size() == 0){
            float minAccommodationsPrice = accommodationRepository.findAllByCity(
                    city,
                    sortByPrice()).get(0).getPrice();
            accommodationsBudget = minAccommodationsPrice*peopleNum + margin;
            priorSum = restaurantsPrior + attractionsPrior;
            restaurantsBudget = (float) (restaurantsPrior/priorSum)*remainBudget;
            attractionsBudget = (float) (attractionsPrior/priorSum)*remainBudget;
        }

        journey.setFlightsBudget(flightsBudget);
        journey.setAccommodationsBudget(accommodationsBudget);
        journey.setRestaurantsBudget(restaurantsBudget);
        journey.setAttractionsBudget(attractionsBudget);
        journeyRepository.save(journey);
    }

    @Transactional(readOnly = true)
    @Override
    public EntireProductListResponseDto setResponseDto(ObjectId journeyId) {
        Optional<Journey> journey = journeyRepository.findById(journeyId);
        journey.ifPresent(this::computePriors);
        String city = journey.map(Journey::getPlace).orElse("");
        int peopleNum = journey.map(Journey::getPeopleNum).orElse(1);
        float flightsBudget = journey.map(Journey::getFlightsBudget).orElse(0F);
        float accommodationsBudget = journey.map(Journey::getAccommodationsBudget).orElse(0F);
        float restaurantsBudget = journey.map(Journey::getAccommodationsBudget).orElse(0F);
        float attractionsBudget = journey.map(Journey::getAttractionsBudget).orElse(0F);
        float budget = journey.map(Journey::getBudget).orElse(0);
        float margin = (float) (budget*0.03);

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );

        return EntireProductListResponseDto.builder()
                .journeyId(journeyId)
                .flightsBudget(flightsBudget)
                .accommodationsBudget(accommodationsBudget)
                .restaurantsBudget(restaurantsBudget)
                .attractionsBudget(attractionsBudget)
                .accommodations(accommodationRepository.findByCityAndPriceBetween(
                        city,
                        (accommodationsBudget-margin)/peopleNum,
                        (accommodationsBudget+margin)/peopleNum,
                        pageRequest))
                .restaurants(restaurantRepository.findAllByCity(city, pageRequest))
                .attractions(attractionRepository.findAllByCity(city, pageRequest))
                .build();
    }

    @Override
    public Page<EntireFlights> findFlights(ObjectId journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(journeyId);
        String city = journey.map(Journey::getPlace).orElse("");
        float flightsBudget = journey.map(Journey::getFlightsBudget).orElse(0F);
        float budget = journey.map(Journey::getBudget).orElse(0);
        float margin = (float) (budget*0.03);
        return null;
    }

    @Override
    public Page<Accommodation> findAccommodations(ObjectId journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(journeyId);
        String city = journey.map(Journey::getPlace).orElse("");
        float accommodationsBudget = journey.map(Journey::getAccommodationsBudget).orElse(0F);
        int peopleNum = journey.map(Journey::getPeopleNum).orElse(1);
        float budget = journey.map(Journey::getBudget).orElse(0);
        float margin = (float) (budget*0.03);
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return accommodationRepository.findByCityAndPriceBetween(
                city,
                (accommodationsBudget-margin)/peopleNum,
                (accommodationsBudget+margin)/peopleNum,
                pageRequest
        );
    }

    @Override
    public Page<Restaurant> findRestaurants(ObjectId journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(journeyId);
        String city = journey.map(Journey::getPlace).orElse("");
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return restaurantRepository.findAllByCity(city, pageRequest);
    }

    @Override
    public Page<Attraction> findAttractions(ObjectId journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(journeyId);
        String city = journey.map(Journey::getPlace).orElse("");
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return attractionRepository.findAllByCity(city, pageRequest);
    }
}
