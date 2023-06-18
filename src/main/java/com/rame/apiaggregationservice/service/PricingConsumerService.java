package com.rame.apiaggregationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PricingConsumerService {

    Logger LOG = LoggerFactory.getLogger(PricingConsumerService.class);

    BlockingQueue<List<String>> bQueue =  new LinkedBlockingQueue<List<String>>();

    private List<String> queries;
    public PricingConsumerService(List<String> queries) {
        this.queries = queries;
    }
}
