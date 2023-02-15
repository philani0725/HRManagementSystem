package philani.model;

public class Mananger {

    private int ManagerID;
    private int EmployeeID;

    public Mananger(int managerID, int employeeID) {
        ManagerID = managerID;
        EmployeeID = employeeID;
    }

    public int getManagerID() {
        return ManagerID;
    }

    public void setManagerID(int managerID) {
        ManagerID = managerID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

}
