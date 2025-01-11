package com.kf7014.citizenmicroservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Configuration properties for the provider service.
 * 
 * <p>Holds configuration related to the provider's API URL used for fetching
 * and sending meter readings.</p>
 * 
 * <p>Properties are prefixed with {@code provider} in the application's configuration files.</p>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Configuration
@ConfigurationProperties(prefix = "provider")
@Validated
public class ProviderProperties {

    /**
     * The URL of the provider's readings API.
     * 
     * <p>This URL is used to fetch the latest readings and send new readings
     * to the provider.</p>
     */
    @NotBlank(message = "Provider URL must not be blank")
    private String url; // Automatic readings API URL

    /**
     * Retrieves the provider API URL.
     * 
     * @return The provider's API URL as a {@link String}.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the provider API URL.
     * 
     * @param url The provider's API URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
