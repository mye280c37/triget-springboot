package com.triget.application.server.repository.schedule;

import com.triget.application.server.domain.schedule.DailySchedule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyScheduleRepository extends MongoRepository<DailySchedule, ObjectId> {
}
