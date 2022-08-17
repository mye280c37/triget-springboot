package com.triget.application.domain.accommodation;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends MongoRepository<Accommodation, ObjectId> {
    public List<Accommodation> findAllByCity(String city, Sort orders);
    @Query(value="{$and:[{city: ?0}, {price: {$gte: ?1, $lte: ?2}}]}")
    public Page<Accommodation> findByCityAndPriceBetween(String city, float priceFrom, float priceTo, Pageable pageable);
    @Query(value="{$and:[{city: ?0}, {price: {$gte: ?1, $lte: ?2}}]}", sort="{price:1}")
    public List<Accommodation> findByCityAndPriceBetween(String city, float priceFrom, float priceTo);
    @Query(value="{$and:[{city: ?0}, {price: {$lte: ?1}}, {keywords: ?2}]}")
    public Page<Accommodation> findByCityAndKeywordsAndPriceLess(String city, float priceTo, String keyword, Pageable pageable);
    @Query(value="{$and:[{city: ?0}, {price: {$lte: ?1}}, {keywords: ?2}]}", sort="{price:1}")
    public List<Accommodation> findByCityAndKeywordsAndPriceLess(String city, float priceTo, String keyword);
}
