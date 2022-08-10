package com.triget.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triget.application.domain.airline.Airline;
import com.triget.application.domain.airline.AirlineRepository;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.flight.FlightRepository;
import com.triget.application.domain.flight.FlightLeg;
import com.triget.application.domain.flight.Segment;
import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.journey.JourneyRepository;
import com.triget.application.domain.theme.JourneyTheme;
import com.triget.application.domain.theme.JourneyThemeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkyScannerFlightsInterfaceTest {
    
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private FlightRepository entireFlightsRepository;
    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private JourneyThemeRepository journeyThemeRepository;
    @Autowired
    private SkyScannerFlightsInterface skyScannerFlightsInterface;
    private ObjectId id;

    private Segment convertToSegment(HashMap<String, Object> segment, int order) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> originMap = objectMapper.convertValue(segment.get("origin"), HashMap.class);
        HashMap<String, Object> destinationMap = objectMapper.convertValue(segment.get("destination"), HashMap.class);
        segment.put("origin", originMap.get("flightPlaceId"));
        segment.put("destination", destinationMap.get("flightPlaceId"));
        HashMap<String, Object> operatingCarrier = objectMapper.convertValue(segment.get("operatingCarrier"), HashMap.class);
        String skyScannerId = operatingCarrier.get("id").toString();
        List<Airline> airlineList = airlineRepository.findBySkyScannerId(skyScannerId);
        if(airlineList.size() == 0) {
            airlineRepository.save(
                    Airline.builder()
                            .skyScannerId(skyScannerId)
                            .name(operatingCarrier.get("name").toString())
                            .build()
            );
        }
        Airline operation = (airlineRepository.findBySkyScannerId(skyScannerId).get(0));
        return  Segment.builder()
                .skyScannerId(skyScannerId)
                .order(order)
                .origin(segment.get("origin").toString())
                .destination(segment.get("destination").toString())
                .departure(segment.get("departure").toString())
                .arrival(segment.get("arrival").toString())
                .durationInMinutes(((Number) segment.get("durationInMinutes")).intValue())
                .flightNumber(segment.get("flightNumber").toString())
                .operation(operation)
                .build();
    }

    private FlightLeg convertToFlight(HashMap<String, Object> leg) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> originMap = objectMapper.convertValue(leg.get("origin"), HashMap.class);
        HashMap<String, Object> destinationMap = objectMapper.convertValue(leg.get("destination"), HashMap.class);
        leg.put("origin", originMap.get("displayCode"));
        leg.put("destination", destinationMap.get("displayCode"));
        List<Airline> operations = new ArrayList<>();
        HashMap<String, Object> carriers = objectMapper.convertValue(leg.get("carriers"), HashMap.class);
        List<HashMap<String, Object>> marketing = objectMapper.convertValue(carriers.get("marketing"), List.class);
        for(HashMap<String, Object> operation: marketing) {
            String skyScannerId = operation.get("id").toString();
            List<Airline> airlineList = airlineRepository.findBySkyScannerId(skyScannerId);
            if(airlineList.size() == 0) {
                airlineRepository.save(
                        Airline.builder()
                                .skyScannerId(skyScannerId)
                                .name(operation.get("name").toString())
                                .logoUrl(operation.get("logoUrl").toString())
                                .build()
                );
            }
            operations.add(airlineRepository.findBySkyScannerId(skyScannerId).get(0));
        }
        List<Segment> segments = new ArrayList<>();
        List<HashMap<String, Object>> segmentsMap = objectMapper.convertValue(leg.get("segments"), List.class);
        int order = 0;
        for(HashMap<String, Object> segment: segmentsMap) {
            segments.add(convertToSegment(segment, order));
            order++;
        }
        return FlightLeg.builder()
                .skyScannerId(leg.get("id").toString())
                .origin(leg.get("origin").toString())
                .destination(leg.get("destination").toString())
                .departure(leg.get("departure").toString())
                .arrival(leg.get("arrival").toString())
                .durationInMinutes(((Number) leg.get("durationInMinutes")).intValue())
                .timeDeltaInDays(((Number) leg.get("timeDeltaInDays")).intValue())
                .stopCount(((Number) leg.get("stopCount")).intValue())
                .isSmallestStops((Boolean) leg.get("isSmallestStops"))
                .operations(operations)
                .airportChangeIn(objectMapper.convertValue(leg.getOrDefault("airportChangeIn", new ArrayList<String>()), List.class))
                .segments(segments)
                .build();
    }

    @Test
    public void testGetData() {
        int adults = 2;
        String origin = "GMP";
        String destination = "HND";
        String departureDate = "2022-10-01";
        String returnDate = "2022-10-04";
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> body;
        HashMap<String, Object> context;
        HashMap<String, Object> itineraries;

        HashMap<String,Object> result = skyScannerFlightsInterface.getData(adults, origin, destination, departureDate, returnDate);

        for (String key: result.keySet()) {
            String value = result.get(key).toString();
            System.out.println("\n"+ key + " " + value + "\n");
        }

        body = objectMapper.convertValue(result.get("body"), HashMap.class);
        context = objectMapper.convertValue(body.get("context"), HashMap.class);
        if((int)context.get("totalResults") < 100) {
            result = skyScannerFlightsInterface.getData(adults, origin, destination, departureDate, returnDate);
        }

        body = objectMapper.convertValue(result.get("body"), HashMap.class);
        itineraries = objectMapper.convertValue(body.get("itineraries"), HashMap.class);
        List<HashMap<String, Object>> buckets = objectMapper.convertValue(itineraries.get("buckets"), List.class);

        for(HashMap<String, Object> bucket: buckets) {
            String type = bucket.get("id").toString();
            List<HashMap<String, Object>> items = objectMapper.convertValue(bucket.get("items"), List.class);
            for(HashMap<String, Object> item: items) {
                List<FlightLeg> legs = new ArrayList<>();

                HashMap<String, Object> priceMap = objectMapper.convertValue(item.get("price"), HashMap.class);

                List<HashMap<String, Object>> legsMap = objectMapper.convertValue(item.get("legs"), List.class);
                for(HashMap<String, Object> leg: legsMap){
                    legs.add(convertToFlight(leg));
                }
                entireFlightsRepository.save(Flight.builder()
                        .skyScannerId(item.get("id").toString())
                        .price(((Number) priceMap.get("raw")).floatValue())
                        .legs(legs)
                        .score(((Number) item.get("score")).floatValue())
                        .type(type)
                        .detailUrl(item.get("deeplink").toString())
                        .build()
                );
            }

        }

    }

    @Test
    public void testAddFlights() {
        JourneyTheme journeyTheme = journeyThemeRepository.findByKoreanName("테마").orElse(null);

        this.id = journeyRepository.save(Journey.builder()
                .place("도쿄")
                .theme(journeyTheme)
                .peopleNum(3)
                .departureDate("2022-09-01")
                .arrivalDate("2022-09-03")
                .departureAirport("ICN")
                .budget(1000000)
                .flightsPriority(5)
                .accommodationsPriority(10)
                .restaurantsPriority(20)
                .attractionsPriority(10)
                .flightsBudget(0)
                .accommodationsBudget(0)
                .restaurantsBudget(0)
                .attractionsBudget(0)
                .build()).getId();

        journeyRepository.findById(id).ifPresent(journey -> {
            try {
                skyScannerFlightsInterface.addFlights(journey);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
