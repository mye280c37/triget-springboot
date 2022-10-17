package com.triget.application.server.web.dto;

import com.triget.application.server.domain.journey.Journey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(value = "여행 스펙 정보", description = "상품 추천을 위해 필요한 사용자의 여행 정보")
public class EntireProductListRequestDto {

    @NotBlank(message = "place is mandatory")
    @ApiModelProperty(value = "여행 장소", example = "도쿄")
    private final String place;
    @NotBlank(message = "theme is mandatory")
    @ApiModelProperty(value = "여행 테마", example = "relaxing")
    private final String theme;
    @Min(value = 1, message = "peopleNum must be larger than 0")
    @ApiModelProperty(value = "여행 총 인원", example = "2")
    private final int peopleNum;
    @NotBlank(message = "departureDate is mandatory")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid expression. It must follow expression 'yyyy-MM-dd' (ex. 2022-12-04)")
    @ApiModelProperty(value = "여행 출발 날짜", example = "2022-12-04")
    private final String departureDate;
    @NotBlank(message = "arrivalDate is mandatory")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid expression. It must follow expression 'yyyy-MM-dd' (ex. 2022-12-08)")
    @ApiModelProperty(value = "여행 도착 날짜", example = "2022-12-08")
    private final String arrivalDate;
    @NotBlank(message = "departureAirport is mandatory")
    @ApiModelProperty(value = "출발 시 이용할 공항 IATA 코드", example = "GMP")
    private final String departureAirport;
    @ApiModelProperty(value = "모든 인원이 여행에 쓸 총 예산", example = "3000000")
    private final int budget;
    @Min(value = 1, message = "flightsPrior must have a value between 1 and 5.")
    @Max(value = 5, message = "flightsPrior must have a value between 1 and 5.")
    @ApiModelProperty(value = "항공 우선순위", example = "2")
    private final int flightsPrior;
    @Min(value = 1, message = "accommodationsPrior must have a value between 1 and 5.")
    @Max(value = 5, message = "accommodationsPrior must have a value between 1 and 5.")
    @ApiModelProperty(value = "숙박 우선순위", example = "4")
    private final int accommodationsPrior;
    @Min(value = 1, message = "restaurantsPrior must have a value between 1 and 5.")
    @Max(value = 5, message = "restaurantsPrior must have a value between 1 and 5.")
    @ApiModelProperty(value = "식당 우선순위", example = "4")
    private final int restaurantsPrior;
    @Min(value = 1, message = "attractionsPrior must have a value between 1 and 5.")
    @Max(value = 5, message = "attractionsPrior must have a value between 1 and 5.")
    @ApiModelProperty(value = "어트랙션 우선순위", example = "5")
    private final int attractionsPrior;

    @Builder
    public EntireProductListRequestDto(String place, String theme, int peopleNum, String departureDate, String arrivalDate,
                                       String departureAirport, int budget, int flightsPrior, int accommodationsPrior,
                                       int restaurantsPrior, int attractionsPrior) {
        this.place = place;
        this.theme = theme;
        this.peopleNum = peopleNum;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureAirport = departureAirport;
        this.budget = budget;
        this.flightsPrior = flightsPrior;
        this.accommodationsPrior = accommodationsPrior;
        this.restaurantsPrior = restaurantsPrior;
        this.attractionsPrior = attractionsPrior;
    }

    public Journey toEntity() {
        int priorSum = flightsPrior + accommodationsPrior + restaurantsPrior + attractionsPrior;
        return Journey.builder()
                .place(place)
                .theme(theme)
                .peopleNum(peopleNum)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .departureAirport(departureAirport)
                .budget(budget)
                .flightsPriority(flightsPrior)
                .accommodationsPriority(accommodationsPrior)
                .restaurantsPriority(restaurantsPrior)
                .attractionsPriority(attractionsPrior)
                .flightsBudget(((float) flightsPrior/priorSum)*budget)
                .accommodationsBudget(((float) accommodationsPrior/priorSum)*budget)
                .restaurantsBudget(((float) restaurantsPrior/priorSum)*budget)
                .attractionsBudget(((float) attractionsPrior/priorSum)*budget)
                .build();
    }
}
