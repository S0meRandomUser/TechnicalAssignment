package com.customer.extractor;

import com.customer.exception.InvalidDataException;

import java.time.Duration;
import java.util.*;

public class CustomerDataExtractor {

    private CustomerDataExtractor() {
    }

    public static CustomerDataExtractor createCustomerDataExtractor() {
        return new CustomerDataExtractor();
    }

    /**
     * Extract the customer data from input string.
     *
     * @return All lines extracted from the input string as a list of strings.
     */
    public List<List<String>> scanLines() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<List<String>> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                var data = Arrays.stream(line.split(","))
                        .map(String::trim)
                        .toArray(String[]::new);
                if (data.length != 6) {
                    throw new InvalidDataException("Invalid input string!");
                } else if (!data[5].matches("\\d+s")) {
                    throw new InvalidDataException("Build duration format incorrect!");
                }
                lines.add(new ArrayList<>(List.of(data)));
            }

            return lines;
        }
    }

    /**
     * Extracts all unique ContractIds from input string.
     *
     * @param lines Lines extracted from input string.
     * @return Unique ContractIds.
     */
    public List<String> extractUniqueContractIds(List<List<String>> lines) {
        return lines.stream()
                .map(line -> line.get(1))
                .distinct()
                .toList();
    }

    /**
     * Extracts all unique Geozones from input string.
     *
     * @param lines Lines extracted from input string.
     * @return Unique Geozones.
     */
    public List<String> extractUniqueGeozones(List<List<String>> lines) {
        return lines.stream()
                .map(line -> line.get(2))
                .distinct()
                .toList();
    }

    /**
     * Creates a map with keys as ContractIds and Values as unique CustomerId count.
     *
     * @param lines             Lines extracted from input string.
     * @param uniqueContractIds List of unique ContractIds.
     * @return A Map where Key = ContractId, Value = unique CustomerId count.
     */
    public Map<String, Long> extractCustomerCountByContractId(List<List<String>> lines, List<String> uniqueContractIds) {
        Map<String, Long> contractIdMap = new HashMap<>();
        uniqueContractIds.forEach(contractId -> {
            var uniqueCustomerIdCount = lines.stream()
                    .filter(line -> line.get(1).equals(contractId))
                    .map(line -> line.get(0))
                    .distinct()
                    .count();
            contractIdMap.put(contractId, uniqueCustomerIdCount);
        });
        return contractIdMap;
    }

    /**
     * Creates a map with keys as Geozones and Values as unique CustomerId count.
     *
     * @param lines          Lines extracted from input string.
     * @param uniqueGeozones List of unique Geozones.
     * @return A Map where Key = Geozone, Value = unique CustomerId count.
     */
    public Map<String, Long> extractCustomerCountByGeozone(List<List<String>> lines, List<String> uniqueGeozones) {
        Map<String, Long> geozoneMap = new HashMap<>();
        uniqueGeozones.forEach(geozone -> {
            var uniqueCustomerIdCount = lines.stream()
                    .filter(line -> line.get(2).equals(geozone))
                    .map(line -> line.get(0))
                    .distinct()
                    .count();
            geozoneMap.put(geozone, uniqueCustomerIdCount);
        });
        return geozoneMap;
    }

    /**
     * Creates a map with keys as Geozones and Values as average build duration.
     *
     * @param lines          Lines extracted from input string.
     * @param uniqueGeozones List of unique Geozones.
     * @return A Map where Key = Geozone, Value = average build duration.
     */
    public Map<String, Double> extractAvgBuildDurationByGeozone(List<List<String>> lines, List<String> uniqueGeozones) {
        Map<String, Double> geozoneMap = new HashMap<>();
        uniqueGeozones.forEach(geozone -> {
            var avgBuildDuration = lines.stream()
                    .filter(line -> line.get(2).equals(geozone))
                    .mapToLong(line -> Duration.parse("PT" + line.get(5)).getSeconds())
                    .average()
                    .orElse(0.0);
            geozoneMap.put(geozone, avgBuildDuration);
        });
        return geozoneMap;
    }

    /**
     * Creates a map with keys as Geozones and Values as list of unique CustomerIds.
     *
     * @param lines          Lines extracted from input string.
     * @param uniqueGeozones List of unique Geozones.
     * @return A Map where Key = Geozone, Value = list of unique CustomerIds.
     */
    public Map<String, List<String>> extractCustomerListByGeozone(List<List<String>> lines, List<String> uniqueGeozones) {
        Map<String, List<String>> geozoneMap = new HashMap<>();
        uniqueGeozones.forEach(geozone -> {
            var uniqueCustomerIds = lines.stream()
                    .filter(line -> line.get(2).equals(geozone))
                    .map(line -> line.get(0))
                    .distinct()
                    .toList();
            geozoneMap.put(geozone, uniqueCustomerIds);
        });
        return geozoneMap;
    }

}
