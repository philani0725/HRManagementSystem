package philani.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tables {

    private final Connection connection;

    /**
     * Create an instance of the Tables object using the provided database connection
     * @param connection The JDBC connection to use
     */

    public Tables(Connection connection) {
        this.connection = connection;
    }

    protected boolean createTable(String sql){
        if (sql.contains("CREATE TABLE")){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute();
                return true;
            } catch (SQLException error){
                throw new RuntimeException(error);
            }
        }
        return false;
    }

    public boolean createEmployee(){
        String sql = "CREATE TABLE IF NOT EXISTS Employee (\n" +
                "    EmployeeID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    FIRSTNAME varchar (50) NOT NULL,\n" +
                "    LASTNAME varchar (50) NOT NULL,\n" +
                "    EMAIL varchar(50) NOT NULL,\n" +
                "    TELNO varchar (20) NOT NULL,\n" +
                "    EMPLOYEEMANAGER varchar (50) NOT NULL,\n" +
                "    STATUS varchar(20) NOT NULL,\n" +
                "    PASSWORD varchar(50) NOT NULL\n" +
                ")";

        return  createTable(sql);
    }

    public boolean createDepartment(){
        String sql = "CREATE TABLE IF NOT EXISTS Department (\n" +
                "    NAME varchar (50) NOT NULL,\n" +
                "    STATUS NOT NULL,\n" +
                "    EmployeeID NOT NULL,\n" +
                "    FOREIGN KEY (STATUS) REFERENCES Employee(STATUS),\n" +
                "    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),\n" +
                "    PRIMARY KEY (STATUS,EmployeeID)\n" +
                ")";

        return createTable(sql);
    }

    public boolean createManager(){
        String sql = "CREATE TABLE IF NOT EXISTS Manager (\n" +
                "    ManagerID Integer Primary Key NOT NULL,\n" +
                "    EmployeeID NOT NULL,\n" +
                "    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)\n" +
                ")";
        return createTable(sql);
    }

    public static void main(String[] args) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        Tables tables = new Tables(databaseManager.getConnection());
        System.out.println(tables.createEmployee());
        System.out.println(tables.createDepartment());
        System.out.println(tables.createManager());
    }
}
