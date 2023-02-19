package HrManagementSys.api.dto;

import com.google.common.base.MoreObjects;

public class NewEmployeeDTO {

    private final  String FIRSTNAME;
    private final String LASTNAME;
    private final String EMAIL;
    private final String TELNO;
    private final String EMPLOYEEMANAGER;
    private final String STATUS;
    private final String PASSWORD;
    private final Integer adminId;

    public NewEmployeeDTO(Integer adminId, String firstname, String lastname, String email, String telno, String employeemanager, String status, String password) {
        this.FIRSTNAME = firstname;
        this.LASTNAME = lastname;
        this.EMAIL = email;
        this.TELNO = telno;
        this.EMPLOYEEMANAGER = employeemanager;
        this.STATUS = status;
        this.PASSWORD = password;
        this.adminId = adminId;
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstname", FIRSTNAME)
                .add("lastname", LASTNAME)
                .add("email", EMAIL)
                .add("employee_manager", EMPLOYEEMANAGER)
                .add("status", STATUS)
                .add("password", PASSWORD)
                .add("telephone", TELNO)
                .toString();
    }

    public Integer getAdminId() {
        return adminId;
    }
}
