package com.arpatilmh.feedingestor.service;

import com.arpatilmh.feedingestor.entity.platform.*;
import com.arpatilmh.feedingestor.entity.thirdparty.BetaBets;
import com.arpatilmh.feedingestor.entity.thirdparty.BetaOdds;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BetaNormalizer implements INormalizer {

    private static final Logger logger = LoggerFactory.getLogger(BetaNormalizer.class);

    private static final Map<String, Outcome> outcomeMap = Map.of("home", Outcome.HOME, "draw", Outcome.DRAW, "away", Outcome.AWAY);

    @Override
    public FeedMessage normalize(JsonNode feedData) throws IllegalArgumentException {
        String messageType = feedData.has("type") ? feedData.get("type").asText() : "";

        switch (messageType) {
            case "ODDS":
                return toOddsChange(feedData);
            case "SETTLEMENT":
                return toBetSettlement(feedData);
            default:
                logger.error("unsupported message type {}", messageType);
                throw new IllegalArgumentException("Unsupported message type");
        }
    }

    private OddsChange toOddsChange(JsonNode feedData) throws IllegalArgumentException {
        Gson gson = new Gson();
        BetaOdds betaOdds;
        try {
            betaOdds = gson.fromJson(feedData.toString(), BetaOdds.class);
        } catch (Exception e) {
            logger.error("Error parsing feed data: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid feed data format");
        }

        betaOdds.validate();

        return new OddsChange(betaOdds.getEventId(),
                new Odds(betaOdds.getOdds().getHome(), betaOdds.getOdds().getDraw(), betaOdds.getOdds().getAway()));
    }

    private BetSettlement toBetSettlement(JsonNode feedData) throws IllegalArgumentException {
        Gson gson = new Gson();
        BetaBets betaBets;
        try {
            betaBets = gson.fromJson(feedData.toString(), BetaBets.class);
        } catch (Exception e) {
            logger.error("Error parsing feed data: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid feed data format");
        }

        betaBets.validate();

        return new BetSettlement(betaBets.getEventId(), outcomeMap.get(betaBets.getResult()));
    }
}
