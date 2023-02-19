package HrManagementSys.api;

import HrManagementSys.HRException;
import HrManagementSys.api.dto.DepartmentDTO;
import HrManagementSys.api.dto.EmployeeDTO;
import HrManagementSys.api.dto.NewDepartmentDTO;
import HrManagementSys.api.dto.NewEmployeeDTO;
import HrManagementSys.model.Admin;
import HrManagementSys.model.Department;
import HrManagementSys.model.Employee;
import HrManagementSys.persistence.AdminDAO;
import HrManagementSys.persistence.DepartmentDAO;
import HrManagementSys.persistence.EmployeeDAO;
import HrManagementSys.server.ServiceRegistry;

import java.util.Collection;
import java.util.Optional;

public class WebService {

    private static AdminDAO adminDAO() {
        return ServiceRegistry.lookup(AdminDAO.class);
    }

    private static EmployeeDAO employeeDAO() {
        return ServiceRegistry.lookup(EmployeeDAO.class);
    }
    private static DepartmentDAO departmentDAO() {
        return ServiceRegistry.lookup(DepartmentDAO.class);
    }

    public static Collection<Employee> findAllEmployees() {
        return employeeDAO().findAll();
    }

    public static Optional<Employee> getEmployee(int id) {
        return employeeDAO().findById(id);
    }

    public static Collection<Employee> findEmployeesForAdmin(int id) {
        Admin person = verifyAdmin(id);
        return employeeDAO().findEmployeesForAdmin(person);
    }

    public static Employee saveEmployee(Employee employee) {
        verifyAdmin(employee.getAdmin().getId());
        return employeeDAO().save(employee);
    }

    public static Department saveDepartment(Department department) {
        verifyAdmin(department.getAdmin().getId());
        return departmentDAO().save(department);
    }

    public static EmployeeDTO createNewEmployee(NewEmployeeDTO newEmployeeDTO) {
        Admin person = verifyAdmin(newEmployeeDTO.getAdminId());
        Employee expense = new Employee(person,newEmployeeDTO.getFIRSTNAME(), newEmployeeDTO.getLASTNAME(), newEmployeeDTO.getEMAIL(),
                newEmployeeDTO.getTELNO(), newEmployeeDTO.getEMPLOYEEMANAGER(), newEmployeeDTO.getSTATUS(), newEmployeeDTO.getPASSWORD());
        return EmployeeDTO.fromEmployee(employeeDAO().save(expense));
    }

    public static DepartmentDTO createNewEmployee(NewDepartmentDTO newDepartmentDTO) {
        Admin person = verifyAdmin(newDepartmentDTO.getAdminId());
        Department department = new Department(person, newDepartmentDTO.getStatus(), newDepartmentDTO.getManager(), newDepartmentDTO.getNAME());
        return DepartmentDTO.fromDepartment(departmentDAO().save(department));
    }


    private static Admin verifyAdmin(int id) {
        return adminDAO().findById(id)
                .orElseThrow(() -> new HRException("Admin not found: " + id));
    }

    private static Employee verifyEmployee(int id) {
        return employeeDAO().findById(id)
                .orElseThrow(() -> new HRException("Employee not found: " + id));
    }

    private static Department verifyDepartment(int id) {
        return departmentDAO().findById(id)
                .orElseThrow(() -> new HRException("Department not found: " + id));
    }

    public static Admin saveAdmin(Admin person) {
        return adminDAO().save(person);
    }

    public static Collection<Admin> findAllAdmin() {
        return adminDAO().findAll();
    }

    public static Optional<Admin> getAdmin(int id) {
        return adminDAO().findById(id);
    }


    @Deprecated
    public static Optional<Admin> findAdminByEmail(String email,String password) {
        return adminDAO().findByEmailPassword(email,password);
    }

    public static Admin findAdminByEmailOrCreate(String email ,String password  ) {
        Optional<Admin> maybePerson = adminDAO().findByEmailPassword(email,password);
        return maybePerson.orElse(new Admin(email,password));
    }

    public static Optional<Department> getDepartment(int id) {
        return departmentDAO().findById(id);
    }

    public static Collection<Department> findAllDepartments() {
        return departmentDAO().findAll();
    }

    public static Collection<Department> findDepartmentsForAdmin(Integer id) {
        Admin person = verifyAdmin(id);
        return departmentDAO().findDepartmentsForAdmin(person);
    }
}
