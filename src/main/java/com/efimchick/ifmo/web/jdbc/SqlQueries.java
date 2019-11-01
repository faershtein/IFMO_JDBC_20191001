package com.efimchick.ifmo.web.jdbc;

/**
 * Implement sql queries like described
 */
public class SqlQueries {
    //Select all employees sorted by last name in ascending order
    //language=HSQLDB
    String select01 = "SELECT * FROM EMPLOYEE ORDER BY LASTNAME";

    //Select employees having no more than 5 characters in last name sorted by last name in ascending order
    //language=HSQLDB
    String select02 = "SELECT * FROM EMPLOYEE WHERE LENGTH(LASTNAME) <= 5 ORDER BY LASTNAME";

    //Select employees having salary no less than 2000 and no more than 3000
    //language=HSQLDB
    String select03 = "SELECT * FROM EMPLOYEE WHERE SALARY between 2000 and 3000";

    //Select employees having salary no more than 2000 or no less than 3000
    //language=HSQLDB
    String select04 = "SELECT * FROM EMPLOYEE WHERE SALARY <= 2000 OR SALARY >= 3000";

    //Select employees assigned to a department and corresponding department name
    //language=HSQLDB
    String select05 = "SELECT emp.id, FIRSTNAME, LASTNAME, SALARY, d.name " +
            "from EMPLOYEE emp " + "INNER JOIN DEPARTMENT D on emp.DEPARTMENT = D.ID";

    //Select all employees and corresponding department name if there is one.
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select06 = "SELECT emp.ID, FIRSTNAME, LASTNAME, SALARY, d.NAME " +
            "from EMPLOYEE emp " + "LEFT JOIN DEPARTMENT D on emp.DEPARTMENT = D.ID";

    //Select total salary pf all employees. Name it "total".
    //language=HSQLDB
    String select07 = "select  sum(SALARY) AS total from EMPLOYEE";

    //Select all departments and amount of employees assigned per department
    //Name column containing name of the department "depname".
    //Name column containing employee amount "staff_size".
    //language=HSQLDB
    String select08 = "select DEPARTMENT.NAME as depname, count(FIRSTNAME) as staff_size from EMPLOYEE, DEPARTMENT where EMPLOYEE.DEPARTMENT = DEPARTMENT.ID group by DEPARTMENT.NAME";

    //Select all departments and values of total and average salary per department
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select09 = "select DEPARTMENT.NAME as depname, sum(EMPLOYEE.SALARY) as total, avg(EMPLOYEE.SALARY) as avarege from EMPLOYEE, DEPARTMENT where  EMPLOYEE.DEPARTMENT = DEPARTMENT.ID group by  DEPARTMENT.NAME";

    //Select all employees and their managers if there is one.
    //Name column containing employee lastname "employee".
    //Name column containing manager lastname "manager".
    //language=HSQLDB
    String select10 = "select e.LASTNAME as employee, m.LASTNAME as manager from EMPLOYEE e left join EMPLOYEE m on e.MANAGER = m.ID";


}
