package com.customer;

import com.customer.report.CustomerReportGenerator;

public class CustomerReportApplication {

    public static void main(String[] args) {
        new CustomerReportGenerator().generateReport();
    }

}