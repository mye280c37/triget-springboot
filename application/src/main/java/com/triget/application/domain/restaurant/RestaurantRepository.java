package com.triget.application.domain.restaurant;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {
    public Page<Restaurant> findAllByCity(String city, Pageable pageable);
}
