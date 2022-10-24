package com.triget.application.server.service;

import com.triget.application.server.common.ObjectNotFoundException;
import com.triget.application.server.domain.journey.Journey;
import com.triget.application.server.repository.journey.JourneyRepository;
import com.triget.application.server.controller.dto.ProductRecommendationRequest;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class JourneyService {
    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public Journey findById(String journeyId) {
        return journeyRepository.findById(new ObjectId(journeyId)).orElseThrow(
                () -> new ObjectNotFoundException("No matching journey found")
        );
    }

    public String createJourney(ProductRecommendationRequest dto) throws NullPointerException {
        return journeyRepository.save(dto.toEntity()).getId().toString();
    }

    public void updateJourney(Journey journey) {
        journeyRepository.save(journey);
    }
}
