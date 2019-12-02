package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.Position;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowMapperFactory {
    public RowMapper<Employee> employeeRowMapper() {
        RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet) {
                try {
                    Employee employee = new Employee(
                            new BigInteger(String.valueOf(resultSet.getInt("id"))),
                            new FullName(
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("middleName")),
                            Position.valueOf(resultSet.getString("position")),
                            LocalDate.parse(resultSet.getString("hiredate")),
                            new BigDecimal(String.valueOf(resultSet.getInt("salary"))));


                    return employee;
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        };
        return rowMapper;
    }
}