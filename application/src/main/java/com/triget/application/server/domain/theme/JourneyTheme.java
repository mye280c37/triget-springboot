package com.triget.application.server.domain.theme;

import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "journey_theme")
public class JourneyTheme {

    @Id
    private ObjectId _id;
    @Field("korean_name")
    private String koreanName;
    @Field("english_name")
    private String englishName;
}
