package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {
    private final AmountsRepository amountsRepository;

    public StatsService(AmountsRepository amountsRepository) {
        this.amountsRepository = amountsRepository;
    }

    public double getAverage() {
        return amountsRepository.getAmounts().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public int getAggregate() {
        return amountsRepository.getAmounts().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public List<Integer> getList() {
        return amountsRepository.getAmounts();
    }
}
