package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.flight.Flight;
import com.triget.application.domain.restaurant.Restaurant;
import com.triget.application.web.dto.EntireProductListRequestDto;
import com.triget.application.web.dto.EntireProductListResponseDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

public interface ProductListService {
    @Transactional
    public ObjectId saveRequestDto(EntireProductListRequestDto dto) throws ParseException, NullPointerException;
    @Transactional(readOnly = true)
    public EntireProductListResponseDto setResponseDto(ObjectId journeyId);
    @Transactional(readOnly = true)
    public Page<Flight> findFlights(String journeyId, int page);
    @Transactional(readOnly = true)
    public Page<Accommodation> findAccommodations(String journeyId, int page);
    @Transactional(readOnly = true)
    public Page<Restaurant> findRestaurants(String journeyId, int page);
    @Transactional(readOnly = true)
    public Page<Attraction> findAttractions(String journeyId, int page);
}
