package org.bigcompany.data;

import org.bigcompany.exception.EmployeeDataParseException;
import org.bigcompany.exception.ValidationException;
import org.bigcompany.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDataImporterTest {

    private EmployeeDataImporter employeeDataImporter;
    String filePath;

    @BeforeEach
    void setUp() {
        employeeDataImporter = new EmployeeDataImporter();
        filePath = "src/test/resources/input.csv";
    }

    @Test
    void testLoadEmployees_returnsListOfEmployees() {
        List<Employee> actualEmployees = employeeDataImporter.loadEmployees(filePath);
        assertEquals(8, actualEmployees.size());
        assertEquals(123, actualEmployees.getFirst().getId());
        assertEquals("Joe", actualEmployees.getFirst().getFirstName());
        assertEquals("Doe", actualEmployees.getFirst().getLastName());
        assertEquals(100000.0, actualEmployees.getFirst().getSalary());
        assertNull(actualEmployees.getFirst().getManagerId());
    }

    @Test
    void testLoadEmployees_returnsEmptyListForEmptyFile() {
        filePath = "src/test/resources/empty.csv";
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            employeeDataImporter.loadEmployees(filePath);
        });
        assertEquals("The file is empty", exception.getMessage());
    }

    @Test
    void testLoadEmployees_throwsExceptionForInvalidFilePath() {
        EmployeeDataParseException employeeDataParseException = assertThrows(EmployeeDataParseException.class, () -> employeeDataImporter.loadEmployees("invalidFilePath.csv"));
        assertTrue(employeeDataParseException.getMessage().contains("Failed to read the csv file"));
    }

    @Test
    void testLoadEmployees_throwsExceptionWhenMoreThan1000Rows() {
        filePath = "src/test/resources/large_input.csv";
        ValidationException employeeDataParseException = assertThrows(ValidationException.class, () -> employeeDataImporter.loadEmployees(filePath));
        assertEquals("The file contains more than 1000 rows", employeeDataParseException.getMessage());
    }
}