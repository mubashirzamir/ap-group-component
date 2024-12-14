package com.smart_cities.citizen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProviderNotifier {
    @Value("${GATEWAY_URL}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProviderNotifier.class);

    @Autowired
    public ProviderNotifier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Notifies the provider with the reading data.
     *
     * @param reading    The reading data.
     * @param providerId The ID of the provider to notify.
     */
    @Async
    public void notify(Map<String, Object> reading, Long providerId) {
        try {
            String providerUrl = this.gatewayUrl + "/provider" + "/" + providerId + "/consumptions";
            ProviderNotifier.logger.info(providerUrl + " Generated: " + reading);
            Object response = this.restTemplate.postForObject(providerUrl, reading, String.class);
            ProviderNotifier.logger.info("Provider Response: " + response.toString());
        } catch (Exception e) {
            ProviderNotifier.logger.error(e.getMessage());
        }
    }
}
