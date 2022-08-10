package com.triget.application.domain.flight;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightLegRepository extends MongoRepository<FlightLeg, ObjectId> {
}
