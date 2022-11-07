package com.triget.application.server.entity.skyscanner;

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
