package com.triget.application.domain.entireflights;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntireFlightsRepository extends MongoRepository<EntireFlights, ObjectId> {
}
