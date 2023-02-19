package HrManagementSys.persistence;

import HrManagementSys.model.Admin;
import HrManagementSys.model.Department;

import java.util.Collection;

public interface DepartmentDAO extends DAO<Department> {
    Collection<Department>findDepartmentsForAdmin(Admin person);
}
