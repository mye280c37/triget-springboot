package com.triget.application.server.repository.product.flight;

import com.triget.application.server.domain.product.flight.Flight;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FlightRepository extends MongoRepository<Flight, ObjectId> {
    @Query(value = "{$and:[{journey_id: ?0}, {skyscanner_id: ?1}]}")
    public List<Flight> findByJourneyIdAndSkyScannerId(String journeyId, String skyScannerId);
    @Query(value="{$and:[{journeyId: ?0}, {price: {$gte: ?1, $lte: ?2}}]}")
    public Page<Flight> findByJourneyIdAndPriceBetween(String journeyId, float priceFrom, float priceTo, Pageable pageable);
    @Query(value="{$and:[{journeyId: ?0}, {price: {$lte: ?1}}]}")
    public Page<Flight> findByJourneyIdAndPriceLess(String journeyId, float priceTo, Pageable pageable);
    public List<Flight> findByJourneyId(String journeyId);
    public List<Flight> findByJourneyId(String journeyId, Sort orders);

}
