package com.arpatilmh.feedingestor.service;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;
import com.arpatilmh.feedingestor.repository.IMessageQueue;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngestionServiceImpl implements IIngestionService {

    private static final Logger logger = LoggerFactory.getLogger(IngestionServiceImpl.class);

    @Autowired
    private IMessageQueue messageQueue;

    @Override
    public String ingest(JsonNode feedData, String provider) {
        INormalizer normalizer = NormalizerFactory.getNormalizer(provider);
        if (normalizer == null) {
            logger.error("Unsupported feed provider");
            throw new IllegalArgumentException("Unsupported feed provider");
        }

        try {
            FeedMessage feedMessage = normalizer.normalize(feedData);
            messageQueue.sendMessage(feedMessage);
        } catch (IllegalArgumentException e) {
            logger.error("Cannot ingest feed data: {}", e.getMessage());
            return "Cannot ingest feed data: " + e.getMessage();
        }


        return "success";
    }
}
