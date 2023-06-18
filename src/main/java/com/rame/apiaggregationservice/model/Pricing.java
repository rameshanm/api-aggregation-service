package com.rame.apiaggregationservice.model;

import java.io.Serializable;
import java.util.Map;

public class Pricing implements Serializable {
  private Map<String, Double> pricing;

    public Map<String, Double> getPricing() {
        return pricing;
    }

    public void setPricing(Map<String, Double> pricing) {
        this.pricing = pricing;
    }
}
