package org.bigcompany.integrationtests;

import org.bigcompany.EmployeeAnalysisApplication;
import org.bigcompany.exception.EmployeeDataParseException;
import org.bigcompany.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeSalaryAnalysisApplicationTest {


    @Test
    void testIntegrationValidFile() {
        String[] args = {"src/test/resources/input.csv"};
        assertDoesNotThrow(() -> EmployeeAnalysisApplication.main(args));
    }

    @Test
    void testIntegrationInvalidFile() {
        String[] args = {"invalidFile.txt"};
        assertThrows(EmployeeDataParseException.class, () -> EmployeeAnalysisApplication.main(args));
    }

    @Test
    void testNoFilePassed() {
        String[] args = {};
        ValidationException validationException = assertThrows(ValidationException.class, () -> EmployeeAnalysisApplication.main(args));
        assertEquals("No input file provided. Please pass the path to the input file as an argument.", validationException.getMessage());
    }
}