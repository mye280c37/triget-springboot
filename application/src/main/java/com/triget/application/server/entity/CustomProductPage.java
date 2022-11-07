package com.triget.application.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomProductPage {
    private List<ProductResponse> content;
    private Boolean last;
    private int numberOfElements;
    private Boolean empty;

    @Builder
    public CustomProductPage(List<ProductResponse> content, Boolean last, int numberOfElements, Boolean empty){
        this.content = content;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }
}
