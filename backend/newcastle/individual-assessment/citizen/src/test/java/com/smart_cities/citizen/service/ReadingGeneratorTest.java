package com.smart_cities.citizen.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadingGeneratorTest {
    @Test
    void generate() {
        // Arrange
        ReadingGenerator readingGenerator = new ReadingGenerator();
        Long lastReading = 10L;
        // Act
        Long newReading = readingGenerator.generate(lastReading);
        // Assert
        assertTrue(newReading >= lastReading);
    }
}