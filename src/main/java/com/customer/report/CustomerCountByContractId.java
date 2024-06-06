package com.customer.report;

import java.util.Map;

/**
 * Generates The number of unique customerId for each contractId.
 */
public class CustomerCountByContractId implements Report {

    @Override
    public void generate(Map<Object, Object> uniqueCustomerIdsCountByContractId) {
        System.out.println("\n\nThe number of unique customerId for each contractId");
        System.out.println(TABLE_BORDER);
        System.out.printf(FORMAT, "CONTRACT_ID", COLUMN_SEPARATOR, "UNIQUE_CUSTOMER_IDS_COUNT");
        System.out.println(TABLE_BORDER);
        uniqueCustomerIdsCountByContractId.forEach((key, value) -> System.out.printf(FORMAT, key, COLUMN_SEPARATOR, value));
        System.out.println(TABLE_BORDER);
    }

}
