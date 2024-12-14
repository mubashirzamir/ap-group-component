package com.smart_cities.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class UriConfiguration {
    private String provider01 = System.getenv().getOrDefault("PROVIDER_01_ROUTE_URI", "http://localhost:9081");
    private String provider02 = System.getenv().getOrDefault("PROVIDER_02_ROUTE_URI", "http://localhost:9082");
    private String provider03 = System.getenv().getOrDefault("PROVIDER_03_ROUTE_URI", "http://localhost:9083");
    private String city = System.getenv().getOrDefault("CITY_ROUTE_URI", "http://localhost:8083");

    public String getProvider01() {
        return provider01;
    }

    public void setProvider01(String provider01) {
        this.provider01 = provider01;
    }

    public String getProvider02() {
        return provider02;
    }

    public void setProvider02(String provider02) {
        this.provider02 = provider02;
    }

    public String getProvider03() {
        return provider03;
    }

    public void setProvider03(String provider03) {
        this.provider03 = provider03;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
