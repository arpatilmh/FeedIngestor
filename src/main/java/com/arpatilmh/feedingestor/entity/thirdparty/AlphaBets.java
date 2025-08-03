package com.arpatilmh.feedingestor.entity.thirdparty;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a settlement message for a betting event from alpha provider.
 */

@Getter
@Setter
public class AlphaBets {

    @SerializedName("msg_type")
    private String msgType;

    @SerializedName("event_id")
    private String eventId;

    private String outcome;

    public void validate() {
        if (!"settlement".equals(msgType)) {
            throw new IllegalArgumentException("Invalid msg_type: " + msgType);
        }

        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Missing event_id");
        }

        if (outcome == null || outcome.isEmpty()) {
            throw new IllegalArgumentException("Missing outcome");
        }

        if (!Set.of("1", "X", "2").contains(outcome)) {
            throw new IllegalArgumentException("Invalid outcome: " + outcome);
        }
    }
}