package com.triget.application.web.dto.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
public class SkyScannerSearchBestDto {
    Itineraries itineraries;
    Context context;
}
