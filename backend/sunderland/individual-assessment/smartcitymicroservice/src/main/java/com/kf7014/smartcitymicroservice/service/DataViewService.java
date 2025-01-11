package com.kf7014.smartcitymicroservice.service;

import com.kf7014.smartcitymicroservice.dto.AggregatedDataByProviderDTO;
import com.kf7014.smartcitymicroservice.dto.AggregatedDataForCityDTO;
import com.kf7014.smartcitymicroservice.dto.MonthlyAverageDTO;
import com.kf7014.smartcitymicroservice.model.MeterReading;
import com.kf7014.smartcitymicroservice.repository.MeterReadingRepository;
import com.kf7014.smartcitymicroservice.util.TimeRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service responsible for providing data views based on raw meter readings.
 */
@Service
public class DataViewService {

    private final MeterReadingRepository meterReadingRepository;
    private static final Logger logger = LoggerFactory.getLogger(DataViewService.class);

    /**
     * Constructor for DataViewService.
     *
     * @param meterReadingRepository the repository for MeterReading entities
     */
    public DataViewService(MeterReadingRepository meterReadingRepository) {
        this.meterReadingRepository = meterReadingRepository;
    }

    /**
     * Retrieves aggregated consumption data for each provider within a specified time range.
     *
     * @param timeRange the predefined time range
     * @param startTime the start time (not used, included for consistency)
     * @param endTime   the end time (not used, included for consistency)
     * @return a list of {@link AggregatedDataByProviderDTO} representing aggregated data per provider
     */
    public List<AggregatedDataByProviderDTO> getAggregatedDataByProvider(TimeRange timeRange, Date startTime, Date endTime) {
        // Resolve the time range based on the predefined range
        Date[] resolvedTimes = resolveTimeRange(timeRange);
        Date resolvedStartTime = resolvedTimes[0];
        Date resolvedEndTime = resolvedTimes[1];

        logger.info("Fetching meter readings for providers between {} and {}", resolvedStartTime, resolvedEndTime);

        // Fetch all meter readings within the resolved time range
        List<MeterReading> readings = meterReadingRepository.findByTimestampBetween(resolvedStartTime, resolvedEndTime);

        logger.info("Fetched {} meter readings for providers", readings.size());

        // Group readings by provider ID
        Map<String, List<MeterReading>> readingsByProvider = readings.stream()
                .collect(Collectors.groupingBy(MeterReading::getProviderId));

        List<AggregatedDataByProviderDTO> aggregatedData = new ArrayList<>();

        // Aggregate total and average consumption for each provider
        for (Map.Entry<String, List<MeterReading>> entry : readingsByProvider.entrySet()) {
            String providerId = entry.getKey();
            List<MeterReading> providerReadings = entry.getValue();

            double totalConsumption = providerReadings.stream()
                    .mapToDouble(MeterReading::getReading)
                    .sum();

            double averageConsumption = providerReadings.stream()
                    .mapToDouble(MeterReading::getReading)
                    .average()
                    .orElse(0.0);

            aggregatedData.add(new AggregatedDataByProviderDTO(providerId, totalConsumption, averageConsumption));
        }

        logger.info("Aggregated data for {} providers", aggregatedData.size());

        return aggregatedData;
    }

    /**
     * Retrieves aggregated consumption data for the entire city within a specified time range.
     *
     * @param timeRange the predefined time range
     * @param startTime the start time (not used, included for consistency)
     * @param endTime   the end time (not used, included for consistency)
     * @return an {@link AggregatedDataForCityDTO} representing aggregated data for the city
     */
    public AggregatedDataForCityDTO getAggregatedDataForCity(TimeRange timeRange, Date startTime, Date endTime) {
        // Resolve the time range based on the predefined range
        Date[] resolvedTimes = resolveTimeRange(timeRange);
        Date resolvedStartTime = resolvedTimes[0];
        Date resolvedEndTime = resolvedTimes[1];

        logger.info("Fetching meter readings for city between {} and {}", resolvedStartTime, resolvedEndTime);

        // Fetch all meter readings within the resolved time range
        List<MeterReading> readings = meterReadingRepository.findByTimestampBetween(resolvedStartTime, resolvedEndTime);

        logger.info("Fetched {} meter readings for city", readings.size());

        // Aggregate total and average consumption for the city
        double totalConsumption = readings.stream()
                .mapToDouble(MeterReading::getReading)
                .sum();

        double averageConsumption = readings.stream()
                .mapToDouble(MeterReading::getReading)
                .average()
                .orElse(0.0);

        logger.info("Aggregated city data: totalConsumption={}, averageConsumption={}", totalConsumption, averageConsumption);

        return new AggregatedDataForCityDTO(totalConsumption, averageConsumption);
    }

    /**
     * Provides monthly average consumption data for each provider for a specified year.
     *
     * @param year the year for which to retrieve monthly averages
     * @return a list of {@link MonthlyAverageDTO} representing monthly averages per provider
     */
   
     public List<MonthlyAverageDTO> getMonthlyAveragePerProvider(int year) {
        Date startTime = getStartOfYear(year);
        Date endTime = getEndOfYear(year);
    
        logger.info("Fetching meter readings for providers for year {} between {} and {}", year, startTime, endTime);
    
        List<MeterReading> readings = meterReadingRepository.findByTimestampBetween(startTime, endTime);
    
        logger.info("Fetched {} meter readings for providers in year {}", readings.size(), year);
        
        // Check if no readings are fetched
        if (readings.isEmpty()) {
            logger.info("No meter readings found for city in year {}. Returning empty list.", year);
            return Collections.emptyList();
        }
    
        // Group by provider ID and then by YearMonth
        Map<String, Map<YearMonth, List<MeterReading>>> groupedData = readings.stream()
                .collect(Collectors.groupingBy(
                        MeterReading::getProviderId,
                        Collectors.groupingBy(this::getYearMonth)
                ));
    
        logger.info("Grouped data into {} providers", groupedData.size());
    
        List<MonthlyAverageDTO> monthlyAverages = new ArrayList<>();
    
        // Generate a list of all months in the year
        List<YearMonth> allMonths = getAllMonthsOfYear(year);
    
        // Ensure every provider has an entry for every month
        Set<String> allProviders = groupedData.keySet();
    
        for (String providerId : allProviders) {
            Map<YearMonth, List<MeterReading>> monthData = groupedData.getOrDefault(providerId, Collections.emptyMap());
    
            for (YearMonth month : allMonths) {
                List<MeterReading> monthReadings = monthData.getOrDefault(month, Collections.emptyList());
    
                double averageConsumption = monthReadings.stream()
                        .mapToDouble(MeterReading::getReading)
                        .average()
                        .orElse(0.0);
    
                monthlyAverages.add(new MonthlyAverageDTO(providerId, month.toString(), averageConsumption));
            }
        }
    
        logger.info("Calculated {} monthly average entries for providers in year {}", monthlyAverages.size(), year);
    
        return monthlyAverages;
    }
    

    /**
     * Provides monthly average consumption data for the entire city for a specified year.
     *
     * @param year the year for which to retrieve monthly averages
     * @return a list of {@link MonthlyAverageDTO} representing monthly averages for the city
     */
    public List<MonthlyAverageDTO> getMonthlyAverageForCity(int year) {
        Date startTime = getStartOfYear(year);
        Date endTime = getEndOfYear(year);
    
        logger.info("Fetching meter readings for city for year {} between {} and {}", year, startTime, endTime);
    
        List<MeterReading> readings = meterReadingRepository.findByTimestampBetween(startTime, endTime);
    
        logger.info("Fetched {} meter readings for city in year {}", readings.size(), year);
        
        // Check if no readings are fetched
        if (readings.isEmpty()) {
            logger.info("No meter readings found for city in year {}. Returning empty list.", year);
            return Collections.emptyList();
        }
    
        // Group readings by YearMonth
        Map<YearMonth, List<MeterReading>> groupedByMonth = readings.stream()
                .collect(Collectors.groupingBy(this::getYearMonth));
    
        logger.info("Grouped data into {} months", groupedByMonth.size());
    
        List<MonthlyAverageDTO> monthlyAverages = new ArrayList<>();
    
        // Generate a list of all months in the year
        List<YearMonth> allMonths = getAllMonthsOfYear(year);
    
        // Ensure all months are represented
        for (YearMonth month : allMonths) {
            List<MeterReading> monthReadings = groupedByMonth.getOrDefault(month, Collections.emptyList());
    
            double averageConsumption = monthReadings.stream()
                    .mapToDouble(MeterReading::getReading)
                    .average()
                    .orElse(0.0);
    
            monthlyAverages.add(new MonthlyAverageDTO(null, month.toString(), averageConsumption));
        }
    
        logger.info("Calculated {} monthly average entries for city in year {}", monthlyAverages.size(), year);
    
        return monthlyAverages;
    }
    

    /**
     * Helper method to extract YearMonth from MeterReading.
     *
     * @param reading the MeterReading instance
     * @return YearMonth extracted from the reading's timestamp
     */
    private YearMonth getYearMonth(MeterReading reading) {
        return YearMonth.from(
                reading.getTimestamp().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
    }

    /**
     * Helper method to get the start of the year as a Date object.
     *
     * @param year the year for which to get the start date
     * @return Date representing January 1st of the specified year at 00:00:00
     */
    private Date getStartOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(); // Clears all fields
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Helper method to get the end of the year as a Date object.
     *
     * @param year the year for which to get the end date
     * @return Date representing December 31st of the specified year at 23:59:59
     */
    private Date getEndOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(); // Clears all fields
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        // Set time to the end of the day
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Resolves the time range based on the predefined range.
     *
     * @param timeRange the predefined time range
     * @return an array containing the start and end times as {@link Date} objects
     */
    private Date[] resolveTimeRange(TimeRange timeRange) {
        Date resolvedStartTime;
        Date resolvedEndTime = new Date(); // Current time

        Calendar calendar = Calendar.getInstance();
        switch (timeRange) {
            case LAST_24_HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, -24);
                resolvedStartTime = calendar.getTime();
                break;
            case LAST_7_DAYS:
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                resolvedStartTime = calendar.getTime();
                break;
            case LAST_30_DAYS:
            default:
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                resolvedStartTime = calendar.getTime();
                break;
        }

        logger.info("Resolved time range: start={}, end={}", resolvedStartTime, resolvedEndTime);

        return new Date[]{resolvedStartTime, resolvedEndTime};
    }

    private List<YearMonth> getAllMonthsOfYear(int year) {
        List<YearMonth> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            months.add(YearMonth.of(year, month));
        }
        return months;
    }
    
}
