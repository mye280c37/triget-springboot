package com.triget.application.domain.place;

import com.triget.application.domain.airport.Airport;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "place")
public class Place {
    @Id
    private ObjectId _id;
    @Field("display_name")
    private String displayName;
    @Field("search_name")
    private String searchName;

}
