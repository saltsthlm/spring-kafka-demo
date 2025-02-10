package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("average")
    public double getAverage() {
        return statsService.getAverage();
    }

    @GetMapping("aggregate")
    public double getAggregate() {
        return statsService.getAggregate();
    }

    @GetMapping("list")
    public List<Integer> getList() {
        return statsService.getList();
    }
}
