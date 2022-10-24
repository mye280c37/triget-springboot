package com.triget.application.server.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triget.application.server.domain.airport.Airport;
import com.triget.application.server.domain.airport.AirportRepository;
import com.triget.application.server.domain.airline.Airline;
import com.triget.application.server.domain.airline.AirlineRepository;
import com.triget.application.server.domain.flight.Flight;
import com.triget.application.server.domain.flight.FlightRepository;
import com.triget.application.server.domain.flight.FlightLeg;
import com.triget.application.server.domain.flight.FlightSegment;
import com.triget.application.server.domain.journey.Journey;
import com.triget.application.server.controller.dto.skyscanner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SkyScannerFlightsInterface {
    @Value("${apiKey.rapidApi}")
    private String API_KEY;
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirportRepository airportRepository;


    private String setUrl(int adults, String origin, String destination, String departureDate, String returnDate) {
        return String.format("https://skyscanner44.p.rapidapi.com/search?adults=%d&origin=%s&destination=%s&departureDate=%s&returnDate=%s&currency=KRW",
                adults, origin, destination, departureDate, returnDate);
    }

    public HashMap<String, Object> getData(int adults, String origin, String destination, String departureDate, String returnDate) {
        HashMap<String, Object> result = new HashMap<>();
        ResponseEntity<Object> resultMap = new ResponseEntity<>(null,null,200);

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            // 이거 숨겨야 함
            header.add("X-RapidAPI-Key", API_KEY);
            header.add("X-RapidAPI-Host", "skyscanner44.p.rapidapi.com");

            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = setUrl(adults, origin, destination, departureDate, returnDate);
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

            resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Object.class);

            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //에러처리해야댐
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("error");
            System.out.println(e.toString());

            return result;
        }
        catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());

            return result;

        }

        return result;

    }

    private FlightSegment convertToSegment(Segment segment, int order) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Operation operatingCarrier = segment.getOperatingCarrier();
        String skyScannerId = operatingCarrier.getId();
        List<Airline> airlineList = airlineRepository.findBySkyScannerId(skyScannerId);
        if(airlineList.size() == 0) {
            airlineRepository.save(
                    Airline.builder()
                            .skyScannerId(skyScannerId)
                            .name(operatingCarrier.getName())
                            .logoUrl("")
                            .build()
            );
        }
        Airline operation = (airlineRepository.findBySkyScannerId(skyScannerId).get(0));
        return  FlightSegment.builder()
                .skyScannerId(skyScannerId)
                .order(order)
                .origin(segment.getOrigin().getFlightPlaceId())
                .destination(segment.getDestination().getFlightPlaceId())
                .departure(segment.getDeparture())
                .arrival(segment.getArrival())
                .durationInMinutes(segment.getDurationInMinutes())
                .flightNumber(segment.getFlightNumber())
                .operation(operation)
                .build();
    }

    private FlightLeg convertToFlight(Leg leg) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Airline> operations = new ArrayList<>();
        List<Operation> marketing = leg.getCarriers().getMarketing();
        assert marketing != null;
        for(Operation operation: marketing) {
            String skyScannerId = operation.getId();
            List<Airline> airlineList = airlineRepository.findBySkyScannerId(skyScannerId);
            if(airlineList.size() == 0) {
                airlineRepository.save(
                        Airline.builder()
                                .skyScannerId(skyScannerId)
                                .name(operation.getName())
                                .logoUrl(operation.getLogoUrl())
                                .build()
                );
            }
            operations.add(airlineRepository.findBySkyScannerId(skyScannerId).get(0));
        }
        List<FlightSegment> flightSegments = new ArrayList<>();
        List<Segment> segmentsMap = leg.getSegments();
        int order = 0;
        for(Segment segment: segmentsMap) {
            flightSegments.add(convertToSegment(segment, order));
            order++;
        }
        return FlightLeg.builder()
                .skyScannerId(leg.getId())
                .origin(leg.getOrigin().getDisplayCode())
                .destination(leg.getDestination().getDisplayCode())
                .departure(leg.getDeparture())
                .arrival(leg.getArrival())
                .durationInMinutes(leg.getDurationInMinutes())
                .timeDeltaInDays(leg.getTimeDeltaInDays())
                .stopCount(leg.getStopCount())
                .isSmallestStops(leg.getIsSmallestStops())
                .operations(operations)
                .airportChangeIn(leg.getAirportChangeIn())
                .segments(flightSegments)
                .build();
    }

    @Transactional
    public void convertToEntireFlights(String journeyId, SkyScannerSearchBestDto body) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Bucket> buckets = body.getItineraries().getBuckets();

        for(Bucket bucket: buckets) {
            String type = bucket.getId();
            List<Item> items = bucket.getItems();
            for(Item item: items) {
                String skyScannerId = item.getId();
                if(flightRepository.findByJourneyIdAndSkyScannerId(journeyId, skyScannerId).isEmpty()){
                    List<FlightLeg> legs = new ArrayList<>();

                    int totalStopCounts = 0;
                    List<Leg> legsMap = item.getLegs();
                    for(Leg leg: legsMap){
                        FlightLeg flightLeg = convertToFlight(leg);
                        totalStopCounts += flightLeg.getStopCount();
                        legs.add(flightLeg);
                    }
                    flightRepository.save(Flight.builder()
                                    .journeyId(journeyId)
                                    .skyScannerId(skyScannerId)
                                    .price(item.getPrice().getRaw())
                                    .legs(legs)
                                    .score(item.getScore())
                                    .type(type)
                                    .totalStopCounts(totalStopCounts)
                                    .detailUrl(item.getDeeplink())
                                    .build()
                    );
                }

            }

        }
    }

    @Transactional
    public List<Future<Object>> addFlights(Journey journey) throws InterruptedException {
        String journeyId = journey.getId().toString();
        int adults = journey.getPeopleNum();
        //List<String> origins = new ArrayList<>(List.of(new String[]{"ICN", "GMP"}));
        String origin = journey.getDepartureAirport();
        List<Airport> destinations = airportRepository.findByNameContainsString(journey.getPlace());
        String departureDate = journey.getDepartureDate();
        String returnDate = journey.getArrivalDate();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ExecutorService executorService = Executors.newFixedThreadPool(destinations.size());
        List<Callable<Object>> calls = new ArrayList<Callable<Object>>();
        if(flightRepository.findByJourneyId(journeyId).size() == 0) {
            for(Airport destination: destinations) {
                calls.add(Executors.callable(()->{
                    while(true) {
                        HashMap<String,Object> result = getData(adults, origin, destination.getIata(), departureDate, returnDate);
                        SkyScannerSearchBestDto body = objectMapper.convertValue(result.get("body"), SkyScannerSearchBestDto.class);
                        int totalResults = body.getContext().getTotalResults();
                        String status = body.getContext().getStatus();
                        System.out.printf("%d(%s)\n", totalResults, status);
                        if(totalResults!=0){
                            convertToEntireFlights(journeyId, body);
                        }
                        if(totalResults < 20 && status.equals("incomplete")) {
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{ break; }
                    }
                }));
            }
        }
        return executorService.invokeAll(calls);
    }
}
