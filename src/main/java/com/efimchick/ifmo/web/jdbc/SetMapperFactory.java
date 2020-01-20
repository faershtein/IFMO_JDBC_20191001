package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SetMapperFactory {

    private static EmployeeSetMapper employeeSetMapper = new EmployeeSetMapper();

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return employeeSetMapper;
    }

    static class EmployeeSetMapper implements SetMapper<Set<Employee>> {

        @Override
        public Set<Employee> mapSet(ResultSet resultSet) {
            Set<Employee> employees = new HashSet<>();
            try {
                while (resultSet.next()) {
                    employees.add(getEmployee(resultSet));
                }
                return employees;
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw new RuntimeException("Error parsing result set.", exception);
            }
        }

        private Employee getEmployee(ResultSet resultSet) throws SQLException {
            return parseEmployee(resultSet, getManager(resultSet));
        }

        private Employee getManager(ResultSet resultSet) throws SQLException {
            BigDecimal managerId = resultSet.getBigDecimal("manager");
            Employee manager = null;
            if (managerId != null) {
                int cursor = resultSet.getRow();
                resultSet.absolute(0);
                while (resultSet.next()) {
                    if (Objects.equals(resultSet.getBigDecimal("id"), managerId)) {
                        manager = getEmployee(resultSet);
                        break;
                    }
                }
                resultSet.absolute(cursor);
            }
            return manager;
        }

        private Employee parseEmployee(ResultSet resultSet, Employee manager) throws SQLException {
            FullName fullName = new FullName(
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("middleName")
            );

            return new Employee(
                    resultSet.getBigDecimal("id").toBigInteger(),
                    fullName,
                    Position.valueOf(resultSet.getString("position")),
                    resultSet.getDate("hireDate").toLocalDate(),
                    resultSet.getBigDecimal("salary"),
                    manager
            );
        }
    }

}

