package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.domain.Department;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDaoImpl extends BaseDao<Department, BigInteger> implements DepartmentDao {

    @Override
    protected Department extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Department(resultSet.getBigDecimal("id").toBigInteger(),
                resultSet.getString("name"),
                resultSet.getString("location"));
    }

    @Override
    protected String getTableName() {
        return "Department";
    }

    @Override
    protected BigInteger getId(Department department) {
        return department.getId();
    }

    @Override
    protected String prepareInsertQuery(Department department) {
        return new StringBuilder()
                .append("insert into department values ('")
                .append(department.getId())
                .append("','")
                .append(department.getName())
                .append("','")
                .append(department.getLocation())
                .append("')")
                .toString();

    }

}
