package HrManagementSys.persistence.collectionbased;

/*
 ** DO NOT CHANGE!!
 */


import HrManagementSys.model.Admin;
import HrManagementSys.model.Employee;
import HrManagementSys.persistence.EmployeeDAO;

import java.util.Collection;
import java.util.stream.Collectors;

public class EmployeeDAOImpl extends CollectionBasedDAO<Employee> implements EmployeeDAO {

    @Override
    public Collection<Employee> findEmployeesForAdmin(Admin person) {
        return findAll().stream()
                .filter(e -> e.getAdmin().equals(person))
                .collect(Collectors.toUnmodifiableList());
    }
}
