package com.arpatilmh.feedingestor.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface for the ingestion service that handles the normalization and ingestion of feed data.
 * It provides a method to ingest feed data from different providers.
 */

public interface IIngestionService {
    String ingest(JsonNode feedData, String provider);
}
