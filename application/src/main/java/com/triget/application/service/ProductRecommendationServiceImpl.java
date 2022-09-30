package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.airline.AirlineRepository;
import com.triget.application.domain.airport.AirportRepository;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.flight.FlightLeg;
import com.triget.application.domain.flight.FlightSegment;
import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.restaurant.Restaurant;
import com.triget.application.web.dto.*;
import com.triget.application.web.dto.flight.FlightLegResponseDto;
import com.triget.application.web.dto.flight.FlightPageResponseDto;
import com.triget.application.web.dto.flight.FlightResponseDto;
import com.triget.application.web.dto.flight.FlightSegmentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class ProductRecommendationServiceImpl implements ProductRecommendationService {
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final SkyScannerFlightsInterface skyScannerFlightsInterface;
    private final JourneyService journeyService;
    private final PlaceService placeService;
    private final ProductService productService;
    private final FlightService flightService;

    @Autowired
    public ProductRecommendationServiceImpl(AirlineRepository airlineRepository,
                                            AirportRepository airportRepository,
                                            SkyScannerFlightsInterface skyScannerFlightsInterface, JourneyService journeyService, PlaceService placeService, ProductService productService, FlightService flightService)
    {
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.skyScannerFlightsInterface = skyScannerFlightsInterface;
        this.journeyService = journeyService;
        this.placeService = placeService;
        this.productService = productService;
        this.flightService = flightService;
    }

    @Transactional
    @Override
    public String saveRequestDto(EntireProductListRequestDto dto) throws NullPointerException {
        return journeyService.createJourney(dto);
    }

    private void computePriors(Journey journey) throws Exception {
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();

        int accommodationsPrior = journey.getAccommodationsPriority();
        int restaurantsPrior = journey.getRestaurantsPriority();
        int attractionsPrior = journey.getAttractionsPriority();
        int priorSum = accommodationsPrior+restaurantsPrior+attractionsPrior;

        int peopleNum = journey.getPeopleNum();

        float remainBudget = journey.getBudget();
        float flightsBudget = journey.getFlightsBudget();
        float accommodationsBudget;
        float restaurantsBudget;
        float attractionsBudget;
        float margin = (float) (remainBudget*0.03);

        List<Flight> flights = flightService.findByJourneyId(journey.getId().toString());
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

        List<Accommodation> accommodations = productService.findByCityAndKeywordsAndPriceLess(
                placeSearchName, theme, accommodationsBudget
        );

        if (accommodations.size() == 0){
            accommodations  = productService.findAllByCity(placeSearchName);
            float minAccommodationsPriceByKrw = accommodations.get(0).getPrice();
            float maxAccommodationsPriceByKrw = accommodations.get(accommodations.size()-1).getPrice();
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
        journeyService.updateJourney(journey);
    }

    @Transactional(readOnly = true)
    @Override
    public EntireProductListResponseDto setResponseDto(String journeyId) throws Exception {
        Journey journey = journeyService.findById(journeyId);
        try {
            List<Future<Object>> futures = skyScannerFlightsInterface.addFlights(journey);
            computePriors(journey);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();

        float flightsBudget = journey.getFlightsBudget();
        float accommodationsBudget = journey.getAccommodationsBudget();
        float restaurantsBudget = journey.getRestaurantsBudget();
        float attractionsBudget = journey.getAttractionsBudget();

        return EntireProductListResponseDto.builder()
                .journeyId(journeyId)
                .flightsBudget(flightsBudget)
                .accommodationsBudget(accommodationsBudget)
                .restaurantsBudget(restaurantsBudget)
                .attractionsBudget(attractionsBudget)
                .flights(mapFlightPageResponseDto(flightService.getFlightPage(journeyId, flightsBudget, 0)))
                .accommodations(mapAccommodationPageResponseDto(productService.getAccommodationPage(placeSearchName, theme, accommodationsBudget, 0)))
                .restaurants(mapRestaurantPageResponseDto(productService.getRestaurantPage(placeSearchName, theme, 0)))
                .attractions(mapAttractionPageResponseDto(productService.getAttractionPage(placeSearchName, theme, 0)))
                .build();
    }

    @Override
    public FlightPageResponseDto findFlights(String journeyId, int page) throws Exception {
        Journey journey = journeyService.findById(journeyId);
        return mapFlightPageResponseDto(flightService.getFlightPage(journeyId, journey.getFlightsBudget(), page));
    }

    @Override
    public ProductPageResponseDto findAccommodations(String journeyId, int page) throws Exception {
        Journey journey = journeyService.findById(journeyId);
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();
        float accommodationsBudget = journey.getAccommodationsBudget();

        return mapAccommodationPageResponseDto(productService.getAccommodationPage(placeSearchName, theme, accommodationsBudget, page));
    }

    @Override
    public ProductPageResponseDto findRestaurants(String journeyId, int page) throws Exception {
        Journey journey = journeyService.findById(journeyId);
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();
        return mapRestaurantPageResponseDto(productService.getRestaurantPage(placeSearchName, theme, page));
    }

    @Override
    public ProductPageResponseDto findAttractions(String journeyId, int page) throws Exception {
        Journey journey = journeyService.findById(journeyId);
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();
        return mapAttractionPageResponseDto(productService.getAttractionPage(placeSearchName, theme, page));
    }

    @Override
    public FlightPageResponseDto mapFlightPageResponseDto(Page<Flight> flightPage) {
        return FlightPageResponseDto.builder()
                .content(flightPage.getContent().stream().map(item->{
                    List<FlightLegResponseDto> legs = new ArrayList<>();
                    for (FlightLeg leg: item.getLegs()){
                        List<FlightSegment> flightSegments = leg.getSegments();
                        List<FlightSegmentResponseDto> segments = new ArrayList<>();
                        if (flightSegments != null){
                            for (FlightSegment segment: flightSegments) {
                                segments.add(FlightSegmentResponseDto.builder()
                                        .segment(segment)
                                        .origin(AirportResponseDto.builder()
                                                .airport(airportRepository.findByIata(segment.getOrigin()).orElse(null))
                                                .build())
                                        .destination(AirportResponseDto.builder()
                                                .airport(airportRepository.findByIata(segment.getDestination()).orElse(null))
                                                .build())
                                        .operation(AirlineResponseDto.builder()
                                                .airline(airlineRepository.findById(segment.getOperation().get_id()).orElse(null))
                                                .build())
                                        .build());
                            }
                        }
                        legs.add(FlightLegResponseDto.builder()
                                .leg(leg)
                                .origin(AirportResponseDto.builder()
                                        .airport(airportRepository.findByIata(leg.getOrigin()).orElse(null))
                                        .build())
                                .destination(AirportResponseDto.builder()
                                        .airport(airportRepository.findByIata(leg.getDestination()).orElse(null))
                                        .build())
                                .operations(leg.getOperations().stream().map(AirlineResponseDto::new).toList())
                                .flightSegments(segments)
                                .build());
                    }
                    return FlightResponseDto.builder()
                            .flight(item)
                            .legs(legs)
                            .build();
                }).toList())
                .numberOfElements(flightPage.getNumberOfElements())
                .last(flightPage.isLast())
                .empty(flightPage.isEmpty())
                .build();
    }

    @Override
    public ProductPageResponseDto mapAccommodationPageResponseDto(Page<Accommodation> accommodationPage) {
        return ProductPageResponseDto.builder()
                .content(accommodationPage.getContent().stream().map(ProductResponseDto::new).toList())
                .numberOfElements(accommodationPage.getNumberOfElements())
                .last(accommodationPage.isLast())
                .empty(accommodationPage.isEmpty())
                .build();
    }

    @Override
    public ProductPageResponseDto mapRestaurantPageResponseDto(Page<Restaurant> restaurantPage) {
        return ProductPageResponseDto.builder()
                .content(restaurantPage.getContent().stream().map(ProductResponseDto::new).toList())
                .numberOfElements(restaurantPage.getNumberOfElements())
                .last(restaurantPage.isLast())
                .empty(restaurantPage.isEmpty())
                .build();
    }

    @Override
    public ProductPageResponseDto mapAttractionPageResponseDto(Page<Attraction> attractionPage) {
        return ProductPageResponseDto.builder()
                .content(attractionPage.getContent().stream().map(ProductResponseDto::new).toList())
                .numberOfElements(attractionPage.getNumberOfElements())
                .last(attractionPage.isLast())
                .empty(attractionPage.isEmpty())
                .build();
    }
}
