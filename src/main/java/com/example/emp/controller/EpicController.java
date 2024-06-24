package com.example.emp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.emp.model.EpicRequest;

@RestController
@RequestMapping("/api")
public class EpicController {

    private static final Logger logger = LoggerFactory.getLogger(EpicController.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EXTERNAL_API_URL = "https://gateway.eci.gov.in/api/v1/elastic/search-by-epic-from-national-display";

    @PostMapping("/submit")
    public ResponseEntity<String> submitEpicDetails(@RequestBody EpicRequest epicRequest) {
        // Log the received epicRequest details
        logger.info("Received EpicRequest: {}", epicRequest);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("isPortal","true");
        requestBody.put("epicNumber", epicRequest.getEpicID());
        requestBody.put("captchaData", epicRequest.getCaptchaInput());
        requestBody.put("captchaId", epicRequest.getCaptchaId());
        requestBody.put("securityKey","na");


        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Applicationname", "ELECTORAL-SEARCH");
        headers.set("Appname", "ELECTORAL-SEARCH");
        headers.set("Channelidobo", "ELECTORAL-SEARCH");
        headers.set("Content-Type", "application/json");
        headers.set("Referer", "https://gateway.eci.gov.in/");
        headers.set("Sec-Ch-Ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"");
        headers.set("Sec-Ch-Ua-Mobile", "?0");
        headers.set("Sec-Ch-Ua-Platform", "\"Windows\"");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            logger.info("Sending request to external API: {}", requestBody);
            ResponseEntity<String> response = restTemplate.exchange(EXTERNAL_API_URL, HttpMethod.POST, entity, String.class);
            logger.info("Received response from external API: {}", response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error occurred while calling external API: Status Code: {}, Response Body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("An error occurred while calling external API", e);
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        }
    }
}
