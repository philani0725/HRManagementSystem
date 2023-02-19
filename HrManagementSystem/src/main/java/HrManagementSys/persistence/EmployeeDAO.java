package HrManagementSys.persistence;

/*
 ** DO NOT CHANGE!!
 */


import HrManagementSys.model.Admin;
import HrManagementSys.model.Employee;

import java.util.Collection;

public interface EmployeeDAO extends DAO<Employee> {
    Collection<Employee> findEmployeesForAdmin(Admin person);
}
