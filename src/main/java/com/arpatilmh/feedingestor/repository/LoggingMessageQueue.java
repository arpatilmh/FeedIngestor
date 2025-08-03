package com.arpatilmh.feedingestor.repository;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingMessageQueue implements IMessageQueue {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMessageQueue.class);

    @Override
    public void sendMessage(FeedMessage feedMessage) {
        logger.info("Data ingested: {}", feedMessage.toString());
    }
}
