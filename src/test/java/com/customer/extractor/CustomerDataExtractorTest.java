package com.customer.extractor;

import com.customer.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.customer.extractor.CustomerDataExtractor.createCustomerDataExtractor;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDataExtractorTest {

    private static final String VALID_TEST_INPUT = """
            2343225,2345,us_east,RedTeam,ProjectApple,3445s
            1223456,2345,us_west,BlueTeam,ProjectBanana,2211s
            3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s
            1233456,2345,us_west,BlueTeam,ProjectDate,2221s
            3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s
            """;

    @Test
    void scanLines_whenEmpty_printEmptyReport() {
        System.setIn(new ByteArrayInputStream("".getBytes()));
        assertDoesNotThrow(() -> createCustomerDataExtractor().scanLines());
    }

    @Test
    void scanLines_whenBlank_printEmptyReport() {
        System.setIn(new ByteArrayInputStream(" ".getBytes()));
        Exception thrown = assertThrows(
                InvalidDataException.class,
                () -> createCustomerDataExtractor().scanLines());
        assertEquals("Invalid input string!", thrown.getMessage());
    }

    @Test
    void scanLines_lessThanSixFieldsProvided_throwsException() {
        System.setIn(new ByteArrayInputStream("1,2".getBytes()));
        Exception thrown = assertThrows(
                InvalidDataException.class,
                () -> createCustomerDataExtractor().scanLines());
        assertEquals("Invalid input string!", thrown.getMessage());
    }

    @Test
    void scanLines_moreThanSixFieldsProvided_throwsException() {
        System.setIn(new ByteArrayInputStream("1,2,3,4,5,6,7".getBytes()));
        Exception thrown = assertThrows(
                InvalidDataException.class,
                () -> createCustomerDataExtractor().scanLines());
        assertEquals("Invalid input string!", thrown.getMessage());
    }

    @Test
    void scanLines_durationDataInvalid_throwsException() {
        System.setIn(new ByteArrayInputStream("2343225,2345,us_east,RedTeam,ProjectApple,3445".getBytes()));
        Exception thrown = assertThrows(
                InvalidDataException.class,
                () -> createCustomerDataExtractor().scanLines());
        assertEquals("Build duration format incorrect!", thrown.getMessage());
    }

    @Test
    void scanLines_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream("2343225,2345,us_east,RedTeam,ProjectApple,3445s".getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        assertEquals(lines.get(0).get(0), "2343225");
    }

    @Test
    void extractUniqueContractIds_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream(VALID_TEST_INPUT.getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        var uniqueContractIds = createCustomerDataExtractor().extractUniqueContractIds(lines);
        assertEquals(2, uniqueContractIds.size());
        assertTrue(uniqueContractIds.contains("2345"));
        assertTrue(uniqueContractIds.contains("2346"));
    }

    @Test
    void extractUniqueGeozones_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream(VALID_TEST_INPUT.getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        var uniqueGeozones = createCustomerDataExtractor().extractUniqueGeozones(lines);
        assertEquals(3, uniqueGeozones.size());
        assertTrue(uniqueGeozones.contains("us_east"));
        assertTrue(uniqueGeozones.contains("us_west"));
        assertTrue(uniqueGeozones.contains("eu_west"));
    }

    @Test
    void extractCustomerCountByContractId_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream(VALID_TEST_INPUT.getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        var uniqueContractIds = createCustomerDataExtractor().extractUniqueContractIds(lines);
        var contractIdMap = createCustomerDataExtractor().extractCustomerCountByContractId(lines, uniqueContractIds);
        assertEquals(2, contractIdMap.size());
        assertEquals(3, contractIdMap.get("2345"));
        assertEquals(2, contractIdMap.get("2346"));
    }

    @Test
    void extractCustomerCountByGeozone_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream(VALID_TEST_INPUT.getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        var uniqueGeozones = createCustomerDataExtractor().extractUniqueGeozones(lines);
        var geozoneMap = createCustomerDataExtractor().extractCustomerCountByGeozone(lines, uniqueGeozones);
        assertEquals(3, geozoneMap.size());
        assertEquals(1, geozoneMap.get("us_east"));
        assertEquals(2, geozoneMap.get("us_west"));
        assertEquals(2, geozoneMap.get("eu_west"));
    }

    @Test
    void extractAvgBuildDurationByGeozone_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream(VALID_TEST_INPUT.getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        var uniqueGeozones = createCustomerDataExtractor().extractUniqueGeozones(lines);
        var geozoneMap = createCustomerDataExtractor().extractAvgBuildDurationByGeozone(lines, uniqueGeozones);
        assertEquals(3, geozoneMap.size());
        assertEquals(3445.0, geozoneMap.get("us_east"));
        assertEquals(2216.0, geozoneMap.get("us_west"));
        assertEquals(4222.0, geozoneMap.get("eu_west"));
    }

    @Test
    void extractCustomerListByGeozone_correctDataProvided_extractsDataProperly() {
        System.setIn(new ByteArrayInputStream(VALID_TEST_INPUT.getBytes()));
        var lines = createCustomerDataExtractor().scanLines();
        var uniqueGeozones = createCustomerDataExtractor().extractUniqueGeozones(lines);
        var geozoneMap = createCustomerDataExtractor().extractCustomerListByGeozone(lines, uniqueGeozones);
        assertEquals(3, geozoneMap.size());
        assertEquals(List.of("2343225"), geozoneMap.get("us_east"));
        assertEquals(List.of("1223456", "1233456"), geozoneMap.get("us_west"));
        assertEquals(List.of("3244332", "3244132"), geozoneMap.get("eu_west"));
    }

}
