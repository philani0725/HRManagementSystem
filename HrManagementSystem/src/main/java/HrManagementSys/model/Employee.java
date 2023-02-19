package HrManagementSys.model;

/*
 ** DO NOT CHANGE!!
 */


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Employee extends PersistentModel<Employee> {

    private final Admin manager;

    private final  String FIRSTNAME;
    private final String LASTNAME;
    private final String EMAIL;
    private final String TELNO;
    private final String EMPLOYEEMANAGER;
    private final String STATUS;
    private final String PASSWORD;

    public Employee(Admin admin, String firstname, String lastname, String email, String telno, String employeemanager, String status, String password) {
        this.FIRSTNAME = firstname;
        this.LASTNAME = lastname;
        this.EMAIL = email;
        this.TELNO = telno;
        this.EMPLOYEEMANAGER = employeemanager;
        this.STATUS = status;
        this.PASSWORD = password;
        this.manager = admin;
    }


    public Admin getAdmin() {
        return manager;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getTELNO() {
        return TELNO;
    }

    public String getEMPLOYEEMANAGER() {
        return EMPLOYEEMANAGER;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee expense = (Employee) o;
        return Objects.equal(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Manager", manager)
                .add("firstname", FIRSTNAME)
                .add("lastname", LASTNAME)
                .add("email", EMAIL)
                .add("employee_manager", EMPLOYEEMANAGER)
                .add("status", STATUS)
                .add("password", PASSWORD)
                .add("telephone", TELNO)
                .toString();
    }
}
