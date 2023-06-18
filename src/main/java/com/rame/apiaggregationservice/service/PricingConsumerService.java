package com.rame.apiaggregationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class PricingConsumerService implements Callable {

    Logger LOG = LoggerFactory.getLogger(PricingConsumerService.class);

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private List<String> queries;

    private String name;
    public PricingConsumerService(List<String> queries, String name) {
        this.queries = queries;
        this.name = name;
    }

    @Override
    public HttpResponse<String> call() throws Exception {
        LOG.info("Entering the Call method {} ", name);
        String apiPath;
        if(queries.size() > 0) {
            apiPath = "/pricing?q=";
        } else {
            apiPath = "/pricing";
        }

        URI uri = new URI(String.join("", "http://localhost:8082", apiPath, String.join(",", queries)));
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
