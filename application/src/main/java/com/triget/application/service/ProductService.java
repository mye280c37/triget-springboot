package com.triget.application.service;

import com.triget.application.domain.accommodation.Accommodation;
import com.triget.application.domain.accommodation.AccommodationRepository;
import com.triget.application.domain.attraction.Attraction;
import com.triget.application.domain.attraction.AttractionRepository;
import com.triget.application.domain.restaurant.Restaurant;
import com.triget.application.domain.restaurant.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final int pageSize = 10;
    private final RestaurantRepository restaurantRepository;
    private final AccommodationRepository accommodationRepository;
    private final AttractionRepository attractionRepository;

    public ProductService(RestaurantRepository restaurantRepository, AccommodationRepository accommodationRepository, AttractionRepository attractionRepository) {
        this.restaurantRepository = restaurantRepository;
        this.accommodationRepository = accommodationRepository;
        this.attractionRepository = attractionRepository;
    }

    private Sort sortByPrice() {
        return Sort.by(Sort.DEFAULT_DIRECTION, "price");
    }

    public List<Accommodation> findByCityAndKeywordsAndPriceLess(String city, String theme, float budget) {
        return accommodationRepository.findByCityAndKeywordsAndPriceLess(
                city,
                budget,
                theme
        );
    }

    public List<Accommodation> findAllByCity(String city) {
        return accommodationRepository.findAllByCity(
                city,
                sortByPrice()
        );
    }

    public Page<Accommodation> getAccommodationPage(String city, String theme, float budget, int page) {
        PageRequest pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return accommodationRepository.findByCityAndKeywordsAndPriceLess(
                city,
                budget,
                theme,
                pageRequest
        );
    }

    public Page<Restaurant> getRestaurantPage(String city, String theme, int page) {
        PageRequest pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return restaurantRepository.findAllByCityAndKeywords(city, theme, pageRequest);
    }

    public Page<Attraction> getAttractionPage(String city, String theme, int page) {
        PageRequest pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.by("rating").descending().and(Sort.by("popularity").descending())
        );
        return attractionRepository.findAllByCityAndKeywords(city, theme, pageRequest);
    }
}
