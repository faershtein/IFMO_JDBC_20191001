package com.efimchick.ifmo.web.jdbc.service;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;
import com.efimchick.ifmo.web.jdbc.domain.Department;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDao {

    private ConnectionSource connectionSource = ConnectionSource.instance();

    public Department getById(BigInteger id) {
        try (Connection connection = connectionSource.createConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("select * from department where id = ?");
            statement.setLong(1, id.longValue());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return extractFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Department extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Department(resultSet.getBigDecimal("id").toBigInteger(),
                resultSet.getString("name"),
                resultSet.getString("location"));
    }

}
