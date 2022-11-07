package com.triget.application.server.domain.schedule;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@Builder
@Document("schedule")
public class Schedule {
    @Id
    private ObjectId _id;
    @Field("user_id")
    @Nullable
    private String userId;
    @Field("journey_id")
    private String journeyId;
    private List<DailySchedule> schedules;

    public Schedule(ObjectId _id, @Nullable String userId, String journeyId, List<DailySchedule> schedules) {
        this._id = _id;
        this.userId = userId;
        this.journeyId = journeyId;
        this.schedules = schedules;
    }
}
