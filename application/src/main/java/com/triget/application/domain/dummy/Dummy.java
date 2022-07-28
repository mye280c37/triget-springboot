package com.triget.application.domain.dummy;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "dummy")
public class Dummy {

    @Id
    private String _id;
    private int x;

}
