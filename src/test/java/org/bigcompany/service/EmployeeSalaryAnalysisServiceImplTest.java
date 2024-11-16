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

class EmployeeSalaryAnalysisServiceImplTest {

    private EmployeeSalaryAnalysisServiceImpl employeeSalaryAnalysisService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        employeeSalaryAnalysisService = new EmployeeSalaryAnalysisServiceImpl();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testAnalyzeSalaries_throwsExceptionForEmptyEmployeeList() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> employeeSalaryAnalysisService.analyzeSalaries(List.of()));
        assertEquals("No employees present in the list.", validationException.getMessage());
    }

    @Test
    void testAnalyzeSalaries_managerEarningLess() {
        Employee manager = new Employee();
        manager.setId(1);
        manager.setFirstName("Joe");
        manager.setLastName("Doe");
        manager.setSalary(50000);
        manager.setManagerId(null);

        Employee subordinate = new Employee();
        subordinate.setId(2);
        subordinate.setFirstName("Martin");
        subordinate.setLastName("Chekov");
        subordinate.setSalary(100000);
        subordinate.setManagerId(1);

        List<Employee> employees = List.of(manager, subordinate);
        employeeSalaryAnalysisService.analyzeSalaries(employees);
        assertTrue(outContent.toString().contains("Joe Doe earns less than he should by 70000.0"));
    }

    @Test
    void testAnalyzeSalaries_managerEarningMore() {
        Employee manager = new Employee();
        manager.setId(1);
        manager.setFirstName("Joe");
        manager.setLastName("Doe");
        manager.setSalary(200000);
        manager.setManagerId(null);

        Employee subordinate = new Employee();
        subordinate.setId(2);
        subordinate.setFirstName("Martin");
        subordinate.setLastName("Chekov");
        subordinate.setSalary(100000);
        subordinate.setManagerId(1);

        List<Employee> employees = List.of(manager, subordinate);
        employeeSalaryAnalysisService.analyzeSalaries(employees);
        assertTrue(outContent.toString().contains("Joe Doe earns more than he should by 50000.0"));
    }

    @Test
    void testAnalyzeSalaries_managerEarningWithinRange() {
        Employee manager = new Employee();
        manager.setId(1);
        manager.setFirstName("Joe");
        manager.setLastName("Doe");
        manager.setSalary(120000);
        manager.setManagerId(null);

        Employee subordinate = new Employee();
        subordinate.setId(2);
        subordinate.setFirstName("Martin");
        subordinate.setLastName("Chekov");
        subordinate.setSalary(100000);
        subordinate.setManagerId(1);

        List<Employee> employees = List.of(manager, subordinate);
        employeeSalaryAnalysisService.analyzeSalaries(employees);
        assertTrue(outContent.toString().contains("All managers earn within the expected range."));
    }
}