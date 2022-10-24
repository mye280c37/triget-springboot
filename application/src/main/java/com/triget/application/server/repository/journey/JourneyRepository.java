package com.triget.application.server.repository.journey;

import com.triget.application.server.domain.journey.Journey;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository  extends MongoRepository<Journey, ObjectId>  {
}
