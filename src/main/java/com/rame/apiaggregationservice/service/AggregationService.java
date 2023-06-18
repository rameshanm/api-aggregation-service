package com.rame.apiaggregationservice.service;

import com.rame.apiaggregationservice.model.AggregateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class AggregationService {

    Logger LOG = LoggerFactory.getLogger(AggregationService.class);
    private static final String BASE_URL = "http://localhost:8082";
    private final WebClient webClient;
    private final HttpClient httpClient;

    public AggregationService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
        this.httpClient = HttpClient.newBuilder().build();
    }

    public Mono<AggregateResponse> aggregateResponse(List<String> pricingParams,
                                                     List<String> trackParams,
                                                     List<String> shipmentParams) {

        LOG.info("Entering the aggregate response Method");
        AggregateResponse response = new AggregateResponse();

        try {

//            BlockingQueue<List<String>> pricingQ =  new LinkedBlockingQueue<List<String>>();
//            BlockingQueue<List<String>> trackQ =  new LinkedBlockingQueue<List<String>>();
//            BlockingQueue<List<String>> shipmentsQ =  new LinkedBlockingQueue<List<String>>();
//
//            pricingQ.put(pricingParams);
//            trackQ.put(trackParams);
//            shipmentsQ.put(shipmentParams);
//
//            if(pricingQ.size() == 5) {
//                consumePricingWebService1(pricingParams);
//            } else {
//                pricingQ.put(pricingParams);
//            }

            String pricingResponse = consumePricingWebService1(pricingParams).body();
            String trackResponse = consumeTrackWebService1(trackParams).body();
            String shipmentResponse = consumeShipmentsWebService1(shipmentParams).body();

            response.setPricing(pricingResponse);
            response.setTrack(trackResponse);
            response.setShipments(shipmentResponse);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Mono.just(response);
    }

    public HttpResponse<String> consumePricingWebService1(List<String> queries) throws URISyntaxException, IOException, InterruptedException {

        String queryParams = String.join(",", queries);
        String apiPath;
        if(queries.size() > 0) {
            apiPath = "/pricing?q=";
        } else {
            apiPath = "/pricing";
        }

        URI uri = new URI(String.join("", BASE_URL, apiPath, queryParams));
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpResponse<String> consumeTrackWebService1(List<String> queries) throws URISyntaxException, IOException, InterruptedException {
        String queryParams = String.join(",", queries);
        String apiPath;
        if(queries.size() > 0) {
            apiPath = "/track?q=";
        } else {
            apiPath = "/track";
        }

        URI uri = new URI(String.join("", BASE_URL, apiPath, queryParams));
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpResponse<String> consumeShipmentsWebService1(List<String> queries) throws URISyntaxException, IOException, InterruptedException {
        String queryParams = String.join(",", queries);
        String apiPath;
        if(queries.size() > 0) {
            apiPath = "/shipments?q=";
        } else {
            apiPath = "/shipments";
        }
        URI uri = new URI(String.join("", BASE_URL, apiPath, queryParams));
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }


    public Mono<String> consumePricingWebService() {
        System.out.println("Entering Consume Pricing Webservice...");
        return this.webClient.get().uri( ub -> ub.path("/pricing").queryParam("q", Arrays.asList("IN","NL")).build()).retrieve().bodyToMono(String.class);
    }

    public Mono<String> consumeTrackWebService()  {
        System.out.println("Entering Consume Track Webservice...");

        return this.webClient.get().uri( ub -> ub.path("/track").queryParam("q", Arrays.asList("123","321")).build()).retrieve().bodyToMono(String.class);
    }
    public Mono<Map> consumeShipmentsWebService() {
        System.out.println("Entering Consume Shipments Webservice...");
        return this.webClient.get().uri( ub -> ub.path("/shipments").queryParam("q", Arrays.asList("123","321")).build()).retrieve().bodyToMono(Map.class);
    }


}
