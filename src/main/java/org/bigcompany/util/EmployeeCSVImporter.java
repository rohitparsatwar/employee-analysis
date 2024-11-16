package org.bigcompany.util;

import org.bigcompany.exception.EmployeeDataParseException;
import org.bigcompany.exception.ValidationException;
import org.bigcompany.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.bigcompany.util.ApplicationConstants.MAX_ROWS;

public class EmployeeCSVImporter {

    private EmployeeCSVImporter() {

    }

    public static List<Employee> readEmployeeData(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            if (lines.isEmpty()) {
                throw new ValidationException("The file is empty");
            }
            //Not allowing more than 1000 rows
            if (lines.size() - 1 > MAX_ROWS) {
                throw new ValidationException("The file contains more than " + MAX_ROWS + " rows");
            }
            List<String> linesExcludingHeaders = lines.subList(1, lines.size());
            for (String line : linesExcludingHeaders) {
                String[] fields = line.split(",", -1);
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(fields[0].trim()));
                employee.setFirstName(fields[1].trim());
                employee.setLastName(fields[2].trim());
                employee.setSalary(Double.parseDouble(fields[3].trim()));
                employee.setManagerId(fields[4].trim().isEmpty() ? null : Integer.parseInt(fields[4].trim()));
                employees.add(employee);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the csv file. Error is " + e.getMessage());
            throw new EmployeeDataParseException("Failed to read the csv file. Error is " + e.getMessage());
        }
        return employees;
    }
}
