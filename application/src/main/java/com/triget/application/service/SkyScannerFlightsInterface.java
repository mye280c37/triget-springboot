package com.triget.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triget.application.domain.airport.Airport;
import com.triget.application.domain.airport.AirportRepository;
import com.triget.application.domain.airline.Airline;
import com.triget.application.domain.airline.AirlineRepository;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.flight.FlightRepository;
import com.triget.application.domain.flight.FlightLeg;
import com.triget.application.domain.flight.Segment;
import com.triget.application.domain.journey.Journey;
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
import java.util.concurrent.TimeUnit;

@Service
public class SkyScannerFlightsInterface {
    @Value("${apiKey.rapidApi}")
    private String API_KEY;
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    AirportRepository airportRepository;

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
                            .logoUrl("")
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

    @Transactional
    public void convertToEntireFlights(String journeyId, HashMap<String,Object> result) {
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String,Object> body = objectMapper.convertValue(result.get("body"), HashMap.class);
        HashMap<String,Object> itineraries = objectMapper.convertValue(body.get("itineraries"), HashMap.class);
        List<HashMap<String, Object>> buckets = objectMapper.convertValue(itineraries.get("buckets"), List.class);

        for(HashMap<String, Object> bucket: buckets) {
            String type = bucket.get("id").toString();
            List<HashMap<String, Object>> items = objectMapper.convertValue(bucket.get("items"), List.class);
            for(HashMap<String, Object> item: items) {
                String skyScannerId = item.get("id").toString();
                if(flightRepository.findByJourneyIdAndSkyScannerId(journeyId, skyScannerId).isEmpty()){
                    List<FlightLeg> legs = new ArrayList<>();

                    HashMap<String, Object> priceMap = objectMapper.convertValue(item.get("price"), HashMap.class);
                    int totalStopCounts = 0;
                    List<HashMap<String, Object>> legsMap = objectMapper.convertValue(item.get("legs"), List.class);
                    for(HashMap<String, Object> leg: legsMap){
                        FlightLeg flightLeg = convertToFlight(leg);
                        totalStopCounts += flightLeg.getStopCount();
                        legs.add(flightLeg);
                    }
                    flightRepository.save(Flight.builder()
                                    .journeyId(journeyId)
                                    .skyScannerId(skyScannerId)
                                    .price(((Number) priceMap.get("raw")).floatValue())
                                    .legs(legs)
                                    .score(((Number) item.get("score")).floatValue())
                                    .type(type)
                                    .totalStopCounts(totalStopCounts)
                                    .detailUrl(item.get("deeplink").toString())
                                    .build()
                    );
                }

            }

        }
    }

    @Transactional
    public void addFlights(Journey journey) throws InterruptedException {
        String journeyId = journey.getId().toString();
        int adults = journey.getPeopleNum();
        List<String> origins = new ArrayList<>(List.of(new String[]{"ICN", "GMP"}));
        List<Airport> destinations = airportRepository.findByNameContainsString(journey.getPlace());
        String departureDate = journey.getDepartureDate();
        String returnDate = journey.getArrivalDate();

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> body;
        HashMap<String, Object> context;
        HashMap<String,Object> result;
        int totalResults;
        String status;
        if(flightRepository.findByJourneyId(journeyId).size() == 0) {
            for(String origin: origins) {
                for(Airport destination: destinations) {
                    while(true) {
                        result = getData(adults, origin, destination.getIata(), departureDate, returnDate);
                        body = objectMapper.convertValue(result.get("body"), HashMap.class);
                        context = objectMapper.convertValue(body.get("context"), HashMap.class);
                        totalResults = ((Number)context.get("totalResults")).intValue();
                        status = context.get("status").toString();
                        System.out.printf("%d(%s)\n", ((Number)context.get("totalResults")).intValue(), context.get("status").toString());
                        if(totalResults!=0){
                            convertToEntireFlights(journeyId, result);
                        }
                        if(totalResults < 50 && status.equals("incomplete")) {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        else{ break; }
                    }
                }
            }
        }
    }
}
