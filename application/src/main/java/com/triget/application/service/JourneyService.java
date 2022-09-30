package com.triget.application.service;

import com.triget.application.domain.journey.Journey;
import com.triget.application.domain.journey.JourneyRepository;
import com.triget.application.web.dto.EntireProductListRequestDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JourneyService {
    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public Journey findById(String journeyId) throws Exception {
        Optional<Journey> journey = journeyRepository.findById(new ObjectId(journeyId));
        if (journey.isEmpty()){
            throw new Exception("");
        }
        else return journey.get();
    }

    public String createJourney(EntireProductListRequestDto dto) throws NullPointerException {
        return journeyRepository.save(dto.toEntity()).getId().toString();
    }

    public void updateJourney(Journey journey) {
        journeyRepository.save(journey);
    }
}
