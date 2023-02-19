package HrManagementSys.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Department extends PersistentModel<Department> {

    private final Admin admin;



    public String getStatus() {
        return status;
    }

    public String getManager() {
        return manager;
    }

    private final String status;
    private final String manager;

    public String getName() {
        return NAME;
    }

    private final String NAME;

    public Department(Admin admin, String status, String manager1, String name) {
        this.admin = admin;
        this.status = status;
        this.manager = manager1;
        this.NAME = name;
    }


    public Admin getAdmin() {
        return admin;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return Objects.equal(id, department.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Manager", manager)
                .add("status", status)
                .add("admin", admin)
                .add("Name", NAME)

                .toString();
    }
}
