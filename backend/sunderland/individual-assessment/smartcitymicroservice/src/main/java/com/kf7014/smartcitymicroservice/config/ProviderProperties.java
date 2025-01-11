package com.kf7014.smartcitymicroservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Configuration properties for external service providers.
 * <p>
 * This class maps to properties prefixed with {@code providers} in the application's configuration files
 * (e.g., {@code application.yml}, {@code application.properties}). It holds a list of provider configurations,
 * each containing an identifier and a base URL.
 * </p>
 * 
 * <p>
 * The {@code @Validated} annotation ensures that the configuration properties are validated upon application startup.
 * </p>
 * 
 * <p>
 * Example configuration:
 * </p>
 * 
 * <pre>
 * providers:
 *   - id: "provider1"
 *     baseUrl: "https://api.provider1.com"
 *   - id: "provider2"
 *     baseUrl: "https://api.provider2.com"
 * </pre>
 * 
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@Configuration
@ConfigurationProperties(prefix = "providers")
@Validated
public class ProviderProperties {

    /**
     * A list of configured external service providers.
     * <p>
     * Must not be empty. Each provider must have a unique identifier and a base URL.
     * </p>
     */
    @NotEmpty(message = "At least one provider configuration must be specified.")
    private List<ProviderConfig> providers;

    /**
     * Configuration details for a single external service provider.
     */
    public static class ProviderConfig {

        /**
         * The unique identifier for the provider.
         * <p>
         * This ID is used to reference the provider within the application.
         * </p>
         */
        @NotEmpty(message = "Provider ID cannot be empty.")
        private String id;

        /**
         * The base URL for the provider's API.
         * <p>
         * All API endpoints for the provider are constructed relative to this base URL.
         * </p>
         */
        @NotEmpty(message = "Provider baseUrl cannot be empty.")
        private String baseUrl;

        /**
         * Retrieves the provider's unique identifier.
         *
         * @return the provider ID
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the provider's unique identifier.
         *
         * @param id the provider ID to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Retrieves the provider's base URL.
         *
         * @return the provider base URL
         */
        public String getBaseUrl() {
            return baseUrl;
        }

        /**
         * Sets the provider's base URL.
         *
         * @param baseUrl the provider base URL to set
         */
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    /**
     * Retrieves the list of configured providers.
     *
     * @return a list of {@link ProviderConfig} objects
     */
    public List<ProviderConfig> getProviders() {
        return providers;
    }

    /**
     * Sets the list of configured providers.
     *
     * @param providers the list of {@link ProviderConfig} objects to set
     */
    public void setProviders(List<ProviderConfig> providers) {
        this.providers = providers;
    }
}
