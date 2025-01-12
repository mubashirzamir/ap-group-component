package com.sedm.app.service;

import com.sedm.app.dto.MonthlyConsumptionDTO;
import com.sedm.app.dto.ProviderAggregationDTO;
import com.sedm.app.entities.PowerConsumptionSC;
import com.sedm.app.entities.ReadingSCEntity;
import com.sedm.app.repository.ReadingSCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReadingAggregationService {

    @Autowired
    private ReadingSCRepository readingSCRepository;

    private String extractYearMonth(String dateString) {
        // Defensive check
        if (dateString == null || dateString.length() < 7) {
            return "UNKNOWN";
        }
        // "yyyy-MM"
        return dateString.substring(0, 7);
    }


    /**
     * Aggregation by provider:
     *   - totalCount = total number of PowerConsumptionSC items
     *   - sum = sum of all powerConsumption fields
     *   - average = sum / totalCount
     */
    public List<ProviderAggregationDTO> getAggregationsByProvider() {
        // 1. Read everything from DB
        List<ReadingSCEntity> allReadings = readingSCRepository.findAll();

        // 2. Map of providerName -> [list of powerConsumption values]
        Map<String, List<Long>> groupedByProvider = new HashMap<>();

        // 3. Populate the map
        for (ReadingSCEntity reading : allReadings) {
            String providerName = reading.getProviderName();

            // Initialize if missing
            groupedByProvider.computeIfAbsent(providerName, k -> new ArrayList<>());

            // Add each reading's powerConsumption to the list
            for (PowerConsumptionSC pcs : reading.getPowerConsumptions()) {
                groupedByProvider.get(providerName).add(pcs.getPowerConsumption());
            }
        }

        // 4. Build the final list of ProviderAggregationDTO
        List<ProviderAggregationDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<Long>> entry : groupedByProvider.entrySet()) {
            String providerName = entry.getKey();
            List<Long> consumptionList = entry.getValue();

            long sum = 0;
            for (Long c : consumptionList) {
                if (c != null) {
                    sum += c;
                }
            }

            long count = consumptionList.size();
            double average = (count == 0) ? 0 : (double) sum / count;

            ProviderAggregationDTO dto = new ProviderAggregationDTO();
            dto.setProviderName(providerName);
            dto.setSum(sum);
            dto.setTotalCount(count);
            dto.setAverage(average);

            result.add(dto);
        }
        return result;
    }

    /**
     * Aggregation for the entire dataset (all providers combined):
     *   - totalCount
     *   - sum
     *   - average
     */
    public ProviderAggregationDTO getTotalAggregation() {
        List<ReadingSCEntity> allReadings = readingSCRepository.findAll();

        long sum = 0;
        long count = 0;

        for (ReadingSCEntity reading : allReadings) {
            for (PowerConsumptionSC pcs : reading.getPowerConsumptions()) {
                if (pcs.getPowerConsumption() != null) {
                    sum += pcs.getPowerConsumption();
                    count++;
                }
            }
        }
        double average = (count == 0) ? 0 : (double) sum / count;

        ProviderAggregationDTO dto = new ProviderAggregationDTO();
        dto.setProviderName("ALL_PROVIDERS");  // or just null
        dto.setSum(sum);
        dto.setTotalCount(count);
        dto.setAverage(average);

        return dto;
    }

    // 1) Monthly consumption for a specific provider
    public List<MonthlyConsumptionDTO> getMonthlyConsumptionByProvider(String providerName) {
        // Fetch all readings from DB
        // (Optionally, implement findByProviderName(...) in ReadingSCRepository for efficiency)
        List<ReadingSCEntity> allReadings = readingSCRepository.findAll();

        // Filter by provider
        List<ReadingSCEntity> providerReadings = new ArrayList<>();
        for (ReadingSCEntity reading : allReadings) {
            if (providerName.equalsIgnoreCase(reading.getProviderName())) {
                providerReadings.add(reading);
            }
        }

        // Map of "yyyy-MM" -> total consumption
        Map<String, Long> monthlySum = new HashMap<>();

        for (ReadingSCEntity reading : providerReadings) {
            if (reading.getPowerConsumptions() != null) {
                for (PowerConsumptionSC pcs : reading.getPowerConsumptions()) {
                    // Use fromDate if present, else readingDate
                    String dateStr = pcs.getFromDate() != null ? pcs.getFromDate() : pcs.getReadingDate();
                    String yearMonth = extractYearMonth(dateStr);
                    long current = monthlySum.getOrDefault(yearMonth, 0L);
                    monthlySum.put(yearMonth, current + pcs.getPowerConsumption());
                }
            }
        }

        // Convert map to list of DTO
        List<MonthlyConsumptionDTO> results = new ArrayList<>();
        for (Map.Entry<String, Long> entry : monthlySum.entrySet()) {
            MonthlyConsumptionDTO dto = new MonthlyConsumptionDTO();
            dto.setMonth(entry.getKey());
            dto.setTotalConsumption(entry.getValue());
            results.add(dto);
        }

        // Sort by month, if desired (lexicographically works since it's yyyy-MM)
        results.sort(Comparator.comparing(MonthlyConsumptionDTO::getMonth));
        return results;
    }

    // 2) Monthly consumption for the entire city (all providers)
    public List<MonthlyConsumptionDTO> getMonthlyConsumptionCityWide() {
        List<ReadingSCEntity> allReadings = readingSCRepository.findAll();

        // Map of "yyyy-MM" -> total consumption
        Map<String, Long> monthlySum = new HashMap<>();

        for (ReadingSCEntity reading : allReadings) {
            if (reading.getPowerConsumptions() != null) {
                for (PowerConsumptionSC pcs : reading.getPowerConsumptions()) {
                    String dateStr = pcs.getFromDate() != null ? pcs.getFromDate() : pcs.getReadingDate();
                    String yearMonth = extractYearMonth(dateStr);
                    long current = monthlySum.getOrDefault(yearMonth, 0L);
                    if (pcs.getPowerConsumption() != null) {
                        monthlySum.put(yearMonth, current + pcs.getPowerConsumption());
                    }

                }
            }
        }

        List<MonthlyConsumptionDTO> results = new ArrayList<>();
        for (Map.Entry<String, Long> entry : monthlySum.entrySet()) {
            MonthlyConsumptionDTO dto = new MonthlyConsumptionDTO();
            dto.setMonth(entry.getKey());
            dto.setTotalConsumption(entry.getValue());
            results.add(dto);
        }

        results.sort(Comparator.comparing(MonthlyConsumptionDTO::getMonth));
        return results;
    }

    public Map<String, Map<String, Long>> getMonthlyConsumptionForAllProviders() {
        // 1) Read all from DB
        List<ReadingSCEntity> allReadings = readingSCRepository.findAll();

        // 2) Build a nested map: providerName -> (month -> sumConsumption)
        Map<String, Map<String, Long>> providerMonthMap = new HashMap<>();

        for (ReadingSCEntity reading : allReadings) {
            String providerName = reading.getProviderName();
            if (reading.getPowerConsumptions() == null || providerName == null) {
                continue;
            }

            for (PowerConsumptionSC pcs : reading.getPowerConsumptions()) {
                // If fromDate is present, use it; otherwise use readingDate
                String dateStr = pcs.getFromDate() != null
                        ? pcs.getFromDate()
                        : pcs.getReadingDate();

                // Extract "yyyy-MM" if date is "yyyy-MM-dd"
                String yearMonth = extractYearMonth(dateStr);

                // 2a) If the provider doesn't exist yet, initialize
                providerMonthMap.putIfAbsent(providerName, new HashMap<>());

                // 2b) Add consumption to that (provider, month)
                Map<String, Long> monthMap = providerMonthMap.get(providerName);
                long existing = monthMap.getOrDefault(yearMonth, 0L);
                if (pcs.getPowerConsumption() != null) {
                    monthMap.put(yearMonth, existing + pcs.getPowerConsumption());
                }
            }
        }

        // 3) Return the nested map. Jackson (by default) will convert it to JSON in the desired shape:
        //    {
        //      "ProviderA": { "2024-01": 12000, "2024-02": 9000 },
        //      "ProviderB": { "2024-01": 10500, "2024-02": 13000 }
        //    }
        return providerMonthMap;
    }

}
