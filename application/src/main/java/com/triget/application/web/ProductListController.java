package com.triget.application.web;

import com.triget.application.common.ErrorResponse;
import com.triget.application.service.ProductRecommendationServiceImpl;
import com.triget.application.web.dto.EntireProductListRequestDto;
import com.triget.application.web.dto.EntireProductListResponseDto;
import com.triget.application.web.dto.ProductPageResponseDto;
import com.triget.application.web.dto.flight.FlightPageResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/product-list/v1")
@Api(tags = {"상품 추천 API"})
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ProductListController {

    @Autowired
    private final ProductRecommendationServiceImpl productListServiceImpl;
    @Autowired
    public ProductListController(ProductRecommendationServiceImpl productListServiceImpl) {
        this.productListServiceImpl = productListServiceImpl;
    }

    @PostMapping()
    @ApiOperation(value = "여행 스펙 저장, 상품 전체 추천", response = EntireProductListResponseDto.class)
    public ResponseEntity<?> returnEntireProductList(@Validated @RequestBody EntireProductListRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            // or 400 bad request
            return ResponseEntity.badRequest().body(new ErrorResponse("400", "Validation failure", errors));
        }
        try {
            return ResponseEntity.ok(
                    productListServiceImpl.setResponseDto(productListServiceImpl.saveRequestDto(dto))
            );
        }catch (Exception e){
            // 200 response with 404 status code
            // errorContent 바꿔야 할듯
            return ResponseEntity.ok(new ErrorResponse("404", "response failure", e.getMessage()));
        }
    }

    @GetMapping()
    @ApiOperation(value = "해당 여행 스펙에 대한 상품 전체 추천 리스트", response = EntireProductListResponseDto.class)
    public EntireProductListResponseDto returnEntireProductList(@RequestParam("journeyId") String journeyId) throws Exception {
        return productListServiceImpl.setResponseDto(journeyId);
    }

    @GetMapping("/flights")
    @ApiOperation(value = "항공 상품 추가 추천", response = FlightPageResponseDto.class)
    public FlightPageResponseDto returnFlightsList(@RequestParam("journeyId") String journeyId,
                                                   @RequestParam("page") int page) throws Exception {
        return productListServiceImpl.findFlights(journeyId, page);
    }

    @GetMapping("/accommodations")
    @ApiOperation(value = "숙박 상품 추가 추천", response = ProductPageResponseDto.class)
    public ProductPageResponseDto returnAccommodationsList(@RequestParam("journeyId") String journeyId,
                                                           @RequestParam("page") int page) throws Exception {
        return productListServiceImpl.findAccommodations(journeyId, page);
    }

    @GetMapping("/restaurants")
    @ApiOperation(value = "식당 상품 추가 추천", response = ProductPageResponseDto.class)
    public ProductPageResponseDto returnRestaurantsList(@RequestParam("journeyId") String journeyId,
                                                  @RequestParam("page") int page) throws Exception {
        return productListServiceImpl.findRestaurants(journeyId, page);
    }

    @GetMapping("/attractions")
    @ApiOperation(value = "어트랙션 상품 추가 추천", response = ProductPageResponseDto.class)
    public ProductPageResponseDto returnAttractionsList(@RequestParam("journeyId") String journeyId,
                                                  @RequestParam("page") int page) throws Exception {
        return productListServiceImpl.findAttractions(journeyId, page);
    }

}
