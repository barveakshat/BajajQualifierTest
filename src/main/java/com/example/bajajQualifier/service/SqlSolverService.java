package com.example.bajajQualifier.service;

import org.springframework.stereotype.Service;

@Service
public class SqlSolverService {

    public String solveSQL(String regNo) {
        String digitsOnly = regNo.replaceAll("\\D+", "");
        int lastTwo = Integer.parseInt(digitsOnly.substring(digitsOnly.length() - 2));

        if (lastTwo % 2 == 1) {
            // Question 1 SQL 
            return "SELECT ... FROM ...";
        } else {
            // Question 2 SQL 
            return """
                SELECT 
                    e1.EMP_ID,
                    e1.FIRST_NAME,
                    e1.LAST_NAME,
                    d.DEPARTMENT_NAME,
                    COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT
                FROM EMPLOYEE e1
                JOIN DEPARTMENT d 
                    ON e1.DEPARTMENT = d.DEPARTMENT_ID
                LEFT JOIN EMPLOYEE e2 
                    ON e1.DEPARTMENT = e2.DEPARTMENT 
                   AND e2.DOB > e1.DOB
                GROUP BY 
                    e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME
                ORDER BY 
                    e1.EMP_ID DESC;
                """;
        }
    }
}

