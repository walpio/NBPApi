package com.github.walpio;

import com.github.walpio.JsonParser.Rate;

import java.util.List;

public class Calculator {

    public double getAverageBid(List<Rate> rates) {
        double sum = 0.0;
        for (Rate element : rates) {
            sum += element.getBid();
        }
        double averageBid = sum/rates.size();
        return averageBid;
    }

    public double getAverageAsk(List<Rate> rates) {
        double sum = 0.0;
        for (Rate element : rates) {
            sum += element.getAsk();
        }
        double averageAsk = sum/rates.size();
        return averageAsk;
    }

    private double getVarianceOfAsk(List<Rate> rates) {
        double averageAsk = getAverageAsk(rates);
        double temp = 0.0;
        for (Rate element : rates) {
            temp += (element.getAsk() - averageAsk) * (element.getAsk() - averageAsk);
        }
        double varianceOfAsk = temp/(rates.size());
        return varianceOfAsk;
    }

    public double getStandardDeviationOfAsk(List<Rate> rates) {
        double standardDeviation = Math.sqrt(getVarianceOfAsk(rates));
        return standardDeviation;
    }
}