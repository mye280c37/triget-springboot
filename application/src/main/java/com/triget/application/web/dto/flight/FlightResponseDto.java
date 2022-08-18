package com.triget.application.web.dto.flight;

import com.triget.application.domain.flight.Flight;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class FlightResponseDto {
    private String id;
    private float price;
    private List<FlightLegResponseDto> legs;
    private float score;
    private String type;
    private int totalStopCounts;
    private String detailUrl;

    @Builder
    public FlightResponseDto(Flight flight, List<FlightLegResponseDto> legs){
        this.id = flight.get_id().toString();
        this.price = flight.getPrice();
        this.legs = legs;
        this.score = flight.getScore();
        this.type = flight.getType();
        this.totalStopCounts = flight.getTotalStopCounts();
        this.detailUrl = flight.getDetailUrl();
    }
}
