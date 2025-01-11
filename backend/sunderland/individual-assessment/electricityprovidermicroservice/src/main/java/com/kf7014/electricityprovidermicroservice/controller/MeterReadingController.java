package com.kf7014.electricityprovidermicroservice.controller;

import com.kf7014.electricityprovidermicroservice.model.MeterReading;
import com.kf7014.electricityprovidermicroservice.service.MeterReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * REST controller for managing meter readings.
 * <p>
 * This controller provides endpoints to receive meter readings, submit manual readings,
 * and retrieve meter readings based on various criteria such as timestamp and meter ID.
 * </p>
 * 
 * @see MeterReadingService
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/api/readings")
@Validated
public class MeterReadingController {

    private final MeterReadingService service;

    /**
     * Constructs a new {@code MeterReadingController} with the specified service.
     *
     * @param service the {@link MeterReadingService} used to handle meter reading operations
     */
    public MeterReadingController(MeterReadingService service) {
        this.service = service;
    }

    /**
     * Receives and stores a smart meter reading.
     *
     * <p>
     * This endpoint accepts a {@link MeterReading} object from the request body, validates it,
     * and saves it to the repository. It is intended for automated or smart meter submissions.
     * </p>
     *
     * @param meterReading the {@link MeterReading} to be received and stored
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @Operation(summary = "Receive Smart Meter Reading", description = "Receives and stores a smart meter reading.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meter reading received and stored.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error processing meter reading.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/smartmeter")
    public ResponseEntity<String> receiveMeterReading(
            @Valid @RequestBody MeterReading meterReading) {
        try {
            service.saveMeterReading(meterReading);
            return ResponseEntity.status(HttpStatus.CREATED).body("Meter reading received and stored.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error processing meter reading: " + e.getMessage());
        }
    }

    /**
     * Submits and stores a manual meter reading.
     *
     * <p>
     * This endpoint accepts a {@link MeterReading} object from the request body, validates it,
     * and saves it to the repository. It is intended for manual submissions by users.
     * </p>
     *
     * @param meterReading the {@link MeterReading} to be submitted manually and stored
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @Operation(summary = "Submit Manual Meter Reading", description = "Submits and stores a manual meter reading.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Manual meter reading received and stored.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error processing manual meter reading.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/manual")
    public ResponseEntity<String> submitManualReading(
            @Valid @RequestBody MeterReading meterReading) {
        try {
            service.saveManualMeterReading(meterReading);
            return ResponseEntity.status(HttpStatus.CREATED).body("Manual meter reading received and stored.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error processing manual meter reading: " + e.getMessage());
        }
    }

    /**
     * Retrieves all meter readings recorded since a specified timestamp.
     *
     * <p>
     * This endpoint fetches a list of {@link MeterReading} objects that have a timestamp later than
     * the provided {@code since} parameter. The {@code since} parameter is mandatory.
     * </p>
     *
     * @param since the ISO-8601 formatted {@link String} representing the starting timestamp
     * @return a {@link ResponseEntity} containing the list of {@link MeterReading} objects or appropriate HTTP status
     */
    @Operation(summary = "Get All Meter Readings", description = "Retrieves all meter readings recorded since the specified timestamp.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of meter readings retrieved.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(
                    schema = @Schema(implementation = MeterReading.class)
                )
            )
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No meter readings found since the specified timestamp.",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid 'since' timestamp format.",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<MeterReading>> getAllMeterReadings(
            @Parameter(description = "ISO-8601 formatted timestamp to retrieve readings since", required = true)
            @RequestParam @NotBlank(message = "'since' parameter is required") String since) {
        try {
            // Parse the 'since' parameter to validate it
            Instant sinceInstant = Instant.parse(since);

            List<MeterReading> readings = service.getAllMeterReadingsSince(sinceInstant);

            if (readings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(readings);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves meter readings for a specific meter ID recorded since a specified timestamp.
     *
     * <p>
     * This endpoint fetches a list of {@link MeterReading} objects associated with the given {@code meterId}
     * and have a timestamp later than the provided {@code since} parameter. Both parameters are mandatory.
     * </p>
     *
     * @param meterId the unique identifier of the meter
     * @param since   the ISO-8601 formatted {@link String} representing the starting timestamp
     * @return a {@link ResponseEntity} containing the list of {@link MeterReading} objects or appropriate HTTP status
     */
    @Operation(summary = "Get Meter Readings by Meter ID Since Timestamp", description = "Retrieves meter readings for a specific meter ID recorded since the specified timestamp.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "List of meter readings retrieved.",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                        schema = @Schema(implementation = MeterReading.class)
                    )
                )
            ),
            @ApiResponse(
                responseCode = "204",
                description = "No meter readings found since the specified timestamp.",
                content = @Content
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid 'since' timestamp format.",
                content = @Content
            )
    })
    @GetMapping("/meter/{meterId}")
    public ResponseEntity<List<MeterReading>> getMeterReadingsByMeterId(
            @Parameter(description = "Unique identifier of the meter", required = true)
            @PathVariable String meterId,
            @Parameter(description = "ISO-8601 formatted timestamp to retrieve readings since", required = true)
            @RequestParam @NotBlank(message = "'since' parameter is required") String since) {
        try {
            // Parse the 'since' parameter to validate it
            Instant sinceInstant = Instant.parse(since);

            List<MeterReading> readings = service.getMeterReadingsByMeterIdSince(meterId, sinceInstant);

            if (readings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(readings);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves the latest meter reading for a specific meter ID.
     *
     * <p>
     * This endpoint fetches the most recent {@link MeterReading} object associated with the given {@code meterId}.
     * If no readings are found for the specified meter ID, an HTTP 204 No Content status is returned.
     * </p>
     *
     * @param meterId the unique identifier of the meter
     * @return a {@link ResponseEntity} containing the latest {@link MeterReading} object or appropriate HTTP status
     */
    @Operation(summary = "Get Latest Meter Reading by Meter ID", description = "Retrieves the latest meter reading for a specific meter ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Latest meter reading retrieved.",
            		content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MeterReading.class)
                        )),
            @ApiResponse(responseCode = "204", description = "No meter readings found for the specified meter ID.",
                    content = @Content)
    })
    @GetMapping("/meter/{meterId}/latest")
    public ResponseEntity<MeterReading> getLatestMeterReadingByMeterId(
            @Parameter(description = "Unique identifier of the meter", required = true)
            @PathVariable String meterId) {
        MeterReading reading = service.getLatestMeterReadingByMeterId(meterId);
        if (reading == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(reading);
    }
}
