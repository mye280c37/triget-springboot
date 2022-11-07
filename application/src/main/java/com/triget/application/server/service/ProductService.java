package com.triget.application.server.service;

import com.triget.application.server.common.ObjectNotFoundException;
import com.triget.application.server.domain.product.Accommodation;
import com.triget.application.server.repository.product.AccommodationRepository;
import com.triget.application.server.domain.product.Attraction;
import com.triget.application.server.repository.product.AttractionRepository;
import com.triget.application.server.domain.product.Restaurant;
import com.triget.application.server.repository.product.RestaurantRepository;
import org.bson.types.ObjectId;
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

    public Accommodation findAccommodationById(String id) {
        return accommodationRepository.findById(new ObjectId(id)).orElseThrow(
                () -> new ObjectNotFoundException("No matching restaurant found")
        );
    }

    public Restaurant findRestaurantById(String id) {
        return restaurantRepository.findById(new ObjectId(id)).orElseThrow(
                () -> new ObjectNotFoundException("No matching restaurant found")
        );
    }

    public Attraction findAttractionById(String id) {
        return attractionRepository.findById(new ObjectId(id)).orElseThrow(
                () -> new ObjectNotFoundException("No matching restaurant found")
        );
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
