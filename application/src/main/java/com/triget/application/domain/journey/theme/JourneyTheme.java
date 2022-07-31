package com.triget.application.domain.journey.theme;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "journey_theme")
public class JourneyTheme {

    @Id
    private String id;
    private String koreanName;
    private String englishName;
}
