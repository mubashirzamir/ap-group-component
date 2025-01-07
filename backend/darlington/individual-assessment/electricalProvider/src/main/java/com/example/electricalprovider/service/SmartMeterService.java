package com.example.electricalprovider.service;

import com.example.electricalprovider.client.CitizenClient;
import com.example.electricalprovider.dto.ProviderSmartMeterSummary;
import com.example.electricalprovider.dto.UserSmartMeterReport;
import com.example.electricalprovider.models.UserModel;
import com.example.electricalprovider.models.smartMeterModel;
import com.example.electricalprovider.repository.SmartMeterRepository;
import com.example.electricalprovider.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service class responsible for handling smart meter data.
 */
@Service
public class SmartMeterService {

    private final SmartMeterRepository smartMeterRepository;

    private final UserRepository UserRepository;

    private final CitizenClient citizenClient;


    /**
     * Constructor for SmartMeterService.
     *
     * @param smartMeterRepository the repository to handle smart meter data
     * @param citizenClient the client to fetch data from the citizen microservice
     */
    public SmartMeterService(SmartMeterRepository smartMeterRepository, CitizenClient citizenClient,UserRepository UserRepository) {
        this.smartMeterRepository = smartMeterRepository;
        this.citizenClient = citizenClient;
        this.UserRepository = UserRepository;
    }


    /**
     * Calls Citizen Microservice to register a smart meter for the given user.
     *
     * @param providerId the ID of the provider
     * @param userId the ID of the user
     * @return the registration result
     * @throws IllegalArgumentException if an error occurs during registration
     */
    public String postRegistrationSmartMeter(String providerId, String userId) {
        try {
            return citizenClient.registerSmartMeter(providerId, userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while registering smart meter: " + e.getMessage(), e);
        }
    }



    /**
     * Summarizes smart meter data for a specific user.
     *
     * @param providerId the ID of the provider
     * @param customerId the ID of the customer
     * @return the report containing the summarized data
     * @throws IllegalArgumentException if an error occurs while fetching the data
     */
    public UserSmartMeterReport summarySmartMeterDataByUser(String providerId, String customerId) {
        validateObjectId(providerId, "Provider ID");
        validateObjectId(customerId, "Customer ID");

        try {
            // Fetch all smart meter readings for the given customer and provider
            List<smartMeterModel> smartMeterData = smartMeterRepository.findAllByCustomerIdAndProviderIdOrderByReadingTimestampDesc(customerId, providerId)
                    .orElseThrow(() -> new IllegalArgumentException("No smart meter data found for the given user."));
            System.out.println("Smart meter data: " + smartMeterData);
            // Get the last entry of this month and last month
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfThisMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);

            Double lastMonthLastReading = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().isBefore(startOfThisMonth) &&
                            data.getReadingTimestamp().isAfter(startOfLastMonth))
                    .max(Comparator.comparing(smartMeterModel::getReadingTimestamp)) // Find the latest by timestamp
                    .map(smartMeterModel::getCurrentConsumption) // Get the consumption value
                    .orElse(0.0);

            Double thisMonthLastReading = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().isAfter(startOfThisMonth))
                    .max(Comparator.comparing(smartMeterModel::getReadingTimestamp)) // Find the latest by timestamp
                    .map(smartMeterModel::getCurrentConsumption) // Get the consumption value
                    .orElse(0.0);

            // Calculate total monthly consumption
            Double totalMonthlyConsumption = thisMonthLastReading - lastMonthLastReading;

            // Calculate number of days in the current month with readings
            long daysInMonth = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().getMonth().equals(now.getMonth()))
                    .map(smartMeterModel::getReadingTimestamp)
                    .map(LocalDateTime::toLocalDate)
                    .distinct()
                    .count();

            // Calculate daily average consumption
            Double dailyAverageConsumption = daysInMonth > 0 ? totalMonthlyConsumption / daysInMonth : 0.0;

            // Calculate peak hourly consumption
            Double peakHourlyConsumption = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().getMonth().equals(now.getMonth()))
                    .mapToDouble(smartMeterModel::getCurrentConsumption)
                    .max()
                    .orElse(0.0);

            // Count the number of readings recorded this month
            Integer numberOfReadingsRecorded = (int) smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().getMonth().equals(now.getMonth()))
                    .count();

            // Check if there were any manual entries this month
            boolean hasManualEntry = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().getMonth().equals(now.getMonth()))
                    .anyMatch(data -> !data.isAutomatedEntryMethod());

            // Build the UserSmartMeterReport
            UserSmartMeterReport report = new UserSmartMeterReport();
            report.setCustomerId(customerId);
            report.setTotalMonthlyConsumption(totalMonthlyConsumption);
            report.setDailyAverageConsumption(dailyAverageConsumption);
            report.setPeakHourlyConsumption(peakHourlyConsumption);
            report.setNumberOfReadingsRecorded(numberOfReadingsRecorded);
            report.setHasManualEntry(hasManualEntry);

            return report;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while fetching smart meter data: " + e.getMessage(), e);
        }
    }


    /**
     * Validates if the given ID is a valid MongoDB ObjectId.
     *
     * @param id the ID to validate
     * @param fieldName the name of the field for error reporting
     * @throws IllegalArgumentException if the ID is not a valid ObjectId
     */
    private void validateObjectId(String id, String fieldName) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Invalid ObjectId for " + fieldName + ": " + id);
        }
    }

    //Used in Asynchronous relationship to get data from citizen
    /**
     * Calls Citizen Microservice to fetch smart meter data and saves it to the repository.
     */
    public void processSmartMeterData(smartMeterModel smartMeterData) {
        try {
            // Generate a new unique ID
            smartMeterData.setId(UUID.randomUUID().toString());
            String customerId =smartMeterData.getCustomerId();
            smartMeterRepository.findTopByCustomerIdOrderByCurrentConsumptionDesc(customerId)
                    .ifPresentOrElse(
                            lastReading -> {
                                // Ensure the new value is greater than the last reading
                                if (smartMeterData.getCurrentConsumption() > lastReading.getCurrentConsumption()) {
                                    smartMeterData.setCurrentConsumption(smartMeterData.getCurrentConsumption());
                                } else {
                                    throw new IllegalArgumentException(
                                            "New currentConsumption value must be greater than the last recorded value. " +
                                                    "Last recorded: " + lastReading.getCurrentConsumption() +
                                                    ", Provided: " + smartMeterData.getCurrentConsumption());
                                }
                            },
                            // Ignore if the database is empty
                            () -> {
                                // Optional: Log a message or initialize default values
                                smartMeterData.setCurrentConsumption(0.0);
                            }
                    );
            // Save as a new document
            smartMeterRepository.save(smartMeterData);

            System.out.println("Smart meter data saved successfully: " + smartMeterData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while processing smart meter data: " + e.getMessage(), e);
        }
    }

    public void generateTestData() {
        List<smartMeterModel> testData = new ArrayList<>();
        List<UserModel> userData = new ArrayList<>();
        Random random = new Random();
        String[] providerIds = {"507f1f77bcf86cd799439010", "507f1f77bcf86cd799439011", "507f1f77bcf86cd799439012"};
        int customersPerProvider = 100;
        int months = 6;
        int dataPerMonth = 100;

        for (String providerId : providerIds) {
            for (int customerIndex = 1; customerIndex <= customersPerProvider; customerIndex++) {
                String customerId = "customer" + customerIndex;
                // Create and save UserModel
                UserModel user = new UserModel();
                user.setId(UUID.randomUUID().toString());
                user.setProviderId(providerId);
                user.setDatabaseNo(random.nextInt(100)); // Example database number
                user.setName("Customer " + customerIndex);
                user.setEmail("customer" + customerIndex + "@example.com");
                user.setPhone("123-456-789" + customerIndex);
                user.setCity("City" + customerIndex);
                user.setState("State" + customerIndex);
                user.setCountry("Country" + customerIndex);
                user.setPostalCode("PostalCode" + customerIndex);
                user.setAddress("Address" + customerIndex);
                userData.add(user);

                for (int month = 0; month < months; month++) {
                    LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(month).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
                    LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
                    for (int i = 0; i < dataPerMonth / (customersPerProvider * months); i++) {
                        smartMeterModel dataEntry = new smartMeterModel();
                        dataEntry.setId(UUID.randomUUID().toString());
                        dataEntry.setProviderId(providerId);
                        dataEntry.setCustomerId(customerId);
                        dataEntry.setCurrentConsumption(50 + (150 * random.nextDouble())); // Random consumption between 50 and 200 kWh
                        dataEntry.setReadingTimestamp(randomDateTimeBetween(startOfMonth, endOfMonth, random));
                        dataEntry.setAutomatedEntryMethod(random.nextBoolean());
                        dataEntry.setAlertFlag(random.nextBoolean());
                        testData.add(dataEntry);
                    }
                }
            }
        }

        // Save the test data
        smartMeterRepository.saveAll(testData);
        UserRepository.saveAll(userData);
    }

    private LocalDateTime randomDateTimeBetween(LocalDateTime start, LocalDateTime end, Random random) {
        long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(ZoneOffset.UTC);
        long randomEpoch = startEpoch + (long) (random.nextDouble() * (endEpoch - startEpoch));
        return LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneOffset.UTC);
    }

    /**
     * Summarizes smart meter data for a specific provider.
     *
     * @param providerId the ID of the provider
     * @return the summary containing the summarized data
     * @throws IllegalArgumentException if an error occurs while fetching the data
     */
    public ProviderSmartMeterSummary summarySmartMeterDataByProvider(String providerId) {
        validateObjectId(providerId, "Provider ID");

        try {
            // Fetch all smart meter readings for the given provider in descending order
            List<smartMeterModel> smartMeterData = smartMeterRepository.findAllByProviderIdOrderByReadingTimestampDesc(providerId)
                    .orElse(null); // Return null if no data is found

            // If no data is available, return null to indicate no response
            if (smartMeterData == null || smartMeterData.isEmpty()) {
                System.out.println("No smart meter data found for the given provider ID: " + providerId);
                return null;
            }
            LocalDateTime now = LocalDateTime.now();

            // Daily Average Consumption
            Map<LocalDate, Double> highestReadingPerDay = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().getMonth().equals(now.getMonth()))
                    .collect(Collectors.toMap(
                            data -> data.getReadingTimestamp().toLocalDate(),
                            smartMeterModel::getCurrentConsumption,
                            (existing, replacement) -> existing // Keep the first (highest) entry
                    ));

            Double totalHighestReadings = highestReadingPerDay.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            long daysWithReadings = highestReadingPerDay.size();

            Double dailyAverageConsumption = daysWithReadings > 0 ? totalHighestReadings / daysWithReadings : 0.0;

            // Peak Demand
            Double peakHourlyConsumption = IntStream.range(0, smartMeterData.size() - 1)
                    .mapToDouble(i -> smartMeterData.get(i).getCurrentConsumption() - smartMeterData.get(i + 1).getCurrentConsumption())
                    .max()
                    .orElse(0.0);

            // Calculate total monthly consumption
            Double totalMonthlyConsumption = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null &&
                            data.getReadingTimestamp().getMonth().equals(now.getMonth()))
                    .mapToDouble(smartMeterModel::getCurrentConsumption)
                    .sum();

            // Count distinct citizens
            long citizenCount = smartMeterData.stream()
                    .map(smartMeterModel::getCustomerId)
                    .distinct()
                    .count();

            // Average consumption per citizen
            Double averageConsumptionPerCitizen = citizenCount > 0 ? totalMonthlyConsumption / citizenCount : 0.0;

            // Build the summary
            ProviderSmartMeterSummary summary = new ProviderSmartMeterSummary();
            summary.setProviderId(providerId);
            summary.setTotalMonthlyConsumption(totalMonthlyConsumption);
            summary.setDailyAverageConsumption(dailyAverageConsumption);
            summary.setAverageConsumptionPerCitizen(averageConsumptionPerCitizen);
            summary.setPeakHourlyConsumption(peakHourlyConsumption);
            summary.setCitizenCount((int) citizenCount);
            summary.setDate(LocalDateTime.now());

            return summary;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while fetching smart meter data: " + e.getMessage(), e);
        }
    }

    //Add-ons for ease of access

    public List<ProviderSmartMeterSummary> summaryAllSmartMeterDataByProvider(String providerId) {
        validateObjectId(providerId, "Provider ID");
        try {
            // Fetch all smart meter readings for the given provider in descending order
            List<smartMeterModel> smartMeterData = smartMeterRepository.findAllByProviderIdOrderByReadingTimestampDesc(providerId)
                    .orElse(null); // Return null if no data is found

            // If no data is available, return an empty list
            if (smartMeterData == null || smartMeterData.isEmpty()) {
                System.out.println("No smart meter data found for the given provider ID: " + providerId);
                return Collections.emptyList();
            }

            // Group data by month
            Map<YearMonth, List<smartMeterModel>> dataByMonth = smartMeterData.stream()
                    .filter(data -> data.getReadingTimestamp() != null)
                    .collect(Collectors.groupingBy(data -> YearMonth.from(data.getReadingTimestamp())));

            List<ProviderSmartMeterSummary> summaries = new ArrayList<>();

            // Calculate summary for each month
            for (Map.Entry<YearMonth, List<smartMeterModel>> entry : dataByMonth.entrySet()) {
                YearMonth month = entry.getKey();
                List<smartMeterModel> monthlyData = entry.getValue();

                // Daily Average Consumption
                Map<LocalDate, Double> highestReadingPerDay = monthlyData.stream()
                        .collect(Collectors.toMap(
                                data -> data.getReadingTimestamp().toLocalDate(),
                                smartMeterModel::getCurrentConsumption,
                                (existing, replacement) -> existing // Keep the first (highest) entry
                        ));

                Double totalHighestReadings = highestReadingPerDay.values().stream()
                        .mapToDouble(Double::doubleValue)
                        .sum();

                long daysWithReadings = highestReadingPerDay.size();

                Double dailyAverageConsumption = daysWithReadings > 0 ? totalHighestReadings / daysWithReadings : 0.0;

                // Peak Demand
                Double peakHourlyConsumption = IntStream.range(0, monthlyData.size() - 1)
                        .mapToDouble(i -> monthlyData.get(i).getCurrentConsumption() - monthlyData.get(i + 1).getCurrentConsumption())
                        .max()
                        .orElse(0.0);

                // Calculate total monthly consumption
                Double totalMonthlyConsumption = monthlyData.stream()
                        .mapToDouble(smartMeterModel::getCurrentConsumption)
                        .sum();

                // Count distinct citizens
                long citizenCount = monthlyData.stream()
                        .map(smartMeterModel::getCustomerId)
                        .distinct()
                        .count();

                // Average consumption per citizen
                Double averageConsumptionPerCitizen = citizenCount > 0 ? totalMonthlyConsumption / citizenCount : 0.0;

                // Build the summary
                ProviderSmartMeterSummary summary = new ProviderSmartMeterSummary();
                summary.setProviderId(providerId);
                summary.setTotalMonthlyConsumption(totalMonthlyConsumption);
                summary.setDailyAverageConsumption(dailyAverageConsumption);
                summary.setAverageConsumptionPerCitizen(averageConsumptionPerCitizen);
                summary.setPeakHourlyConsumption(peakHourlyConsumption);
                summary.setCitizenCount((int) citizenCount);
                summary.setDate(month.atEndOfMonth().atStartOfDay());

                summaries.add(summary);
            }

            return summaries;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while fetching smart meter data: " + e.getMessage(), e);
        }
    }
}
