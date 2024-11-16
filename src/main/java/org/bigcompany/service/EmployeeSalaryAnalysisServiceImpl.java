package org.bigcompany.service;

import org.bigcompany.exception.ValidationException;
import org.bigcompany.model.Employee;
import org.bigcompany.util.OutputPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bigcompany.util.ApplicationConstants.MAX_SALARY_MULTIPLIER;
import static org.bigcompany.util.ApplicationConstants.MIN_SALARY_MULTIPLIER;

public class EmployeeSalaryAnalysisServiceImpl implements EmployeeSalaryAnalysisService {


    @Override
    public void analyzeSalaries(List<Employee> employees) {
        if (employees.isEmpty()) {
            throw new ValidationException("No employees present in the list.");
        }
        Map<Integer, List<Employee>> managerToDirectSubordinates = new HashMap<>();
        List<String> managerEarningLess = new ArrayList<>();
        List<String> managerEarningMore = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getManagerId() != null) {
                managerToDirectSubordinates.computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>()).add(employee);
            }
        }

        for (Employee manager : employees) {
            if (managerToDirectSubordinates.containsKey(manager.getId())) {
                List<Employee> subordinates = managerToDirectSubordinates.get(manager.getId());
                double avgSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0);
                double minAllowedSalary = avgSalary * MIN_SALARY_MULTIPLIER;
                double maxAllowedSalary = avgSalary * MAX_SALARY_MULTIPLIER;

                if (manager.getSalary() < minAllowedSalary) {
                    managerEarningLess.add(manager.getFirstName() + " " + manager.getLastName() + " earns less than he should by " + (minAllowedSalary - manager.getSalary()));
                } else if (manager.getSalary() > maxAllowedSalary) {
                    managerEarningMore.add(manager.getFirstName() + " " + manager.getLastName() + " earns more than he should by " + (manager.getSalary() - maxAllowedSalary));
                }
            }
        }

        if (!managerEarningLess.isEmpty()) {
            System.out.println("**Managers earning less than they should**");
            OutputPrinter.display(managerEarningLess);
        }

        if (!managerEarningMore.isEmpty()) {
            System.out.println("**Managers earning more than they should**");
            OutputPrinter.display(managerEarningMore);
        }

        if (managerEarningLess.isEmpty() && managerEarningMore.isEmpty()) {
            System.out.println("All managers earn within the expected range.");
        }

    }

}