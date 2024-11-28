package com.swissre;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BigCompanyInspectorTest {

    private List<Employee> employees;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
         employees = Arrays.asList(
                new Employee(1, "John", "Doe", 50000, null),
                new Employee(2, "Jane", "Smith", 30000, 1),
                new Employee(3, "Robert", "Brown", 35000, 2),
                new Employee(4, "Shankar", "Sub", 20000, 3),
                new Employee(5, "Muthu", "Kumar", 21000, 4),
                new Employee(6, "Selvi", "Rani", 22000, 5),
                new Employee(7, "Micheal", "R", 23000, 4),
                new Employee(8, "Ravi", "Kumar", 18000, 7),
                new Employee(9, "Pradeepa", "Shank", 30000, 5)
        );
    }

        @Test
        public void testCalculateReportingLevels() {

        Map<Integer, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));

        assertEquals(0, BigCompanyInspector.calculateReportingLevels(employees.get(0), employeeMap));
        assertEquals(1, BigCompanyInspector.calculateReportingLevels(employees.get(1), employeeMap));
        assertEquals(5, BigCompanyInspector.calculateReportingLevels(employees.get(8), employeeMap));
    }
}
