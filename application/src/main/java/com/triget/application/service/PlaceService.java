package com.triget.application.service;

import com.triget.application.domain.place.Place;
import com.triget.application.domain.place.PlaceRepository;
import org.springframework.stereotype.Service;


@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public String getSearchName(String place) throws Exception {
        String city = placeRepository.findByDisplayName(place).map(Place::getSearchName).orElse(null);
        if (city == null) {
            throw new Exception("");
        }
        else return city;
    }
}
