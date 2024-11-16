package org.bigcompany.service;

import org.bigcompany.exception.ValidationException;
import org.bigcompany.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeReportingLineAnalysisServiceImplTest {

    private EmployeeReportingLineAnalysisServiceImpl employeeReportingLineAnalysisService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        employeeReportingLineAnalysisService = new EmployeeReportingLineAnalysisServiceImpl();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testAnalyzeReportingLines_throwsExceptionForEmptyEmployeeList() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> employeeReportingLineAnalysisService.analyzeReportingLines(List.of()));
        assertEquals("No employees present in the list.", validationException.getMessage());
    }

    @Test
    void testAnalyzeReportingLines_whenValidEmployeeList() {
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setFirstName("Joe");
        e1.setLastName("Doe");
        e1.setSalary(50000);
        e1.setManagerId(2);
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setFirstName("Martin");
        e2.setLastName("Chekov");
        e2.setSalary(60000);
        e2.setManagerId(null);

        List<Employee> employees = List.of(e1, e2);
        employeeReportingLineAnalysisService.analyzeReportingLines(employees);
        assertEquals("All employees have a reporting line which is within the limit.\n", outContent.toString());
    }

    @Test
    void testAnalyzeReportingLines_whenLongReportingLine() {
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setFirstName("Joe");
        e1.setLastName("Doe");
        e1.setSalary(50000);
        e1.setManagerId(2);
        Employee e2 = new Employee();
        e2.setId(2);
        e2.setFirstName("Martin");
        e2.setLastName("Chekov");
        e2.setSalary(60000);
        e2.setManagerId(3);
        Employee e3 = new Employee();
        e3.setId(3);
        e3.setFirstName("Bob");
        e3.setLastName("Ronstadt");
        e3.setSalary(70000);
        e3.setManagerId(4);
        Employee e4 = new Employee();
        e4.setId(4);
        e4.setFirstName("Alice");
        e4.setLastName("Hasacat");
        e4.setSalary(80000);
        e4.setManagerId(5);
        Employee e5 = new Employee();
        e5.setId(5);
        e5.setFirstName("Brett");
        e5.setLastName("Hardleaf");
        e5.setSalary(90000);
        e5.setManagerId(6);
        Employee e6 = new Employee();
        e6.setId(6);
        e6.setFirstName("Lee");
        e6.setLastName("Hardleaf");
        e6.setSalary(90000);
        e6.setManagerId(7);
        Employee e7 = new Employee();
        e7.setId(7);
        e7.setFirstName("Lee2");
        e7.setLastName("Hardleaf");
        e7.setSalary(90000);
        e7.setManagerId(null);

        List<Employee> employees = List.of(e1, e2, e3, e4, e5, e6, e7);
        employeeReportingLineAnalysisService.analyzeReportingLines(employees);
        assertTrue(outContent.toString().contains("Joe Doe has a reporting line which is too long by 1."));
    }
}