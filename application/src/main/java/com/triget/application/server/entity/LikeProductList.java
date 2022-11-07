package com.triget.application.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@Getter
@Setter
@ApiModel(value = "관심 상품 목록", description = "검색 상품 중 일정표 생성을 위한 관심 상품 목록")
public class LikeProductList {
    @NotBlank(message = "journey id is mandatory")
    @ApiModelProperty(value = "여행 스펙 ID", example = "6368dde01edb22572d5cc930")
    private String journeyId;
    @NotBlank(message = "flight product id is mandatory")
    @ApiModelProperty(value = "항공 상품 ID", example = "6368dde41edb22572d5cc931")
    private String flightId;
    @NotEmpty(message = "accommodation product ids can not be empty")
    @ApiModelProperty(value = "숙박 상품 ID 리스트")
    private List<String> accommodationIds;
    @NotEmpty(message = "restaurant product ids can not be empty")
    @ApiModelProperty(value = "식당 상품 ID 리스트")
    private List<String> restaurantIds;
    @NotEmpty(message = "attraction product ids can not be empty")
    @ApiModelProperty(value = "어트랙션 상품 ID 리스트")
    private List<String> attractionIds;
}
