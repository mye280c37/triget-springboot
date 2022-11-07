package com.triget.application.server.service;

import com.triget.application.server.common.ObjectNotFoundException;
import com.triget.application.server.domain.airport.Airport;
import com.triget.application.server.repository.airport.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport findByIata(String iata) {
        return airportRepository.findByIata(iata).orElseThrow(
                () -> new ObjectNotFoundException("No matching airport found")
        );
    }
}
