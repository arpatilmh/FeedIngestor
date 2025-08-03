package com.arpatilmh.feedingestor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class APITests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testProviderAlphaOddsChange() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123",
            "values": {
                "1": 2.0,
                "X": 3.1,
                "2": 3.8
            }
        }
        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("success");
    }

    @Test
    void testProviderAlphaBetSettlement() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "settlement",
            "event_id": "ev123",
            "outcome": "1"
        }
        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("success");
    }

    @Test
    void testProviderBetaOddsChange() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456",
            "odds": {
                "home": 1.95,
                "draw": 3.2,
                "away": 4.0
            }
        }
        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("success");
    }

    @Test
    void testProviderBetaBetSettlement() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "SETTLEMENT",
            "event_id": "ev456",
            "result": "away"
        }
        """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("success");
    }

    // ProviderAlpha ODDS_CHANGE error cases
    @Test
    void testAlphaOddsChangeMissingMsgType() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "event_id": "ev123",
            "values": {
                "1": 2.0,
                "X": 3.1,
                "2": 3.8
            }
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaOddsChangeMissingEventId() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "values": {
                "1": 2.0,
                "X": 3.1,
                "2": 3.8
            }
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaOddsChangeMissingValues() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaOddsChangeInvalidValuesType() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123",
            "values": "not_a_map"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaOddsChangeExtraField() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123",
            "values": {
                "1": 2.0,
                "X": 3.1,
                "2": 3.8
            },
            "extra": "field"
        }
        """;
        // Should still succeed if extra fields are ignored
        assertSuccessResponse(url, json);
    }

    @Test
    void testAlphaOddsChangeInvalidOddsValue() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123",
            "values": {
                "1": "not_a_number",
                "X": 3.1,
                "2": 3.8
            }
        }
        """;
        assertErrorResponse(url, json);
    }

    // ProviderAlpha BET_SETTLEMENT error cases
    @Test
    void testAlphaBetSettlementMissingMsgType() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "event_id": "ev123",
            "outcome": "1"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaBetSettlementMissingEventId() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "settlement",
            "outcome": "1"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaBetSettlementMissingOutcome() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "settlement",
            "event_id": "ev123"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testAlphaBetSettlementInvalidOutcome() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "settlement",
            "event_id": "ev123",
            "outcome": "invalid"
        }
        """;
        assertErrorResponse(url, json);
    }

    // ProviderBeta ODDS_CHANGE error cases
    @Test
    void testBetaOddsChangeMissingType() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "event_id": "ev456",
            "odds": {
                "home": 1.95,
                "draw": 3.2,
                "away": 4.0
            }
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaOddsChangeMissingEventId() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "odds": {
                "home": 1.95,
                "draw": 3.2,
                "away": 4.0
            }
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaOddsChangeMissingOdds() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaOddsChangeInvalidOddsType() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456",
            "odds": "not_a_map"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaOddsChangeExtraField() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456",
            "odds": {
                "home": 1.95,
                "draw": 3.2,
                "away": 4.0
            },
            "extra": "field"
        }
        """;
        assertSuccessResponse(url, json);
    }

    @Test
    void testBetaOddsChangeInvalidOddsValue() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456",
            "odds": {
                "home": "not_a_number",
                "draw": 3.2,
                "away": 4.0
            }
        }
        """;
        assertErrorResponse(url, json);
    }

    // ProviderBeta BET_SETTLEMENT error cases
    @Test
    void testBetaBetSettlementMissingType() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "event_id": "ev456",
            "result": "away"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaBetSettlementMissingEventId() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "SETTLEMENT",
            "result": "away"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaBetSettlementMissingResult() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "SETTLEMENT",
            "event_id": "ev456"
        }
        """;
        assertErrorResponse(url, json);
    }

    @Test
    void testBetaBetSettlementInvalidResult() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "SETTLEMENT",
            "event_id": "ev456",
            "result": "invalid"
        }
        """;
        assertErrorResponse(url, json);
    }

    // Malformed JSON tests for ProviderAlpha
    @Test
    void testAlphaMalformedJsonMissingBracket() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123",
            "values": {
                "1": 2.0,
                "X": 3.1,
                "2": 3.8
            }
        """;
        assertMalformedJsonResponse(url, json);
    }

    @Test
    void testAlphaMalformedJsonExtraComma() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = """
        {
            "msg_type": "odds_update",
            "event_id": "ev123",
            "values": {
                "1": 2.0,
                "X": 3.1,
                "2": 3.8,
            }
        }
        """;
        assertMalformedJsonResponse(url, json);
    }

    @Test
    void testAlphaMalformedJsonNotJson() {
        String url = "http://localhost:" + port + "/provider-alpha/feed";
        String json = "not a json";
        assertMalformedJsonResponse(url, json);
    }

    // Malformed JSON tests for ProviderBeta
    @Test
    void testBetaMalformedJsonMissingBracket() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456",
            "odds": {
                "home": 1.95,
                "draw": 3.2,
                "away": 4.0
            }
        """;
        assertMalformedJsonResponse(url, json);
    }

    @Test
    void testBetaMalformedJsonExtraComma() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = """
        {
            "type": "ODDS",
            "event_id": "ev456",
            "odds": {
                "home": 1.95,
                "draw": 3.2,
                "away": 4.0,
            }
        }
        """;
        assertMalformedJsonResponse(url, json);
    }

    @Test
    void testBetaMalformedJsonNotJson() {
        String url = "http://localhost:" + port + "/provider-beta/feed";
        String json = "not a json";
        assertMalformedJsonResponse(url, json);
    }

    // Utility method for malformed JSON
    private void assertMalformedJsonResponse(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        assertThat(response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()).isTrue();
    }

    // Utility methods for assertions
    private void assertErrorResponse(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        assertThat(response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()).isTrue();
    }

    private void assertSuccessResponse(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("success");
    }
}