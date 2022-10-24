package com.triget.application.server.service;

import com.triget.application.server.domain.product.flight.Flight;
import com.triget.application.server.repository.product.flight.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    private Sort sortByPrice() {
        return Sort.by(Sort.DEFAULT_DIRECTION, "price");
    }

    public List<Flight> findByJourneyId(String journeyId) {
        return flightRepository.findByJourneyId(journeyId, sortByPrice());
    }

    public Page<Flight> getFlightPage(String journeyId, float budget, int page) {
        PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.by("stopCount").and(Sort.by("score").descending())
        );
        return flightRepository.findByJourneyIdAndPriceLess(
                journeyId,
                budget,
                pageRequest
        );
    }
}
