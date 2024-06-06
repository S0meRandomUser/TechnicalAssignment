package com.customer.report;

import java.util.Collections;

public class CustomerReportGenerator {

    /**
     * Generates the following reports:
     * <ul>
     *   <li>The number of unique customerId for each contractId.</li>
     *   <li>The number of unique customerId for each geozone.</li>
     *   <li>The average buildduration for each geozone.</li>
     *   <li>The list of unique customerId for each geozone.</li>
     * </ul>
     */
    public void generateReport() {
        CustomerReportData customerReportData = new CustomerReportData();

        new CustomerCountByContractId().generate(Collections.unmodifiableMap(customerReportData.getCustomerCountByContractId()));
        new CustomerCountByGeozone().generate(Collections.unmodifiableMap(customerReportData.getCustomerCountByGeozone()));
        new AvgBuildDurationByGeozone().generate(Collections.unmodifiableMap(customerReportData.getAvgBuildDurationByGeozone()));
        new CustomerListByGeozone().generate(Collections.unmodifiableMap(customerReportData.getCustomerListByGeozone()));
    }
}
