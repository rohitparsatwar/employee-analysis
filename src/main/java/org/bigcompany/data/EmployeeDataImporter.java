package org.bigcompany.data;

import org.bigcompany.model.Employee;
import org.bigcompany.util.EmployeeCSVImporter;

import java.util.List;

public class EmployeeDataImporter {
    public List<Employee> loadEmployees(String filePath) {
        return EmployeeCSVImporter.readEmployeeData(filePath);
    }
}
