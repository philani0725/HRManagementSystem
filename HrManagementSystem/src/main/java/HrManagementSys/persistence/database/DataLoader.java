package HrManagementSys.persistence.database;
import java.sql.Connection;

public class DataLoader {

    private final Connection connection;

//    public Map<String, Department> getStrinDepartmentMap() {
//        return strinDepartmentMap;
//    }
//
//    public List<Admin> getAdmins() {
//        return admin;
//    }
//
//    public  Map<String, Department> stringDepartmentMap = new HashMap<>();
//
    public DataLoader(Connection connection) {
        this.connection = connection;
    }
}
