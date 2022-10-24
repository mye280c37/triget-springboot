package com.triget.application.server.repository.product;

import com.triget.application.server.domain.product.Attraction;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AttractionRepository extends MongoRepository<Attraction, ObjectId> {
    @Query(value="{$and:[{city: ?0}, {keywords: ?1}]}")
    public Page<Attraction> findAllByCityAndKeywords(String city, String keyword, Pageable pageable);
}
