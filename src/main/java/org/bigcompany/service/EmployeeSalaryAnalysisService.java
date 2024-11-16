package org.bigcompany.service;

import org.bigcompany.model.Employee;

import java.util.List;

public interface EmployeeSalaryAnalysisService {
    void analyzeSalaries(List<Employee> employees);

}
