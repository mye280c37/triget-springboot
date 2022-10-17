package com.triget.application.server.web.dto;

import com.triget.application.server.domain.airline.Airline;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class AirlineResponseDto {
    private String id;
    private String name;
    @Nullable
    private String logoUrl;

    @Builder
    public AirlineResponseDto(Airline airline) {
        this.id = airline.get_id().toString();
        this.name = airline.getName();
        this.logoUrl = airline.getLogoUrl();
    }
}
