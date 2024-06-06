package com.customer.report;

import java.util.Map;

/**
 * Generates The number of unique customerId for each geozone.
 */
public class CustomerCountByGeozone implements Report {

    private static final String CUSTOMER_COUNT_BY_GEOZONE_TITLE = "\n\nThe number of unique customerId for each geozone";

    @Override
    public void generate(Map<Object, Object> customerCountByGeozone) {
        System.out.println(CUSTOMER_COUNT_BY_GEOZONE_TITLE);
        System.out.println(TABLE_BORDER);
        System.out.printf(FORMAT, "CONTRACT_ID", COLUMN_SEPARATOR, "UNIQUE_CUSTOMER_IDS_COUNT");
        System.out.println(TABLE_BORDER);
        customerCountByGeozone.forEach((key, value) -> System.out.printf(FORMAT, key, COLUMN_SEPARATOR, value));
        System.out.println(TABLE_BORDER);
    }
}
