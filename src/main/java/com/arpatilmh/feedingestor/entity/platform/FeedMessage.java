package com.arpatilmh.feedingestor.entity.platform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a generic feed message.
 * Contains the type of message and an event identifier.
 */

@Setter
@Getter
@AllArgsConstructor
public abstract class FeedMessage {
    private MessageType messageType;
    private String eventId;
}
