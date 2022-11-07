package com.triget.application.server.entity.flight;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomFlightPage {
    private List<FlightResponse> content;
    private Boolean last;
    private int numberOfElements;
    private Boolean empty;

    @Builder
    public CustomFlightPage(List<FlightResponse> content, Boolean last, int numberOfElements, Boolean empty){
        this.content = content;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }
}
