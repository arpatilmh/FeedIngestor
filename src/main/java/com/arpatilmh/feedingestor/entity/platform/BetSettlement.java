package com.arpatilmh.feedingestor.entity.platform;

import lombok.Getter;
import lombok.Setter;

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
