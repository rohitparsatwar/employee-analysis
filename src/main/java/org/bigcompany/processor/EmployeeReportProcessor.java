package org.bigcompany.processor;

import org.bigcompany.data.EmployeeDataImporter;
import org.bigcompany.model.Employee;
import org.bigcompany.service.EmployeeReportingLineAnalysisService;
import org.bigcompany.service.EmployeeReportingLineAnalysisServiceImpl;
import org.bigcompany.service.EmployeeSalaryAnalysisService;
import org.bigcompany.service.EmployeeSalaryAnalysisServiceImpl;

import java.util.List;

public class EmployeeReportProcessor {

    private final EmployeeDataImporter employeeDataLoader = new EmployeeDataImporter();
    private final EmployeeSalaryAnalysisService employeeSalaryAnalysisService = new EmployeeSalaryAnalysisServiceImpl();
    private final EmployeeReportingLineAnalysisService employeeReportingLineAnalysisService = new EmployeeReportingLineAnalysisServiceImpl();

    public void processEmployeeReport(String filePath) {
        List<Employee> employees = employeeDataLoader.loadEmployees(filePath);
        employeeSalaryAnalysisService.analyzeSalaries(employees);
        employeeReportingLineAnalysisService.analyzeReportingLines(employees);
    }
}
