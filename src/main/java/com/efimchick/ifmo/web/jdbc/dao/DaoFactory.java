package com.efimchick.ifmo.web.jdbc.dao;

public class DaoFactory {
    public EmployeeDao employeeDAO()  {
        return new EmployeeDaoImpl();
    }

    public DepartmentDao departmentDAO()  {
        return new DepartmentDaoImpl();
    }
}
