package com.triget.application.server.controller;

import com.triget.application.server.service.ProductRecommendationService;
import com.triget.application.server.controller.dto.ProductRecommendationRequest;
import com.triget.application.server.controller.dto.ProductRecommendationResponse;
import com.triget.application.server.controller.dto.CustomProductPage;
import com.triget.application.server.controller.dto.flight.CustomFlightPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/product-list")
@Api(tags = {"상품 추천 API"})
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ProductRecommendationController {

    @Autowired
    private final ProductRecommendationService productListServiceImpl;
    @Autowired
    public ProductRecommendationController(ProductRecommendationService productListServiceImpl) {
        this.productListServiceImpl = productListServiceImpl;
    }

    @PostMapping()
    @ApiOperation(value = "여행 스펙 저장, 상품 전체 추천", response = ProductRecommendationResponse.class)
    public ProductRecommendationResponse returnEntireProductList(@Validated @RequestBody ProductRecommendationRequest dto) throws Exception {
        return productListServiceImpl.setResponseDto(productListServiceImpl.saveRequestDto(dto));
    }

    @GetMapping()
    @ApiOperation(value = "해당 여행 스펙에 대한 상품 전체 추천 리스트", response = ProductRecommendationResponse.class)
    public ProductRecommendationResponse returnEntireProductList(@RequestParam("journeyId") String journeyId) throws Exception {
        return productListServiceImpl.setResponseDto(journeyId);
    }

    @GetMapping("/flights")
    @ApiOperation(value = "항공 상품 추가 추천", response = CustomFlightPage.class)
    public CustomFlightPage returnFlightsList(@RequestParam("journeyId") String journeyId,
                                              @RequestParam("page") int page) {
        return productListServiceImpl.findFlights(journeyId, page);
    }

    @GetMapping("/accommodations")
    @ApiOperation(value = "숙박 상품 추가 추천", response = CustomProductPage.class)
    public CustomProductPage returnAccommodationsList(@RequestParam("journeyId") String journeyId,
                                                      @RequestParam("page") int page) {
        return productListServiceImpl.findAccommodations(journeyId, page);
    }

    @GetMapping("/restaurants")
    @ApiOperation(value = "식당 상품 추가 추천", response = CustomProductPage.class)
    public CustomProductPage returnRestaurantsList(@RequestParam("journeyId") String journeyId,
                                                   @RequestParam("page") int page) {
        return productListServiceImpl.findRestaurants(journeyId, page);
    }

    @GetMapping("/attractions")
    @ApiOperation(value = "어트랙션 상품 추가 추천", response = CustomProductPage.class)
    public CustomProductPage returnAttractionsList(@RequestParam("journeyId") String journeyId,
                                                   @RequestParam("page") int page) {
        return productListServiceImpl.findAttractions(journeyId, page);
    }

}
