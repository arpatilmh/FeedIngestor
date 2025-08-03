package com.arpatilmh.feedingestor.service;

import com.fasterxml.jackson.databind.JsonNode;


public interface IIngestionService {
    String ingest(JsonNode feedData, String provider);
}
