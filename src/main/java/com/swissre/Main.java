package com.swissre;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/Employee.xls";
        try {
            List<Employee> employees = EmployeeDataImporter.loadEmployees(filePath);
            BigCompanyInspector.inspector(employees);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}

