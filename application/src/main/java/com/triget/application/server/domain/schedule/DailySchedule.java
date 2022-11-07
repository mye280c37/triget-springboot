package com.triget.application.server.domain.schedule;

import com.triget.application.server.controller.dto.CustomProductPage;
import com.triget.application.server.controller.dto.ProductResponse;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@Document("daily_schedule")
public class DailySchedule {
    @Id
    private ObjectId _id;
    private int order;
    @Field("route_num")
    private int routeNum;
    private List<ProductResponse> vertices;
    private List<Route> edges;

    public DailySchedule(ObjectId _id, int order, int routeNum, List<ProductResponse> vertices, List<Route> edges) {
        this._id = _id;
        this.order = order;
        this.routeNum = routeNum;
        this.vertices = vertices;
        this.edges = edges;
    }
}
