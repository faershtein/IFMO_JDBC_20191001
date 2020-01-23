package com.efimchick.ifmo.web.jdbc.service;

public class ServiceFactory {

    public EmployeeService employeeService(){
        return new EmployeeServiceImpl();
    }
}
