package com.efimchick.ifmo.web.jdbc.service;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;
import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmployeeDao {

    private ConnectionSource connectionSource;
    private DepartmentDao departmentDao;

    public EmployeeDao() {
        connectionSource = ConnectionSource.instance();
        departmentDao = new DepartmentDao();
    }

    public List<Employee> getPage(Paging paging, String query) {
        try (Connection connection = connectionSource.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + getPagination(paging));

            List<Employee> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(extractFromResultSet(resultSet, true, false));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Employee getById(BigInteger id, boolean isFirst, boolean cascade) {
        try (Connection connection = connectionSource.createConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("select * from employee where id = ?");
            statement.setLong(1, id.longValue());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return extractFromResultSet(resultSet, false, cascade);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getTopNth(String query, int topN) {
        try (Connection connection = connectionSource.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + " limit 1 offset " + (topN - 1));
            resultSet.next();
            return extractFromResultSet(resultSet, true, false);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getPagination(Paging paging) {
        return " limit " + paging.itemPerPage + " offset " + paging.itemPerPage * (paging.page - 1);
    }

    private Employee extractFromResultSet(ResultSet resultSet, boolean isFirst, boolean cascade) throws SQLException {
        FullName fullName = new FullName(
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("middleName")
        );

        Employee manager =
                Optional.ofNullable(isFirst || cascade ? resultSet.getBigDecimal("manager") : null)
                        .map(id -> getById(id.toBigInteger(), false, cascade))
                        .orElse(null);

        Department department =
                Optional.ofNullable(resultSet.getBigDecimal("department"))
                        .map(BigDecimal::toBigInteger)
                        .map(departmentDao::getById)
                        .orElse(null);

        return new Employee(
                resultSet.getBigDecimal("id").toBigInteger(),
                fullName,
                Position.valueOf(resultSet.getString("position")),
                resultSet.getDate("hireDate").toLocalDate(),
                resultSet.getBigDecimal("salary"),
                manager,
                department
        );
    }

}
