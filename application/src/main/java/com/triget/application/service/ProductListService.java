package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.entireflights.EntireFlights;
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
    public Page<EntireFlights> findFlights(ObjectId journeyId, int page);
    @Transactional(readOnly = true)
    public Page<Accommodation> findAccommodations(ObjectId journeyId, int page);
    @Transactional(readOnly = true)
    public Page<Restaurant> findRestaurants(ObjectId journeyId, int page);
    @Transactional(readOnly = true)
    public Page<Attraction> findAttractions(ObjectId journeyId, int page);
}
