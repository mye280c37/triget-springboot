package com.triget.application.domain.accommodation;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends MongoRepository<Accommodation, ObjectId> {
    public Page<Accommodation> findByPriceBetween(int priceFrom, int priceTo, Pageable pageable);
}
