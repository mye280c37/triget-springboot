package com.triget.application.server.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class LikeProductList {
    @NotBlank(message = "journey id is mandatory")
    private String journeyId;
    @NotBlank(message = "flight product id is mandatory")
    private String flightId;
    @NotEmpty(message = "accommodation product ids can not be empty")
    private List<String> accommodationIds;
    @NotEmpty(message = "restaurant product ids can not be empty")
    private List<String> restaurantIds;
    @NotEmpty(message = "attraction product ids can not be empty")
    private List<String> attractionIds;
}
