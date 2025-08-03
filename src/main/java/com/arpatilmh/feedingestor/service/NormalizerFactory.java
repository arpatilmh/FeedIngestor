package com.arpatilmh.feedingestor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormalizerFactory {

    private static final Logger logger = LoggerFactory.getLogger(NormalizerFactory.class);

    public static INormalizer getNormalizer(String provider) {
        switch (provider) {
            case "alpha":
                return new AlphaNormalizer();
            case "beta":
                return new BetaNormalizer();
            default:
                logger.error("Normalizer not found : {}", provider);
                return null;
        }
    }
}
