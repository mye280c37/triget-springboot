package com.triget.application.server.entity;

import com.triget.application.server.domain.airline.Airline;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class AirlineResponse {
    private String id;
    private String name;
    @Nullable
    private String logoUrl;

    @Builder
    public AirlineResponse(Airline airline) {
        this.id = airline.get_id().toString();
        this.name = airline.getName();
        this.logoUrl = airline.getLogoUrl();
    }
}
