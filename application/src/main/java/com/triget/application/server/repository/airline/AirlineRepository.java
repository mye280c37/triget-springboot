package com.triget.application.server.repository.airline;

import com.triget.application.server.domain.airline.Airline;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AirlineRepository extends MongoRepository<Airline, ObjectId> {
    public List<Airline> findBySkyScannerId(String skyScannerId);
}
