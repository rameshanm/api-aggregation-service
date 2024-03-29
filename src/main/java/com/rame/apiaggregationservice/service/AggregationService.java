package com.rame.apiaggregationservice.service;

import com.rame.apiaggregationservice.model.AggregateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AggregationService {

    Logger LOG = LoggerFactory.getLogger(AggregationService.class);

    @Value("${backend.service.base.url}")
    private String baseUrl;

    public Mono<AggregateResponse> aggregateResponse(List<String> pricingParams,
                                                              List<String> trackParams,
                                                              List<String> shipmentParams) {

        LOG.info("Entering the aggregate response Method");
        AggregateResponse response = consolidatePricingQueryParams(pricingParams, trackParams, shipmentParams);

        return Mono.just(response);
    }

    private AggregateResponse consolidatePricingQueryParams(List<String> pricingParams,
                                                         List<String> trackParams,
                                                         List<String> shipmentParams) {

        LOG.info("Executors to consolidate the Responses");

        AggregateResponse response = new AggregateResponse();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<HttpResponse<String>> pricingFuture =
                executor.submit(() -> new PricingConsumerService(pricingParams, "Pricing", baseUrl).call());

        Future<HttpResponse<String>> trackFuture =
                executor.submit(() -> new TrackConsumeService(trackParams, "Tracking", baseUrl).call());

        Future<HttpResponse<String>> shipFuture =
                executor.submit(() -> new ShipmentConsumeService(shipmentParams, "Shipment", baseUrl).call());

        try {
            response.setPricing(pricingFuture.get().body());
            response.setTrack(trackFuture.get().body());
            response.setShipments(shipFuture.get().body());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
