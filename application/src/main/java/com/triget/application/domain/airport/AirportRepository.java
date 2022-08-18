package com.triget.application.domain.airport;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends MongoRepository<Airport, ObjectId> {
    @Query(value = "{name: {$regex: \".*?0.*\"}}")
    public List<Airport> findByNameContainsString(String place);
    public Optional<Airport> findByIata(String iata);
}
