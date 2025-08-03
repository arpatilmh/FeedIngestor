package com.arpatilmh.feedingestor.service;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface for normalizing feed data into a FeedMessage.
 * This interface defines the contract for normalizing JSON feed data into a FeedMessage object.
 * Implementations of this interface can provide different normalization strategies.
 */

public interface INormalizer {
    FeedMessage normalize(JsonNode feedData) throws IllegalArgumentException;
}
