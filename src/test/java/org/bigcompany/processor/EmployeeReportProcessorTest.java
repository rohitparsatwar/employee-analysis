package org.bigcompany.processor;

import org.bigcompany.exception.EmployeeDataParseException;
import org.bigcompany.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeReportProcessorTest {

    private EmployeeReportProcessor employeeReportProcessor;

    @BeforeEach
    void setUp() {
        employeeReportProcessor = new EmployeeReportProcessor();
    }

    @Test
    void testProcessEmployeeReport_processesValidFileSuccessfully() {
        String filePath = "src/test/resources/input.csv";
        assertDoesNotThrow(() -> employeeReportProcessor.processEmployeeReport(filePath));
    }

    @Test
    void testProcessEmployee_throwsExceptionForEmptyFile() {
        String filePath = "src/test/resources/empty.csv";
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            employeeReportProcessor.processEmployeeReport(filePath);
        });
        assertEquals("The file is empty", exception.getMessage());
    }

    @Test
    void testProcessEmployeeReport_throwsExceptionForInvalidFilePath() {
        EmployeeDataParseException employeeDataParseException = assertThrows(EmployeeDataParseException.class, () -> employeeReportProcessor.processEmployeeReport("invalidFilePath.csv"));
        assertTrue(employeeDataParseException.getMessage().contains("Failed to read the csv file"));
    }
}