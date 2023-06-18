package com.rame.apiaggregationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Callable;

public class ThrottledExecutableService implements Callable {

    Logger LOG = LoggerFactory.getLogger(ThrottledExecutableService.class);
    private static final String BASE_URL = "http://localhost:8082";

    private final HttpClient httpClient;

    private List<String> queries;

    public ThrottledExecutableService(List<String> queries) {
        this.httpClient = HttpClient.newHttpClient();
        this.queries = queries;
    }

    @Override
    public HttpResponse<String> call() throws Exception {
        LOG.info("Entering the Call method");
        String apiPath;
        if(queries.size() > 0) {
            apiPath = "/pricing?q=";
        } else {
            apiPath = "/pricing";
        }

        URI uri = new URI(String.join("", BASE_URL, apiPath, String.join(",", queries)));
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
