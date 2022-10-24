package com.triget.application.server.controller.dto.skyscanner;

import lombok.Getter;

import java.util.List;

@Getter
public class Bucket {
    private String id;
    private String name;
    private List<Item> items;
}
