package com.triget.application.domain.attraction;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttractionRepository extends MongoRepository<Attraction, ObjectId> {
    public Page<Attraction> findByPriceBetween(int priceFrom, int priceTo, Pageable pageable);
}
