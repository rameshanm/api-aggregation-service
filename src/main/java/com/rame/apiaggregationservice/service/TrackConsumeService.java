package com.rame.apiaggregationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Callable;

public class TrackConsumeService implements Callable {

    Logger LOG = LoggerFactory.getLogger(TrackConsumeService.class);

    private String baseUrl;

    private List<String> queries;
    private String name;

    public TrackConsumeService(List<String> queries, String name, String baseUrl) {
        this.queries = queries;
        this.name = name;
        this.baseUrl = baseUrl;
    }

    @Override
    public HttpResponse<String> call() throws Exception {
        LOG.info("Entering the Call method for {} ", name);
        String apiPath;
        if(queries.size() > 0) {
            apiPath = "/track?q=";
        } else {
            apiPath = "/track";
        }

        URI uri = new URI(String.join("", baseUrl, apiPath, String.join(",", queries)));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
