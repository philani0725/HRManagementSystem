package HrManagementSys.api.dto;

import HrManagementSys.model.Employee;
import com.google.common.base.MoreObjects;


public class EmployeeDTO {
    private final int EmployeeID;
    private final int adminID;
    private final String FIRSTNAME;
    private final String LASTNAME;
    private final String EMAIL;
    private final String TELNO;
    private final String EMPLOYEEMANAGER;
    private final String STATUS;
    private final String PASSWORD;

    private EmployeeDTO(int employeeID, int adminID, String firstname, String lastname, String email, String telno, String employeemanager, String status, String password) {
        this.EmployeeID = employeeID;
        this.adminID = adminID;
        this.FIRSTNAME = firstname;
        this.LASTNAME = lastname;
        this.EMAIL = email;
        this.TELNO = telno;
        this.EMPLOYEEMANAGER = employeemanager;
        this.STATUS = status;
        this.PASSWORD = password;
    }
    public static EmployeeDTO fromEmployee(Employee employee){
        return new EmployeeDTO(
                employee.getId(),
                employee.getAdmin().getId(),
                employee.getFIRSTNAME(),
                employee.getLASTNAME(),
                employee.getEMAIL(),
                employee.getTELNO(),
                employee.getEMPLOYEEMANAGER(),
                employee.getSTATUS(),
                employee.getPASSWORD());
    }

    public int getEmployeeID() {
        return EmployeeID;
    }
    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public int getAdminID() {
        return adminID;
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
        return this.STATUS;
    }

    public String getPASSWORD() {
        return this.PASSWORD;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("FIRSTNAME", FIRSTNAME)
                .add("LASTNAME", LASTNAME)
                .add("EmployeeID", EmployeeID)
                .add("EMAIL", EMAIL)
                .add("TELNO", TELNO)
                .add("EMPLOYEEMANAGER", EMPLOYEEMANAGER)
                .add("STATUS", STATUS)
                .add("PASSWORD", PASSWORD)
                .toString();
    }
}
