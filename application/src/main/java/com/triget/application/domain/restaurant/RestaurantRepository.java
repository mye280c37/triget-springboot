package com.triget.application.domain.restaurant;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {
    public Page<Restaurant> findByPriceBetween(int priceFrom, int priceTo, Pageable pageable);
}
