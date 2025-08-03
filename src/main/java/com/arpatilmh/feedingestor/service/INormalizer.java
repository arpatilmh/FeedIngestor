package com.arpatilmh.feedingestor.service;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;
import com.fasterxml.jackson.databind.JsonNode;


public interface INormalizer {
    FeedMessage normalize(JsonNode feedData) throws IllegalArgumentException;
}
