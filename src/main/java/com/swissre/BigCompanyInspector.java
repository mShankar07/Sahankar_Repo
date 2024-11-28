package com.swissre;

import java.util.*;
import java.util.stream.Collectors;

public class BigCompanyInspector {
    private static final int MAX_REPORTING_LEVELS = 4;
    private static final double DEFAULT_VALUE=0.0;
    private static final double MIN_EXPECTED=1.2;
    private static final double MAX_EXPECTED=1.5;

    public static void inspector(List<Employee> employees) {
        Map<Integer, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));

        inspectManagerSalaries(employees);
        inspectReportingLines(employees, employeeMap);
    }

    public static void inspectManagerSalaries(List<Employee> employees) {
        for (Employee manager : employees) {
            List<Employee> subordinates = employees.stream()
                    .filter(e -> e.getManagerId() != null && e.getManagerId().equals(manager.getId()))
                    .toList();

            if (subordinates.isEmpty()) continue;

            double avgSubordinateSalary = subordinates.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(DEFAULT_VALUE);

            double minExpectedSalary = avgSubordinateSalary * MIN_EXPECTED;
            double maxExpectedSalary = avgSubordinateSalary * MAX_EXPECTED;

            if (manager.getSalary() < minExpectedSalary) {
                System.out.printf("Manager %s earns %.2f less than the expected amount.%n",
                        manager.getFirstName()+ " "+manager.getLastName(), minExpectedSalary - manager.getSalary());
            } else if (manager.getSalary() > maxExpectedSalary) {
                System.out.printf("Manager %s earns %.2f above the expected amount.%n",
                        manager.getFirstName()+ " "+manager.getLastName(), manager.getSalary() - maxExpectedSalary);
            }
        }
    }

    public static void inspectReportingLines(List<Employee> employees, Map<Integer, Employee> employeeMap) {
        for (Employee employee : employees) {
            int reportingLevels = calculateReportingLevels(employee, employeeMap);
            if (reportingLevels > MAX_REPORTING_LEVELS) {
                System.out.printf("Employee %s's reporting line is %d level longer than it should be.%n",
                        employee.getFirstName()+ " "+employee.getLastName(), reportingLevels - MAX_REPORTING_LEVELS);
            }
        }
    }

    public static int calculateReportingLevels(Employee employee, Map<Integer, Employee> employeeMap) {
        int levels = 0;
        Integer managerId = employee.getManagerId();

        while (managerId != null) {
            levels++;
            Employee manager = employeeMap.get(managerId);
            if (manager == null) break;
            managerId = manager.getManagerId();
        }

        return levels;
    }
}
