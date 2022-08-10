package com.triget.application.service;

import com.triget.application.domain.journey.Journey;
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

import java.util.HashMap;

@Service
public class SkyScannerFlightsInterface {
    @Value("${RAPID_API_KEY}")
    private String API_KEY;

    private String setUrl(int adults, String origin, String destination, String departureDate, String returnDate) {
        return String.format("https://skyscanner44.p.rapidapi.com/search?adults=%d&origin=%s&destination=%s&departureDate=%s&returnDate=%s&currency=KRW",
                adults, origin, destination, departureDate, returnDate);
    }

    public HashMap<String, Object> getData(int adults, String origin, String destination, String departureDate, String returnDate) {
        HashMap<String, Object> result = new HashMap<String, Object>();
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

    @Transactional
    public void addFlights(Journey journey, String destination) {
        int adults = journey.getPeopleNum();
        String origin = journey.getDepartureAirport();
        String departureDate = journey.getDepartureDateTime().toString();
        String returnDate = journey.getArrivalDateTime().toString();
        HashMap<String, Object> result = getData(adults, origin, destination, departureDate, returnDate);
    }
}
