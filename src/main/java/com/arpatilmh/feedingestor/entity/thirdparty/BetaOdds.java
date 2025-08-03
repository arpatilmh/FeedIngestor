package com.arpatilmh.feedingestor.entity.thirdparty;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the odds for a sporting event from beta provider.
 */

@Getter
@Setter
public class BetaOdds {
    @SerializedName("type")
    private String type;

    @SerializedName("event_id")
    private String eventId;

    @SerializedName("odds")
    private Odds odds;

    @Getter
    @Setter
    public static class Odds {
        @SerializedName("home")
        private Double home;

        @SerializedName("draw")
        private Double draw;

        @SerializedName("away")
        private Double away;
    }

    public void validate() {
        if (!"ODDS".equals(type)) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Missing event_id");
        }

        if (odds == null) {
            throw new IllegalArgumentException("Missing odds");
        }

        if (odds.home == null || odds.draw == null || odds.away == null) {
            throw new IllegalArgumentException("Missing one or more mandatory odds: home, draw, away");
        }
    }

}
