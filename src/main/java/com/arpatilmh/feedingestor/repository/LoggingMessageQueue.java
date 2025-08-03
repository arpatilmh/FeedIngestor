package com.arpatilmh.feedingestor.repository;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * LoggingMessageQueue is a simple implementation of IMessageQueue that logs the messages
 * instead of sending them to an actual message queue.
 */
@Component
public class LoggingMessageQueue implements IMessageQueue {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMessageQueue.class);

    @Override
    public void sendMessage(FeedMessage feedMessage) {
        logger.info("Data ingested: {}", feedMessage.toString());
    }
}
