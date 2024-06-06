package com.customer.report;

import com.customer.exception.InvalidDataException;

import java.util.List;
import java.util.Map;

import static com.customer.extractor.CustomerDataExtractor.createCustomerDataExtractor;

public class CustomerReportData {
    private Map<String, Long> customerCountByContractId;
    private Map<String, Long> customerCountByGeozone;
    private Map<String, Double> avgBuildDurationByGeozone;
    private Map<String, List<String>> customerListByGeozone;

    public CustomerReportData() {
        try {
            extractCustomerReportData();
        } catch (InvalidDataException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Unknown runtime error!");
            ex.printStackTrace();
        }
    }

    private void extractCustomerReportData() {
        List<List<String>> lines = createCustomerDataExtractor().scanLines();
        List<String> uniqueContractIds = createCustomerDataExtractor().extractUniqueContractIds(lines);
        List<String> uniqueGeozones = createCustomerDataExtractor().extractUniqueGeozones(lines);
        customerCountByContractId = createCustomerDataExtractor().extractCustomerCountByContractId(lines, uniqueContractIds);
        customerCountByGeozone = createCustomerDataExtractor().extractCustomerCountByGeozone(lines, uniqueGeozones);
        avgBuildDurationByGeozone = createCustomerDataExtractor().extractAvgBuildDurationByGeozone(lines, uniqueGeozones);
        customerListByGeozone = createCustomerDataExtractor().extractCustomerListByGeozone(lines, uniqueGeozones);
    }

    public Map<String, Long> getCustomerCountByContractId() {
        return customerCountByContractId;
    }

    public Map<String, Long> getCustomerCountByGeozone() {
        return customerCountByGeozone;
    }

    public Map<String, Double> getAvgBuildDurationByGeozone() {
        return avgBuildDurationByGeozone;
    }

    public Map<String, List<String>> getCustomerListByGeozone() {
        return customerListByGeozone;
    }
}
