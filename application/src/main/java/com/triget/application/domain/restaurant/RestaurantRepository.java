package com.triget.application.domain.restaurant;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {
    @Query(value="{$and:[{city: ?0}, {keywords: ?1}]}")
    public Page<Restaurant> findAllByCityAndKeywords(String city, String keyword, Pageable pageable);
}
