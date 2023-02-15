package philani.model;

public class Employee {

    private static int EmployeeID;
    private static String FIRSTNAME;
    private static String LASTNAME;
    private static String EMAIL;
    private static String TELNO;
    private static String EMPLOYEEMANAGER;
    private static String STATUS;
    private static String PASSWORD;

    public static int getEmployeeID() {
        return EmployeeID;
    }

    public static void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public static String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public static void setFIRSTNAME(String FIRSTNAME) {
        Employee.FIRSTNAME = FIRSTNAME;
    }

    public static String getLASTNAME() {
        return LASTNAME;
    }

    public static void setLASTNAME(String LASTNAME) {
        Employee.LASTNAME = LASTNAME;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static void setEMAIL(String EMAIL) {
        Employee.EMAIL = EMAIL;
    }

    public static String getTELNO() {
        return TELNO;
    }

    public static void setTELNO(String TELNO) {
        Employee.TELNO = TELNO;
    }

    public static String getEMPLOYEEMANAGER() {
        return EMPLOYEEMANAGER;
    }

    public static void setEMPLOYEEMANAGER(String EMPLOYEEMANAGER) {
        Employee.EMPLOYEEMANAGER = EMPLOYEEMANAGER;
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public static void setSTATUS(String STATUS) {
        Employee.STATUS = STATUS;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        Employee.PASSWORD = PASSWORD;
    }

}
