package com.arpatilmh.feedingestor.entity.platform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class FeedMessage {
    private MessageType messageType;
    private String eventId;
}
