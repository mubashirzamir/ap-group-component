package com.smart_cities.citizen.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ReadingGenerator {
    /**
     * Generates a new reading that is greater than the last reading.
     *
     * @param lastReading The last reading.
     * @return The new reading.
     */
    public Long generate(Long lastReading) {
        return ThreadLocalRandom.current().nextLong(lastReading, lastReading + 5);
    }
}
