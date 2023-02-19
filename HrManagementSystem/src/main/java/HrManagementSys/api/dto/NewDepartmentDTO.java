package HrManagementSys.api.dto;

import com.google.common.base.MoreObjects;

public class NewDepartmentDTO {


    private final String status;
    private final String manager;
    private final String NAME;
    private final Integer adminId;

    public NewDepartmentDTO(String status, String manager, String name, Integer adminId) {
        this.status = status;
        this.manager = manager;
        this.NAME = name;
        this.adminId = adminId;
    }


    public String getStatus() {
        return status;
    }

    public String getManager() {
        return manager;
    }

    public String getNAME() {
        return NAME;
    }



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name", NAME)
                .add("status", status)
                .add("manager", manager)

                .toString();
    }

    public Integer getAdminId() {
        return adminId;
    }
}

