package com.triget.application.domain.theme;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyThemeRepository extends MongoRepository<JourneyTheme, String>{
    public List<JourneyTheme> findByKoreanName(String koreanName);
}
