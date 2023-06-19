package com.rame.apiaggregationservice.controller;

import com.rame.apiaggregationservice.model.AggregateResponse;
import com.rame.apiaggregationservice.service.AggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AggregationController {

    Logger LOG = LoggerFactory.getLogger(AggregationController.class);

    private AggregationService service;

    @Autowired
    public AggregationController(AggregationService service) {
        this.service = service;
    }

    @GetMapping("/aggregation")
    public ResponseEntity<Mono<AggregateResponse>> getAggregatedResponse(
                                        @RequestParam(value = "pricing", required = false) String countryCodes,
                                        @RequestParam(value = "track", required = false) String orderIds,
                                        @RequestParam(value = "shipments", required = false) String orders) {

        List<String> pricingParams, trackParams, shipmentParams;
        if(countryCodes != null) {
            pricingParams = Arrays.stream(countryCodes.split(",")).toList();
        } else {
            pricingParams = new ArrayList<>();
        }
        if(orderIds != null) {
            trackParams = Arrays.stream(orderIds.split(",")).toList();
        } else {
            trackParams = new ArrayList<>();
        }
        if(orders != null) {
            shipmentParams = Arrays.stream(orders.split(",")).toList();
        } else {
            shipmentParams = new ArrayList<>();
        }

        Mono<AggregateResponse> result = service.aggregateResponse(pricingParams, trackParams, shipmentParams);

        return ResponseEntity.ok(result);

    }

}
