package com.kf7014.smartcitymicroservice.controller;

import com.kf7014.smartcitymicroservice.dto.AggregatedDataByProviderDTO;
import com.kf7014.smartcitymicroservice.dto.AggregatedDataForCityDTO;
import com.kf7014.smartcitymicroservice.dto.MonthlyAverageDTO;
import com.kf7014.smartcitymicroservice.service.DataViewService;
import com.kf7014.smartcitymicroservice.util.TimeRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

/**
 * REST Controller for providing data views based on raw meter readings.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DataViewController {

    private final DataViewService dataViewService;
    private static final Logger logger = LoggerFactory.getLogger(DataViewController.class);

    /**
     * Constructs a new {@code DataViewController} with the specified {@link DataViewService}.
     *
     * @param dataViewService the service responsible for data view operations
     */
    public DataViewController(DataViewService dataViewService) {
        this.dataViewService = dataViewService;
    }

    /**
     * Retrieves aggregated consumption data for each provider within a specified time range.
     *
     * @param timeRange the predefined time range (LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS). Defaults to LAST_30_DAYS if not specified.
     * @return a {@link ResponseEntity} containing a list of {@link AggregatedDataByProviderDTO} and HTTP status
     */
    @GetMapping("/data/providers")
    public ResponseEntity<?> getAggregatedDataByProvider(
            @RequestParam(required = false, defaultValue = "LAST_30_DAYS") TimeRange timeRange) {
        logger.info("Received request for aggregated data by providers with timeRange={}", timeRange);
        try {
            List<AggregatedDataByProviderDTO> data = dataViewService.getAggregatedDataByProvider(timeRange, null, null);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request parameter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred."));
        }
    }

    /**
     * Retrieves aggregated consumption data for the entire city within a specified time range.
     *
     * @param timeRange the predefined time range (LAST_24_HOURS, LAST_7_DAYS, LAST_30_DAYS). Defaults to LAST_30_DAYS if not specified.
     * @return a {@link ResponseEntity} containing {@link AggregatedDataForCityDTO} and HTTP status
     */
    @GetMapping("/data/city")
    public ResponseEntity<?> getAggregatedDataForCity(
            @RequestParam(required = false, defaultValue = "LAST_30_DAYS") TimeRange timeRange) {
        logger.info("Received request for aggregated data for city with timeRange={}", timeRange);
        try {
            AggregatedDataForCityDTO data = dataViewService.getAggregatedDataForCity(timeRange, null, null);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request parameter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred."));
        }
    }

    /**
     * Provides monthly average consumption data for each provider for a specified year.
     *
     * @param year the year for which to retrieve monthly averages. Defaults to the current year if not specified.
     * @return a {@link ResponseEntity} containing a list of {@link MonthlyAverageDTO} and HTTP status
     */
    // @CrossOrigin(origins = "*")
    @GetMapping("/graphs/monthly-average/providers")
    public ResponseEntity<?> getMonthlyAveragePerProvider(
            @RequestParam(required = false) Integer year) {
        int targetYear = (year != null) ? year : YearMonth.now().getYear();
        logger.info("Received request for monthly average per provider for year={}", targetYear);
        try {
            List<MonthlyAverageDTO> data = dataViewService.getMonthlyAveragePerProvider(targetYear);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request parameter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred."));
        }
    }

    /**
     * Provides monthly average consumption data for the entire city for a specified year.
     *
     * @param year the year for which to retrieve monthly averages. Defaults to the current year if not specified.
     * @return a {@link ResponseEntity} containing a list of {@link MonthlyAverageDTO} and HTTP status
     */
    // @CrossOrigin(origins = "*")
    @GetMapping("/graphs/monthly-average/city")
    public ResponseEntity<?> getMonthlyAverageForCity(
            @RequestParam(required = false) Integer year) {
        int targetYear = (year != null) ? year : YearMonth.now().getYear();
        logger.info("Received request for monthly average for city for year={}", targetYear);
        try {
            List<MonthlyAverageDTO> data = dataViewService.getMonthlyAverageForCity(targetYear);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request parameter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred."));
        }
    }
}
