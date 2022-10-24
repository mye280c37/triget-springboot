package com.triget.application.server.repository.journey;

import com.triget.application.server.domain.journey.Place;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlaceRepository extends MongoRepository<Place, ObjectId> {
    public Optional<Place> findByDisplayName(String displayName);
}
