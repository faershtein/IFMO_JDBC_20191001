package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl extends BaseDao<Employee, BigInteger> implements EmployeeDao {

    @Override
    public List<Employee> getByDepartment(Department department) {
        return findList("select * from " + getTableName() + " where department = " + department.getId());
    }

    @Override
    public List<Employee> getByManager(Employee employee) {
        return findList("select * from " + getTableName() + " where manager = " + employee.getId());
    }

    @Override
    protected Employee extractFromResultSet(ResultSet resultSet) throws SQLException {
        FullName fullName = new FullName(
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("middleName")
        );

        BigInteger manager =
                Optional.ofNullable(resultSet.getBigDecimal("manager"))
                        .map(BigDecimal::toBigInteger)
                        .orElse(BigInteger.ZERO);

        BigInteger department =
                Optional.ofNullable(resultSet.getBigDecimal("department"))
                        .map(BigDecimal::toBigInteger)
                        .orElse(BigInteger.ZERO);

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

    @Override
    protected String getTableName() {
        return "Employee";
    }

    @Override
    protected BigInteger getId(Employee employee) {
        return employee.getId();
    }

    @Override
    protected String prepareInsertQuery(Employee employee) {
        return new StringBuilder()
                .append("insert into employee values ('")
                .append(employee.getId())
                .append("','")
                .append(employee.getFullName().getFirstName())
                .append("','")
                .append(employee.getFullName().getLastName())
                .append("','")
                .append(employee.getFullName().getMiddleName())
                .append("','")
                .append(employee.getPosition())
                .append("','")
                .append(employee.getManagerId())
                .append("','")
                .append(Date.valueOf(employee.getHired()))
                .append("','")
                .append(employee.getSalary())
                .append("','")
                .append(employee.getDepartmentId())
                .append("')")
                .toString();
    }
}
