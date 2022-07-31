package com.triget.application.domain.journey.theme;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyThemeRepository extends MongoRepository<JourneyTheme, String> {
}
