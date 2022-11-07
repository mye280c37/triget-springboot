package com.triget.application.server.repository.schedule;

import com.triget.application.server.domain.schedule.Route;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends MongoRepository<Route, ObjectId> {
}
