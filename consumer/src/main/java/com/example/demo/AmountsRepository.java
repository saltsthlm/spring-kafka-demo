package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AmountsRepository {

    private final ArrayList<Integer> amounts;

    public AmountsRepository() {
        amounts = new ArrayList<>();
    }

    public void addAmount(Integer amount) {
        amounts.add(amount);
    }

    public ArrayList<Integer> getAmounts() {
        return amounts;
    }
}
