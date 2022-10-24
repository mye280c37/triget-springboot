package com.triget.application.server.repository.product.flight;

import com.triget.application.server.domain.product.flight.FlightLeg;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightLegRepository extends MongoRepository<FlightLeg, ObjectId> {
}
