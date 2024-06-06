package com.customer.report;

import java.util.Map;

public interface Report {
    String TABLE_BORDER = "-----------------------------------------------------------";
    String FORMAT = "%15s %10s %25s %n";
    String COLUMN_SEPARATOR = "|";

    void generate(Map<Object, Object> reportData);

}
