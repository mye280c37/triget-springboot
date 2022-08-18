package com.triget.application.web.dto.flight;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FlightPageResponseDto {
    private List<FlightResponseDto> content;
    private Boolean last;
    private int numberOfElements;
    private Boolean empty;

    @Builder
    public FlightPageResponseDto(List<FlightResponseDto> content, Boolean last, int numberOfElements, Boolean empty){
        this.content = content;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }
}
