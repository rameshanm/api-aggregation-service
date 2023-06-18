package com.rame.apiaggregationservice.model;

import java.util.List;
import java.util.Map;

public class Shipments {
    private Map<String, List<String>> shipmentResponse;

    public Shipments(Map<String, List<String>> shipmentResponse) {
        this.shipmentResponse = shipmentResponse;
    }

    public Map<String, List<String>> getShipmentResponse() {
        return shipmentResponse;
    }

    public void setShipmentResponse(Map<String, List<String>> shipmentResponse) {
        this.shipmentResponse = shipmentResponse;
    }

    @Override
    public String toString() {
        return "Shipments{" +
                "shipmentResponse=" + shipmentResponse +
                '}';
    }
}
