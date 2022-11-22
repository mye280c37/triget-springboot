package com.triget.application.server.service;

import com.triget.application.server.domain.product.Accommodation;
import com.triget.application.server.entity.*;
import com.triget.application.server.repository.airline.AirlineRepository;
import com.triget.application.server.repository.airport.AirportRepository;
import com.triget.application.server.domain.product.Attraction;
import com.triget.application.server.domain.product.flight.Flight;
import com.triget.application.server.domain.product.flight.FlightLeg;
import com.triget.application.server.domain.product.flight.FlightSegment;
import com.triget.application.server.domain.journey.Journey;
import com.triget.application.server.domain.product.Restaurant;
import com.triget.application.server.entity.flight.FlightLegResponse;
import com.triget.application.server.entity.flight.CustomFlightPage;
import com.triget.application.server.entity.flight.FlightResponse;
import com.triget.application.server.entity.flight.FlightSegmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class ProductRecommendationService {
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final SkyScannerFlightsInterface skyScannerFlightsInterface;
    private final JourneyService journeyService;
    private final PlaceService placeService;
    private final ProductService productService;
    private final FlightService flightService;
    private final BudgetComputationService budgetComputationService;

    @Autowired
    public ProductRecommendationService(AirlineRepository airlineRepository,
                                        AirportRepository airportRepository,
                                        SkyScannerFlightsInterface skyScannerFlightsInterface, JourneyService journeyService, PlaceService placeService, ProductService productService, FlightService flightService, BudgetComputationService budgetComputationService)
    {
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.skyScannerFlightsInterface = skyScannerFlightsInterface;
        this.journeyService = journeyService;
        this.placeService = placeService;
        this.productService = productService;
        this.flightService = flightService;
        this.budgetComputationService = budgetComputationService;
    }

    @Transactional
    public String saveRequestDto(ProductRecommendationRequest dto) throws NullPointerException {
        return journeyService.createJourney(dto);
    }

    @Transactional(readOnly = true)
    public ProductRecommendationResponse setResponseDto(String journeyId) throws Exception {
        Journey journey = journeyService.findById(journeyId);
        try {
            List<Future<Object>> futures = skyScannerFlightsInterface.addFlights(journey);
            budgetComputationService.setItemizedBudget(journey);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();

        float flightsBudget = journey.getFlightsBudget();
        float accommodationsBudget = journey.getAccommodationsBudget();
        float restaurantsBudget = journey.getRestaurantsBudget();
        float attractionsBudget = journey.getAttractionsBudget();

        return ProductRecommendationResponse.builder()
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

    public CustomFlightPage findFlights(String journeyId, int page) {
        Journey journey = journeyService.findById(journeyId);
        return mapFlightPageResponseDto(flightService.getFlightPage(journeyId, journey.getFlightsBudget(), page));
    }

    public CustomProductPage findAccommodations(String journeyId, int page) {
        Journey journey = journeyService.findById(journeyId);
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();
        float accommodationsBudget = journey.getAccommodationsBudget();

        return mapAccommodationPageResponseDto(productService.getAccommodationPage(placeSearchName, theme, accommodationsBudget, page));
    }

    public CustomProductPage findRestaurants(String journeyId, int page) {
        Journey journey = journeyService.findById(journeyId);
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();
        return mapRestaurantPageResponseDto(productService.getRestaurantPage(placeSearchName, theme, page));
    }

    public CustomProductPage findAttractions(String journeyId, int page) {
        Journey journey = journeyService.findById(journeyId);
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();
        return mapAttractionPageResponseDto(productService.getAttractionPage(placeSearchName, theme, page));
    }

    public CustomFlightPage mapFlightPageResponseDto(Page<Flight> flightPage) {
        return CustomFlightPage.builder()
                .content(flightPage.getContent().stream().map(item->{
                    List<FlightLegResponse> legs = new ArrayList<>();
                    for (FlightLeg leg: item.getLegs()){
                        List<FlightSegment> flightSegments = leg.getSegments();
                        List<FlightSegmentResponse> segments = new ArrayList<>();
                        if (flightSegments != null){
                            for (FlightSegment segment: flightSegments) {
                                segments.add(FlightSegmentResponse.builder()
                                        .segment(segment)
                                        .origin(AirportResponse.builder()
                                                .airport(airportRepository.findByIata(segment.getOrigin()).orElse(null))
                                                .build())
                                        .destination(AirportResponse.builder()
                                                .airport(airportRepository.findByIata(segment.getDestination()).orElse(null))
                                                .build())
                                        .operation(AirlineResponse.builder()
                                                .airline(airlineRepository.findById(segment.getOperation().get_id()).orElse(null))
                                                .build())
                                        .build());
                            }
                        }
                        legs.add(FlightLegResponse.builder()
                                .leg(leg)
                                .origin(AirportResponse.builder()
                                        .airport(airportRepository.findByIata(leg.getOrigin()).orElse(null))
                                        .build())
                                .destination(AirportResponse.builder()
                                        .airport(airportRepository.findByIata(leg.getDestination()).orElse(null))
                                        .build())
                                .operations(leg.getOperations().stream().map(AirlineResponse::new).toList())
                                .flightSegments(segments)
                                .build());
                    }
                    return FlightResponse.builder()
                            .flight(item)
                            .legs(legs)
                            .build();
                }).toList())
                .numberOfElements(flightPage.getNumberOfElements())
                .last(flightPage.isLast())
                .empty(flightPage.isEmpty())
                .build();
    }

    public CustomProductPage mapAccommodationPageResponseDto(Page<Accommodation> accommodationPage) {
        return CustomProductPage.builder()
                .content(accommodationPage.getContent().stream().map(ProductResponse::new).toList())
                .numberOfElements(accommodationPage.getNumberOfElements())
                .last(accommodationPage.isLast())
                .empty(accommodationPage.isEmpty())
                .build();
    }

    public CustomProductPage mapRestaurantPageResponseDto(Page<Restaurant> restaurantPage) {
        return CustomProductPage.builder()
                .content(restaurantPage.getContent().stream().map(ProductResponse::new).toList())
                .numberOfElements(restaurantPage.getNumberOfElements())
                .last(restaurantPage.isLast())
                .empty(restaurantPage.isEmpty())
                .build();
    }

    public CustomProductPage mapAttractionPageResponseDto(Page<Attraction> attractionPage) {
        return CustomProductPage.builder()
                .content(attractionPage.getContent().stream().map(ProductResponse::new).toList())
                .numberOfElements(attractionPage.getNumberOfElements())
                .last(attractionPage.isLast())
                .empty(attractionPage.isEmpty())
                .build();
    }
}
