package org.bigcompany.service;

import org.bigcompany.model.Employee;

import java.util.List;

public interface EmployeeReportingLineAnalysisService {

    void analyzeReportingLines(List<Employee> employees);
}
