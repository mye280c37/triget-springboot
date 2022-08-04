package com.triget.application.domain.journey;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository  extends MongoRepository<Journey, ObjectId>  {
}
