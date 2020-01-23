package com.efimchick.ifmo.web.jdbc.service;

import com.efimchick.ifmo.web.jdbc.domain.Department;
import com.efimchick.ifmo.web.jdbc.domain.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    public static final String SELECT_FROM_EMPLOYEE = "select * from employee";
    public static final String SELECT_FROM_EMPLOYEE_BY_DEPARTMENT = SELECT_FROM_EMPLOYEE + " where department = ";
    public static final String SELECT_FROM_EMPLOYEE_BY_MANAGER = SELECT_FROM_EMPLOYEE + " where manager = ";

    private EmployeeDao employeeDao = new EmployeeDao();

    @Override
    public List<Employee> getAllSortByHireDate(Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE + " order by hiredate");
    }

    @Override
    public List<Employee> getAllSortByLastname(Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE + " order by lastname");
    }

    @Override
    public List<Employee> getAllSortBySalary(Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE + " order by salary");
    }

    @Override
    public List<Employee> getAllSortByDepartmentNameAndLastname(Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE + " order by department, lastname");
    }

    @Override
    public List<Employee> getByDepartmentSortByHireDate(Department department, Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE_BY_DEPARTMENT + department.getId() + " order by hiredate");
    }

    @Override
    public List<Employee> getByDepartmentSortBySalary(Department department, Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE_BY_DEPARTMENT + department.getId() + " order by salary");
    }

    @Override
    public List<Employee> getByDepartmentSortByLastname(Department department, Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE_BY_DEPARTMENT + department.getId() + " order by lastname");
    }

    @Override
    public List<Employee> getByManagerSortByLastname(Employee manager, Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE_BY_MANAGER + manager.getId() + " order by lastname");
    }

    @Override
    public List<Employee> getByManagerSortByHireDate(Employee manager, Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE_BY_MANAGER + manager.getId() + " order by hiredate");
    }

    @Override
    public List<Employee> getByManagerSortBySalary(Employee manager, Paging paging) {
        return employeeDao.getPage(paging, SELECT_FROM_EMPLOYEE_BY_MANAGER + manager.getId() + " order by salary");
    }

    @Override
    public Employee getWithDepartmentAndFullManagerChain(Employee employee) {
        return employeeDao.getById(employee.getId(), true, true);
    }

    @Override
    public Employee getTopNthBySalaryByDepartment(int salaryRank, Department department) {
        return employeeDao.getTopNth(SELECT_FROM_EMPLOYEE + " where department = " + department.getId() + " order by salary desc", salaryRank);
    }

}
