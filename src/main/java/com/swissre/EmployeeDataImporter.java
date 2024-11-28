package com.swissre;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataImporter {
    public static List<Employee> loadEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                int id = (int) row.getCell(0).getNumericCellValue();
                String firstName = row.getCell(1).getStringCellValue();
                String lastName = row.getCell(2).getStringCellValue();
                double salary = row.getCell(3).getNumericCellValue();
                Integer managerId = row.getCell(4) != null ? (int) row.getCell(4).getNumericCellValue() : null;

                employees.add(new Employee(id, firstName, lastName, salary, managerId));
            }
        }

        return employees;
    }
}


