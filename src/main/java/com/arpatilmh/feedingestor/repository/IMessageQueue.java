package com.arpatilmh.feedingestor.repository;

import com.arpatilmh.feedingestor.entity.platform.FeedMessage;

public interface IMessageQueue {
    void sendMessage(FeedMessage feedMessage);
}
