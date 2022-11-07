package com.triget.application.server.entity;

import com.triget.application.server.domain.product.Accommodation;
import com.triget.application.server.domain.product.Attraction;
import com.triget.application.server.domain.product.Restaurant;
import com.triget.application.server.domain.product.flight.Flight;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductSet {
    @Nullable
    private Flight flight;
    private ArrayList<Accommodation> accommodations;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Attraction> attractions;

    public ProductSet() {
        flight = null;
        accommodations = new ArrayList<>();
        restaurants = new ArrayList<>();
        attractions = new ArrayList<>();
    }

    public ProductSet(Flight flight, ArrayList<Accommodation> accommodations, ArrayList<Restaurant> restaurants, ArrayList<Attraction> attractions) {
        this.flight = flight;
        this.accommodations = accommodations;
        this.restaurants = restaurants;
        this.attractions = attractions;
    }
}
