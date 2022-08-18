package com.triget.application.web;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.restaurant.Restaurant;
import com.triget.application.service.ProductListServiceImpl;
import com.triget.application.web.dto.EntireProductListRequestDto;
import com.triget.application.web.dto.EntireProductListResponseDto;
import com.triget.application.web.dto.ProductPageResponseDto;
import com.triget.application.web.dto.flight.FlightPageResponseDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class ProductListController {

    @Autowired
    private final ProductListServiceImpl productListServiceImpl;

    @Autowired
    public ProductListController(ProductListServiceImpl productListServiceImpl) {
        this.productListServiceImpl = productListServiceImpl;
    }

    @PostMapping("/product/list")
    public EntireProductListResponseDto returnEntireProductList(@RequestBody EntireProductListRequestDto dto) throws ParseException {
        ObjectId journeyId = productListServiceImpl.saveRequestDto(dto);
        return productListServiceImpl.setResponseDto(journeyId);
    }

    @GetMapping("/product/list")
    public EntireProductListResponseDto returnEntireProductList(@RequestParam("journeyId") String journeyId) {
        return productListServiceImpl.setResponseDto(new ObjectId(journeyId));
    }

    @GetMapping("/product/list/flights")
    public FlightPageResponseDto returnFlightsList(@RequestParam("journeyId") String journeyId,
                                                   @RequestParam("page") int page) {
        return productListServiceImpl.findFlights(journeyId, page);
    }

    @GetMapping("/product/list/accommodations")
    public ProductPageResponseDto returnAccommodationsList(@RequestParam("journeyId") String journeyId,
                                                           @RequestParam("page") int page) {
        return productListServiceImpl.findAccommodations(journeyId, page);
    }

    @GetMapping("/product/list/restaurants")
    public ProductPageResponseDto returnRestaurantsList(@RequestParam("journeyId") String journeyId,
                                                  @RequestParam("page") int page) {
        return productListServiceImpl.findRestaurants(journeyId, page);
    }

    @GetMapping("/product/list/attractions")
    public ProductPageResponseDto returnAttractionsList(@RequestParam("journeyId") String journeyId,
                                                  @RequestParam("page") int page) {
        return productListServiceImpl.findAttractions(journeyId, page);
    }

}
