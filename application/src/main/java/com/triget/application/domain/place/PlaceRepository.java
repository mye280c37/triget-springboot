package com.triget.application.domain.place;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends MongoRepository<Place, ObjectId> {
    public Optional<Place> findByDisplayName(String displayName);
}
