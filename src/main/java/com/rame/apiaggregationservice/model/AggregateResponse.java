package com.rame.apiaggregationservice.model;

public class AggregateResponse {

    private String pricing;
    private String track;
    private String shipments;

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getShipments() {
        return shipments;
    }

    public void setShipments(String shipments) {
        this.shipments = shipments;
    }

    @Override
    public String toString() {
        return "AggregateResponse{" +
                "pricing=" + pricing +
                ", track=" + track +
                ", shipments=" + shipments +
                '}';
    }
}
