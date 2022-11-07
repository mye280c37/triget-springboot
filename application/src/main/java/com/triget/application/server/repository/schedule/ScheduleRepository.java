package com.triget.application.server.repository.schedule;

import com.triget.application.server.domain.schedule.Schedule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, ObjectId> {
    List<Schedule> findByUserId(String userId);
    Optional<Schedule> findByJourneyId(String journeyId);
}
