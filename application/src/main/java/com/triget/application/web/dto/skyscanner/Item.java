package com.triget.application.web.dto.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
public class Item {
    private String id;
    private TmpObject price;
    private List<Leg> legs;
    private float score;
    private String deeplink;
}
