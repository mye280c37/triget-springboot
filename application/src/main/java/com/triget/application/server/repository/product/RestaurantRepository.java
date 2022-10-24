package com.triget.application.server.repository.product;

import com.triget.application.server.domain.product.Restaurant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {
    @Query(value="{$and:[{city: ?0}, {keywords: ?1}]}")
    public Page<Restaurant> findAllByCityAndKeywords(String city, String keyword, Pageable pageable);
}
