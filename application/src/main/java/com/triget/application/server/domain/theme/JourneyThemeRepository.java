package com.triget.application.server.domain.theme;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JourneyThemeRepository extends MongoRepository<JourneyTheme, ObjectId>{
    public Optional<JourneyTheme> findByKoreanName(String koreanName);
}
