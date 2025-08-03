package com.arpatilmh.feedingestor.repository;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;
/**
 * Interface for message queue operations.
 * This interface defines the contract for sending messages to a message queue.
 * Implementations of this interface can provide different message queue mechanisms.
 */
public interface IMessageQueue {
    void sendMessage(FeedMessage feedMessage);
}
