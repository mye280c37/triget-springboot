package com.triget.application.server.controller;

import com.triget.application.server.entity.LikeProductList;
import com.triget.application.server.domain.schedule.Schedule;
import com.triget.application.server.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    @Autowired
    private final ScheduleService scheduleService;

    @PostMapping()
    public Schedule createSchedule(@Validated @RequestBody LikeProductList likeProductList) {
        return scheduleService.createSchedule(likeProductList);
    }

    @PostMapping("/auth")
    public Schedule saveSchedule(@Validated @RequestBody Schedule schedule) {
        return scheduleService.saveSchedule(schedule);
    }
}
