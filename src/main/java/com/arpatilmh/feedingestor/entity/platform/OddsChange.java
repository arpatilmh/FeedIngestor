package com.arpatilmh.feedingestor.entity.platform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OddsChange extends FeedMessage {
    private Odds odds;

    public OddsChange(String eventId, Odds odds) {
        super(MessageType.ODDS_CHANGE, eventId);
        this.odds = odds;
    }

    public String toString() {
        return "OddsChange{" +
                "messageType=" + getMessageType() +
                ", eventId='" + getEventId() + '\'' +
                ", odds=" + odds.toString() +
                '}';
    }
}
