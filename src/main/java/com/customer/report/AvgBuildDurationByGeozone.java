package com.customer.report;

import java.util.Map;

/**
 * Generates The average build duration for each geozone.
 */
public class AvgBuildDurationByGeozone implements Report {

    private static final String AVG_BUILD_DURATION_BY_GEOZONE_TITLE = "\n\nThe average buildduration for each geozone";

    @Override
    public void generate(Map<Object, Object> avgBuildDurationByGeozone) {
        System.out.println(AVG_BUILD_DURATION_BY_GEOZONE_TITLE);
        System.out.println(TABLE_BORDER);
        System.out.printf(FORMAT, "GEOZONE", COLUMN_SEPARATOR, "AVG_BUILD_DURATION");
        System.out.println(TABLE_BORDER);
        avgBuildDurationByGeozone.forEach((key, value) -> System.out.printf(FORMAT, key, COLUMN_SEPARATOR, value + "s"));
        System.out.println(TABLE_BORDER);
    }
}
