package com.kf7014.citizenmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.kf7014.citizenmicroservice.model.Meter;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for the Citizen Microservice.
 * 
 * <p>Defines beans for smart meters, citizen meters, and the WebClient
 * used for external API interactions.</p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Configuration
public class AppConfig {

    /**
     * Creates and configures a list of smart meters.
     * 
     * <p>Initializes 50 smart meters with unique IDs prefixed with "SM".</p>
     * 
     * @return A {@link List} of {@link Meter} instances representing smart meters.
     */
    @Bean
    public List<Meter> smartMeters() {
        List<Meter> meters = new ArrayList<>();
        for (int i = 0; i < 50; i++) { // 50 smart meters
            String meterId = "SM" + i;
            meters.add(new Meter(meterId));
        }
        return meters;
    }

    /**
     * Creates and configures a list of citizen meters.
     * 
     * <p>Initializes 100 citizen meters with unique IDs prefixed with "CM".</p>
     * 
     * @return A {@link List} of {@link Meter} instances representing citizen meters.
     */
    @Bean
    public List<Meter> citizenMeters() {
        List<Meter> meters = new ArrayList<>();
        for (int i = 0; i < 100; i++) { // 100 citizen meters
            String meterId = "CM" + i;
            meters.add(new Meter(meterId));
        }
        return meters;
    }

    /**
     * Configures and provides a {@link WebClient} bean for external API communication.
     * 
     * <p>The {@link WebClient} is used to interact with the provider's automatic readings API.</p>
     * 
     * @return A configured {@link WebClient} instance.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
