package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.accommodation.AccommodationRepository;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.attraction.AttractionRepository;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.flight.FlightRepository;
import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.journey.JourneyRepository;
import com.triget.application.domain.place.Place;
import com.triget.application.domain.place.PlaceRepository;
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
    private final FlightRepository flightRepository;
    private final PlaceRepository placeRepository;
    private final SkyScannerFlightsInterface skyScannerFlightsInterface;

    @Autowired
    public ProductListServiceImpl(JourneyRepository journeyRepository,
                                  JourneyThemeRepository journeyThemeRepository,
                                  AccommodationRepository accommodationRepository,
                                  RestaurantRepository restaurantRepository,
                                  AttractionRepository attractionRepository,
                                  FlightRepository flightRepository,
                                  PlaceRepository placeRepository,
                                  SkyScannerFlightsInterface skyScannerFlightsInterface)
    {
        this.journeyRepository = journeyRepository;
        this.journeyThemeRepository = journeyThemeRepository;
        this.accommodationRepository = accommodationRepository;
        this.restaurantRepository = restaurantRepository;
        this.attractionRepository = attractionRepository;
        this.flightRepository = flightRepository;
        this.placeRepository = placeRepository;
        this.skyScannerFlightsInterface = skyScannerFlightsInterface;
    }

    @Transactional
    @Override
    public ObjectId saveRequestDto(EntireProductListRequestDto dto) throws ParseException, NullPointerException {
        JourneyTheme journeyTheme = journeyThemeRepository.findByKoreanName(dto.getTheme()).orElse(null);
        return journeyRepository.save(dto.toEntity(journeyTheme)).getId();
    }

    private Sort sortByPrice() {
        return Sort.by(Sort.DEFAULT_DIRECTION, "price");
    }

    private void computePriors(Journey journey) {
        String place = journey.getPlace();
        String city = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse(null);
        assert city!=null;

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

        List<Flight> flights = flightRepository.findByJourneyId(journey.getId().toString(), sortByPrice());
        float minFlightsPrice = flights.get(0).getPrice();
        float maxFlightsPrice = flights.get(flights.size()-1).getPrice();
        if (flightsBudget < minFlightsPrice){
            flightsBudget = minFlightsPrice+margin;
        } else if (maxFlightsPrice < flightsBudget) {
            flightsBudget = maxFlightsPrice;
        }
        remainBudget -= flightsBudget;
        accommodationsBudget = ((float) accommodationsPrior/priorSum)*remainBudget;
        restaurantsBudget = ((float) restaurantsPrior/priorSum)*remainBudget;
        attractionsBudget = ((float) attractionsPrior/priorSum)*remainBudget;

        float exchangeRate = (float) 9.79;

        List<Accommodation> accommodations = accommodationRepository.findByCityAndPriceLess(
                city,
                accommodationsBudget
        );

        if (accommodations.size() == 0){
            accommodations  = accommodationRepository.findAllByCity(
                    city,
                    sortByPrice());
            float minAccommodationsPriceByKrw = accommodations.get(0).getPrice()*exchangeRate;
            float maxAccommodationsPriceByKrw = accommodations.get(accommodations.size()-1).getPrice()*exchangeRate;
            System.out.printf("price(JPY): %.1f, %.1f\n", accommodations.get(0).getPrice(), accommodations.get(accommodations.size()-1).getPrice());
            if (accommodationsBudget < minAccommodationsPriceByKrw){
                accommodationsBudget = minAccommodationsPriceByKrw + margin;
            } else if (maxAccommodationsPriceByKrw < accommodationsBudget) {
                accommodationsBudget = maxAccommodationsPriceByKrw;
            }
            remainBudget -= accommodationsBudget;
            priorSum = restaurantsPrior + attractionsPrior;
            restaurantsBudget = ((float) restaurantsPrior/priorSum)*remainBudget;
            attractionsBudget = ((float) attractionsPrior/priorSum)*remainBudget;
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

        journey.ifPresent(obj -> {
            try {
                skyScannerFlightsInterface.addFlights(obj);
                computePriors(obj);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        String place = journey.map(Journey::getPlace).orElse(null);
        assert place!=null;
        String city = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse(null);
        assert city!=null;
        int peopleNum = journey.map(Journey::getPeopleNum).orElse(1);
        float flightsBudget = journey.map(Journey::getFlightsBudget).orElse(0F);
        float accommodationsBudget = journey.map(Journey::getAccommodationsBudget).orElse(0F);
        float restaurantsBudget = journey.map(Journey::getAccommodationsBudget).orElse(0F);
        float attractionsBudget = journey.map(Journey::getAttractionsBudget).orElse(0F);
        float budget = journey.map(Journey::getBudget).orElse(0);
        float margin = (float) 0.03*budget;

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );

        float exchangeRate = (float) 9.79;

        return EntireProductListResponseDto.builder()
                .journeyId(journeyId.toString())
                .flightsBudget(flightsBudget)
                .accommodationsBudget(accommodationsBudget)
                .restaurantsBudget(restaurantsBudget)
                .attractionsBudget(attractionsBudget)
                .flights(flightRepository.findByJourneyIdAndPriceLess(
                        journeyId.toString(),
                        flightsBudget,
                        pageRequest
                ))
                .accommodations(accommodationRepository.findByCityAndPriceLess(
                        city,
                        accommodationsBudget/exchangeRate,
                        pageRequest))
                .restaurants(restaurantRepository.findAllByCity(city, pageRequest))
                .attractions(attractionRepository.findAllByCity(city, pageRequest))
                .build();
    }

    @Override
    public Page<Flight> findFlights(String journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(new ObjectId(journeyId));
        float flightsBudget = journey.map(Journey::getFlightsBudget).orElse(0F);
        float budget = journey.map(Journey::getBudget).orElse(0);
        float margin = (float) (budget*0.03);
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("stopCount").and(Sort.by("score").descending())
        );
        return flightRepository.findByJourneyIdAndPriceLess(
                journeyId,
                flightsBudget,
                pageRequest
        );
    }

    @Override
    public Page<Accommodation> findAccommodations(String journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(new ObjectId(journeyId));
        String place = journey.map(Journey::getPlace).orElse(null);
        assert place!=null;
        String city = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse(null);
        assert city!=null;
        float accommodationsBudget = journey.map(Journey::getAccommodationsBudget).orElse(0F);
        int peopleNum = journey.map(Journey::getPeopleNum).orElse(1);
        float budget = journey.map(Journey::getBudget).orElse(0);
        float margin = (float) (budget*0.03);
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );

        float exchangeRate = (float) 9.79;

        return accommodationRepository.findByCityAndPriceLess(
                city,
                accommodationsBudget/exchangeRate,
                pageRequest
        );
    }

    @Override
    public Page<Restaurant> findRestaurants(String journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(new ObjectId(journeyId));
        String place = journey.map(Journey::getPlace).orElse(null);
        assert place!=null;
        String city = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse(null);
        assert city!=null;
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return restaurantRepository.findAllByCity(city, pageRequest);
    }

    @Override
    public Page<Attraction> findAttractions(String journeyId, int page) {
        Optional<Journey> journey = journeyRepository.findById(new ObjectId(journeyId));
        String place = journey.map(Journey::getPlace).orElse(null);
        assert place!=null;
        String city = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse(null);
        assert city!=null;
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return attractionRepository.findAllByCity(city, pageRequest);
    }
}
