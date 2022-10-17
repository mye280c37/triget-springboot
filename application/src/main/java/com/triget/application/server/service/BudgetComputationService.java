package com.triget.application.server.service;

import com.triget.application.server.domain.accommodation.Accommodation;
import com.triget.application.server.domain.flight.Flight;
import com.triget.application.server.domain.journey.Journey;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetComputationService {
    private final JourneyService journeyService;
    private final PlaceService placeService;
    private final ProductService productService;
    private final FlightService flightService;

    public BudgetComputationService(JourneyService journeyService, PlaceService placeService, ProductService productService, FlightService flightService) {
        this.journeyService = journeyService;
        this.placeService = placeService;
        this.productService = productService;
        this.flightService = flightService;
    }

    public float computeFlightBudget(String journeyId, float flightsBudget, float margin) {
        List<Flight> flights = flightService.findByJourneyId(journeyId);
        float minFlightsPrice = flights.get(0).getPrice();
        float maxFlightsPrice = flights.get(flights.size()-1).getPrice();
        if (flightsBudget < minFlightsPrice){
            flightsBudget = minFlightsPrice+margin;
        } else if (maxFlightsPrice < flightsBudget) {
            flightsBudget = maxFlightsPrice;
        }
        return flightsBudget;
    }

    public float computeAccommodationBudget(String placeSearchName, String theme, float accommodationsBudget, float margin) {
        List<Accommodation> accommodations = productService.findByCityAndKeywordsAndPriceLess(
                placeSearchName, theme, accommodationsBudget
        );
        if (accommodations.size() == 0){
            accommodations  = productService.findAllByCity(placeSearchName);
            float minAccommodationsPrice = accommodations.get(0).getPrice();
            float maxAccommodationsPrice = accommodations.get(accommodations.size()-1).getPrice();
            if (accommodationsBudget < minAccommodationsPrice){
                accommodationsBudget = minAccommodationsPrice + margin;
            } else if (maxAccommodationsPrice < accommodationsBudget) {
                accommodationsBudget = maxAccommodationsPrice;
            }
        }
        return accommodationsBudget;
    }

    public void setItemizedBudget(Journey journey) throws Exception {
        String placeSearchName = placeService.getSearchName(journey.getPlace());
        String theme = journey.getTheme();

        //int peopleNum = journey.getPeopleNum();

        float remainBudget = journey.getBudget();
        float margin = (float) (remainBudget*0.03);

        float flightsBudget = journey.getFlightsBudget();
        // float flightsBudget = computeFlightBudget(journey.getId().toString(), journey.getFlightsBudget(), margin);
        remainBudget -= flightsBudget;

        int accommodationsPrior = journey.getAccommodationsPriority();
        int restaurantsPrior = journey.getRestaurantsPriority();
        int attractionsPrior = journey.getAttractionsPriority();
        int priorSum = accommodationsPrior+restaurantsPrior+attractionsPrior;

        float accommodationsBudget = ((float) accommodationsPrior/priorSum)*remainBudget;
        accommodationsBudget = computeAccommodationBudget(placeSearchName, theme, accommodationsBudget, margin);

        remainBudget -= accommodationsBudget;
        priorSum = restaurantsPrior + attractionsPrior;
        float restaurantsBudget = ((float) restaurantsPrior/priorSum)*remainBudget;
        float attractionsBudget = ((float) attractionsPrior/priorSum)*remainBudget;

        journey.setFlightsBudget(flightsBudget);
        journey.setAccommodationsBudget(accommodationsBudget);
        journey.setRestaurantsBudget(restaurantsBudget);
        journey.setAttractionsBudget(attractionsBudget);
        journeyService.updateJourney(journey);
    }
}
