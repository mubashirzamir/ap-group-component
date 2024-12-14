package com.smart_cities.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "provider01=http://localhost:${wiremock.server.port}/provider/1",
                "provider02=http://localhost:${wiremock.server.port}/provider/2",
                "provider03=http://localhost:${wiremock.server.port}/provider/3"
        })
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    void testGetRouteProvider() {
        stubFor(get(urlEqualTo("/consumptions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"forwarded\": true}")
                        .withHeader("Content-Type", "application/json")));

        webClient.get().uri("/provider/1/consumptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.forwarded").isEqualTo(true);

        webClient.get().uri("/provider/2/consumptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.forwarded").isEqualTo(true);

        webClient.get().uri("/provider/3/consumptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.forwarded").isEqualTo(true);
    }

    @Test
    void testCircuitBreakerFallback() {
        stubFor(get(urlEqualTo("/consumptions"))
                .willReturn(aResponse()
                        .withFixedDelay(5000)
                        .withBody("{\"status\":\"timeout\"}")));

        webClient.get().uri("/provider/1/consumptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(new String(response.getResponseBody()))
                        .isEqualTo("Service is currently unavailable"));

        webClient.get().uri("/provider/2/consumptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(new String(response.getResponseBody()))
                        .isEqualTo("Service is currently unavailable"));

        webClient.get().uri("/provider/3/consumptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(new String(response.getResponseBody()))
                        .isEqualTo("Service is currently unavailable"));
    }
}
