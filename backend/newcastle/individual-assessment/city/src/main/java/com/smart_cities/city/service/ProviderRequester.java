package com.smart_cities.city.service;

import com.smart_cities.city.dto.Consumption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ProviderRequester {
    @Value("${GATEWAY_URL}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProviderRequester.class);

    @Autowired
    public ProviderRequester(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Requests the consumption data from the provider.
     *
     * @param providerId  The ID of the provider.
     * @param periodStart The start of the period.
     * @param periodEnd   The end of the period.
     * @return CompletableFuture<List < Consumption>> The list of consumption data.
     */
    @Async
    public CompletableFuture<List<Consumption>> request(
            Long providerId,
            LocalDateTime periodStart,
            LocalDateTime periodEnd
    ) {
        try {
            List<Consumption> consumptions = List.of(
                    Objects.requireNonNull(this.restTemplate.getForObject(
                            this.buildUri(providerId, periodStart, periodEnd),
                            Consumption[].class)
                    )
            );

            ProviderRequester.logger.info("Provider " + providerId + " Response: " + consumptions);

            return CompletableFuture.completedFuture(consumptions);
        } catch (Exception e) {
            ProviderRequester.logger.error(e.getMessage());
        }

        return CompletableFuture.completedFuture(List.of());
    }

    /**
     * Builds the URI for the request.
     * The URI is built using the gateway URL, provider ID, and the period start and end.
     */
    public String buildUri(Long providerId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        return UriComponentsBuilder.fromUri(URI.create(this.gatewayUrl + "/provider" + "/" + providerId + "/consumptions"))
                .queryParam("consumptionPeriodStart", periodStart.toString())
                .queryParam("consumptionPeriodEnd", periodEnd.toString())
                .toUriString();
    }
}
