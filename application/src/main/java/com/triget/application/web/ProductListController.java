package com.triget.application.web;

import com.triget.application.service.ProductListServiceImpl;
import com.triget.application.web.dto.EntireProductListRequestDto;
import com.triget.application.web.dto.EntireProductListResponseDto;
import com.triget.application.web.dto.ProductPageResponseDto;
import com.triget.application.web.dto.flight.FlightPageResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/product-list/v1")
@Api(tags = {"상품 추천 API"})
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ProductListController {

    @Autowired
    private final ProductListServiceImpl productListServiceImpl;

    @Autowired
    public ProductListController(ProductListServiceImpl productListServiceImpl) {
        this.productListServiceImpl = productListServiceImpl;
    }

    @PostMapping()
    @ApiOperation(value = "여행 스펙 저장, 상품 전체 추천", response = EntireProductListRequestDto.class)
    public EntireProductListResponseDto returnEntireProductList(@RequestBody EntireProductListRequestDto dto) throws ParseException {
        ObjectId journeyId = productListServiceImpl.saveRequestDto(dto);
        return productListServiceImpl.setResponseDto(journeyId);
    }

    @GetMapping()
    @ApiOperation(value = "해당 여행 스펙에 대한 상품 전체 추천 리스트", response = EntireProductListRequestDto.class)
    public EntireProductListResponseDto returnEntireProductList(@RequestParam("journeyId") String journeyId) {
        return productListServiceImpl.setResponseDto(new ObjectId(journeyId));
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
