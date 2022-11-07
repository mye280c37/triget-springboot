package com.triget.application.server.entity;

import com.triget.application.server.domain.airport.Airport;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AirportResponse {
    private String id;
    private String iata;
    private String name;
    private float longitude;
    private float latitude;
    private String cityInEnglish;

    @Builder
    public AirportResponse(Airport airport) {
        this.id = airport.get_id().toString();
        this.iata = airport.getIata();
        this.name = airport.getName();
        this.longitude = airport.getLongitude();
        this.latitude = airport.getLatitude();
        this.cityInEnglish = airport.getCityInEnglish();
    }
}
