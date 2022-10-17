package com.triget.application.server.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductPageResponseDto {
    private List<ProductResponseDto> content;
    private Boolean last;
    private int numberOfElements;
    private Boolean empty;

    @Builder
    public ProductPageResponseDto(List<ProductResponseDto> content, Boolean last, int numberOfElements, Boolean empty){
        this.content = content;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }
}
