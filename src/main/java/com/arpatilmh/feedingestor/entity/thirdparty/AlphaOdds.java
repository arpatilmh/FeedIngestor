package com.arpatilmh.feedingestor.entity.thirdparty;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlphaOdds {

    @SerializedName("msg_type")
    private String msgType;

    @SerializedName("event_id")
    private String eventId;

    private Values values;

    @Getter
    public static class Values {
        @SerializedName("1")
        private Double one;

        @SerializedName("X")
        private Double draw;

        @SerializedName("2")
        private Double two;
    }

    public void validate() {
        if (!"odds_update".equals(msgType)) {
            throw new IllegalArgumentException("Invalid msg_type: " + msgType);
        }
        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Missing event_id");
        }
        if (values == null) {
            throw new IllegalArgumentException("Missing values");
        }

        if (values.one == null || values.draw == null || values.two == null) {
            throw new IllegalArgumentException("Missing one or more mandatory odds: 1, X, 2");
        }
    }

}
