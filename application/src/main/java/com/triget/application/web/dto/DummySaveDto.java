package com.triget.application.web.dto;

import  com.triget.application.domain.dummy.Dummy;

import lombok.Data;

@Data
public class DummySaveDto {
    private int x;

    public Dummy toEntity() {
        Dummy dummy = new Dummy();
        dummy.setX(x);
        return dummy;
    }
}
