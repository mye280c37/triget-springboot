package com.triget.application.service;

import com.triget.application.domain.journey.JourneyRepository;
import com.triget.application.domain.theme.JourneyTheme;
import com.triget.application.domain.theme.JourneyThemeRepository;
import com.triget.application.web.dto.ProductListRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductListService {
    private JourneyRepository journeyRepository;
    private JourneyThemeRepository journeyThemeRepository;

    @Transactional
    public String save(ProductListRequestDto dto){
        JourneyTheme journeyTheme = journeyThemeRepository.findByKoreanName(dto.getTheme()).get(0);
        return journeyRepository.save(dto.toEntity(journeyTheme)).getId();
    }
}
