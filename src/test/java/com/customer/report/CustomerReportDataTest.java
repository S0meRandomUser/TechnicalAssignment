package com.customer.report;

import com.customer.extractor.CustomerDataExtractor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class CustomerReportDataTest {

    @Test
    void customerReportData_InvalidData_catchesInvalidDataException() {
        System.setIn(new ByteArrayInputStream("1,2".getBytes()));
        assertDoesNotThrow(CustomerReportData::new);
    }

    @Test
    void customerReportData_validData_catchesUnidentifiedException() {
        var mockedExtractor = mock(CustomerDataExtractor.class);
        when(mockedExtractor.extractCustomerCountByContractId(any(), any())).thenThrow(RuntimeException.class);
        assertDoesNotThrow(CustomerReportData::new);
    }

}
