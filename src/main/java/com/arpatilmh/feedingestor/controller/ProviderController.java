package com.arpatilmh.feedingestor.controller;

import com.arpatilmh.feedingestor.service.IIngestionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    private IIngestionService ingestionService;

    @PostMapping("/provider-alpha/feed")
    public ResponseEntity<String> feedAlpha(@RequestBody JsonNode feedData) {
        String ingestionResult = ingestionService.ingest(feedData, "alpha");
        if ("success".equals(ingestionResult)) {
            return ResponseEntity.ok(ingestionResult);
        } else {
            return ResponseEntity.badRequest().body(ingestionResult);
        }
    }

    @PostMapping("/provider-beta/feed")
    public ResponseEntity<String> feedBeta(@RequestBody JsonNode feedData) {
        String ingestionResult = ingestionService.ingest(feedData, "beta");
        if ("success".equals(ingestionResult)) {
            return ResponseEntity.ok(ingestionResult);
        } else {
            return ResponseEntity.badRequest().body(ingestionResult);
        }
    }
}
