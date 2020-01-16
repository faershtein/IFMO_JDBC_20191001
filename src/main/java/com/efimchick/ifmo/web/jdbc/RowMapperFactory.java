package com.efimchick.ifmo.web.jdbc;

import com.efimchick.ifmo.web.jdbc.domain.Employee;
import com.efimchick.ifmo.web.jdbc.domain.FullName;
import com.efimchick.ifmo.web.jdbc.domain.Position;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowMapperFactory {
    public RowMapper<Employee> employeeRowMapper() {
        RowMapper<Employee> ROWMAPPER = new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet RSLT){
                try {
                    Employee employ = new Employee(
                            new BigInteger(String.valueOf(RSLT.getString("id"))),
                            new FullName(RSLT.getString("firstname"),
                                    RSLT.getString("lastname"),
                                    RSLT.getString("middlename")),
                            Position.valueOf(RSLT.getString("position")),
                            LocalDate.parse(RSLT.getString("hiredate")),
                            new BigDecimal(String.valueOf(RSLT.getDouble("salary")))
                    );
                    return employ;
                } catch (SQLException exception){
                    exception.printStackTrace();
                    return null;
                }
            }
        };
        return ROWMAPPER;
    }
}
