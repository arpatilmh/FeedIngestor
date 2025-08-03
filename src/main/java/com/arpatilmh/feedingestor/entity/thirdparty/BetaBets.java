package com.arpatilmh.feedingestor.entity.thirdparty;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BetaBets {
    @SerializedName("type")
    private String type;

    @SerializedName("event_id")
    private String eventId;

    @SerializedName("result")
    private String result;

    public void validate() {
        if (!"SETTLEMENT".equals(type)) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Missing event_id");
        }

        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException("Missing result");
        }

        if (!Set.of("home", "draw", "away").contains(result)) {
            throw new IllegalArgumentException("Invalid result: " + result);
        }


    }
}
