package com.arpatilmh.feedingestor.service;

import com.arpatilmh.feedingestor.entity.platform.*;
import com.arpatilmh.feedingestor.entity.thirdparty.AlphaBets;
import com.arpatilmh.feedingestor.entity.thirdparty.AlphaOdds;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
/**
    * AlphaNormalizer is an implementation of the INormalizer interface that normalizes feed data
    * from the Alpha provider into a FeedMessage. It handles odds updates and bet settlements.
    * The normalization process includes parsing JSON data and converting it into OddsChange or BetSettlement objects.
 */

public class AlphaNormalizer implements INormalizer {

    private static final Logger logger = LoggerFactory.getLogger(AlphaNormalizer.class);

    private static final Map<String, Outcome> outcomeMap = Map.of("1", Outcome.HOME, "X", Outcome.DRAW, "2", Outcome.AWAY);

    @Override
    public FeedMessage normalize(JsonNode feedData) throws IllegalArgumentException {
        String messageType = feedData.has("msg_type") ? feedData.get("msg_type").asText() : "";


        switch (messageType) {
            case "odds_update":
                return toOddsChange(feedData);

            case "settlement":
                return toBetSettlement(feedData);
            default:
                logger.error("unsupported message type");
                throw new IllegalArgumentException("Unsupported message type");
        }
    }

    private OddsChange toOddsChange(JsonNode feedData) throws IllegalArgumentException {
        Gson gson = new Gson();
        AlphaOdds alphaOdds;
        try {
            alphaOdds = gson.fromJson(feedData.toString(), AlphaOdds.class);
        } catch (Exception e) {
            logger.error("Error parsing feed data: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid feed data format");
        }

        alphaOdds.validate();

        return new OddsChange(alphaOdds.getEventId(),
                new Odds(alphaOdds.getValues().getOne(), alphaOdds.getValues().getDraw(), alphaOdds.getValues().getTwo()));
    }

    private BetSettlement toBetSettlement(JsonNode feedData) throws IllegalArgumentException {
        Gson gson = new Gson();
        AlphaBets alphaBets;
        try {
            alphaBets = gson.fromJson(feedData.toString(), AlphaBets.class);
        } catch (Exception e) {
            logger.error("Error parsing feed data: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid feed data format");
        }

        alphaBets.validate();

        return new BetSettlement(alphaBets.getEventId(), outcomeMap.get(alphaBets.getOutcome()));
    }

}
