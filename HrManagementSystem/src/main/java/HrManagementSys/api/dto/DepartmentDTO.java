package HrManagementSys.api.dto;

import HrManagementSys.model.Department;
import com.google.common.base.MoreObjects;


public class DepartmentDTO {
    private final int DepartmentID;
    private final String status;
    private final String manager;
    private final  String NAME;

    public DepartmentDTO(int departmentID, String status, String manager, String name) {
        this.DepartmentID = departmentID;
        this.status = status;
        this.manager = manager;
        this.NAME = name;
    }

    public static DepartmentDTO fromDepartment(Department department){
        return new DepartmentDTO(
                department.getId(),
                department.getStatus(),
                department.getManager(),
                department.getName());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("DepartmentID", DepartmentID)
                .add("Manager", manager)
                .add("STATUS", status)
                .add("Name", NAME)

                .toString();
    }
}
