package com.customer.report;

import java.util.Map;

/**
 * Generates The list of unique customerId for each geozone.
 */
public class CustomerListByGeozone implements Report {

    private static final String CUSTOMER_LIST_COUNT_BY_GEOZONE_TITLE = "\n\nThe list of unique customerId for each geozone";

    @Override
    public void generate(Map<Object, Object> customerListByGeozone) {
        System.out.println(CUSTOMER_LIST_COUNT_BY_GEOZONE_TITLE);
        System.out.println(TABLE_BORDER);
        System.out.printf(FORMAT, "GEOZONE", COLUMN_SEPARATOR, "UNIQUE_CUSTOMER_IDS_COUNT");
        System.out.println(TABLE_BORDER);
        customerListByGeozone.forEach((key, value) -> System.out.printf(FORMAT, key, COLUMN_SEPARATOR, value));
        System.out.println(TABLE_BORDER);
    }
}
