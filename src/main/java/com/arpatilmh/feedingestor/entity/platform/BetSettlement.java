package com.arpatilmh.feedingestor.entity.platform;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a bet settlement message in the feed.
 * Contains the outcome of the bet settlement.
 */

@Setter
@Getter
public class BetSettlement extends FeedMessage {
    private Outcome outcome;

    public BetSettlement(String eventId, Outcome outcome) {
        super(MessageType.BET_SETTLEMENT, eventId);
        this.outcome = outcome;
    }

    public String toString() {
        return "BetSettlement{" +
                "messageType=" + getMessageType() +
                ", eventId='" + getEventId() + '\'' +
                ", outcome=" + outcome +
                '}';
    }
}
