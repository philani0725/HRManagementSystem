package HrManagementSys.persistence.collectionbased;

import HrManagementSys.model.Admin;
import HrManagementSys.model.Department;
import HrManagementSys.persistence.DepartmentDAO;

import java.util.Collection;
import java.util.stream.Collectors;

public class DepartmentDAOImpl extends CollectionBasedDAO<Department> implements DepartmentDAO {

    @Override
    public Collection<Department> findDepartmentsForAdmin(Admin person) {
        return findAll().stream()
                .filter(e -> e.getAdmin().equals(person))
                .collect(Collectors.toUnmodifiableList());
    }
}
