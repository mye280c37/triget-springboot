package com.triget.application.server.web;

import com.triget.application.server.service.ProductRecommendationService;
import com.triget.application.server.web.dto.EntireProductListRequestDto;
import com.triget.application.server.web.dto.EntireProductListResponseDto;
import com.triget.application.server.web.dto.ProductPageResponseDto;
import com.triget.application.server.web.dto.flight.FlightPageResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/product-list")
@Api(tags = {"상품 추천 API"})
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ProductListController {

    @Autowired
    private final ProductRecommendationService productListServiceImpl;
    @Autowired
    public ProductListController(ProductRecommendationService productListServiceImpl) {
        this.productListServiceImpl = productListServiceImpl;
    }

    @PostMapping()
    @ApiOperation(value = "여행 스펙 저장, 상품 전체 추천", response = EntireProductListResponseDto.class)
    public EntireProductListResponseDto returnEntireProductList(@Validated @RequestBody EntireProductListRequestDto dto) throws Exception {
        return productListServiceImpl.setResponseDto(productListServiceImpl.saveRequestDto(dto));
    }

    @GetMapping()
    @ApiOperation(value = "해당 여행 스펙에 대한 상품 전체 추천 리스트", response = EntireProductListResponseDto.class)
    public EntireProductListResponseDto returnEntireProductList(@RequestParam("journeyId") String journeyId) throws Exception {
        return productListServiceImpl.setResponseDto(journeyId);
    }

    @GetMapping("/flights")
    @ApiOperation(value = "항공 상품 추가 추천", response = FlightPageResponseDto.class)
    public FlightPageResponseDto returnFlightsList(@RequestParam("journeyId") String journeyId,
                                                   @RequestParam("page") int page) {
        return productListServiceImpl.findFlights(journeyId, page);
    }

    @GetMapping("/accommodations")
    @ApiOperation(value = "숙박 상품 추가 추천", response = ProductPageResponseDto.class)
    public ProductPageResponseDto returnAccommodationsList(@RequestParam("journeyId") String journeyId,
                                                           @RequestParam("page") int page) {
        return productListServiceImpl.findAccommodations(journeyId, page);
    }

    @GetMapping("/restaurants")
    @ApiOperation(value = "식당 상품 추가 추천", response = ProductPageResponseDto.class)
    public ProductPageResponseDto returnRestaurantsList(@RequestParam("journeyId") String journeyId,
                                                  @RequestParam("page") int page) {
        return productListServiceImpl.findRestaurants(journeyId, page);
    }

    @GetMapping("/attractions")
    @ApiOperation(value = "어트랙션 상품 추가 추천", response = ProductPageResponseDto.class)
    public ProductPageResponseDto returnAttractionsList(@RequestParam("journeyId") String journeyId,
                                                  @RequestParam("page") int page) {
        return productListServiceImpl.findAttractions(journeyId, page);
    }

}
