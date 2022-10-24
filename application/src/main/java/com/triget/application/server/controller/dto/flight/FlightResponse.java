package com.triget.application.server.controller.dto.flight;

import com.triget.application.server.domain.flight.Flight;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class FlightResponse {
    private String id;
    private float price;
    private List<FlightLegResponse> legs;
    private float score;
    private String type;
    private int totalStopCounts;
    private String detailUrl;

    @Builder
    public FlightResponse(Flight flight, List<FlightLegResponse> legs){
        this.id = flight.get_id().toString();
        this.price = flight.getPrice();
        this.legs = legs;
        this.score = flight.getScore();
        this.type = flight.getType();
        this.totalStopCounts = flight.getTotalStopCounts();
        this.detailUrl = flight.getDetailUrl();
    }
}
