package org.bigcompany.service;

import org.bigcompany.exception.ValidationException;
import org.bigcompany.model.Employee;
import org.bigcompany.util.OutputPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bigcompany.util.ApplicationConstants.MAX_REPORTING_LINE_LENGTH;

public class EmployeeReportingLineAnalysisServiceImpl implements EmployeeReportingLineAnalysisService {

    @Override
    public void analyzeReportingLines(List<Employee> employees) {
        if (employees.isEmpty()) {
            throw new ValidationException("No employees present in the list.");
        }

        Map<Integer, Employee> employeeMap = new HashMap<>();
        List<String> employeesHavingLongReportingLine = new ArrayList<>();

        for (Employee employee : employees) {
            employeeMap.put(employee.getId(), employee);
        }

        for (Employee employee : employees) {
            int count = 0;
            Employee current = employee;
            while (current.getManagerId() != null) {
                count++;
                current = employeeMap.get(current.getManagerId());
            }
            if ((count - 1) > MAX_REPORTING_LINE_LENGTH) {
                employeesHavingLongReportingLine.add(employee.getFirstName() + " " + employee.getLastName() + " has a reporting line which is too long by " + ((count - 1) - MAX_REPORTING_LINE_LENGTH) + ".");
            }
        }

        if (!employeesHavingLongReportingLine.isEmpty()) {
            System.out.println("**Employees having a reporting line which is too long**");
            OutputPrinter.display(employeesHavingLongReportingLine);
        } else {
            System.out.println("All employees have a reporting line which is within the limit.");
        }

    }
}