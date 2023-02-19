package HrManagementSys.api;

import HrManagementSys.api.dto.LoginDTO;
import HrManagementSys.api.dto.NewDepartmentDTO;
import HrManagementSys.api.dto.NewEmployeeDTO;
import HrManagementSys.model.Admin;
import HrManagementSys.model.Department;
import HrManagementSys.model.Employee;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Optional;

public class ApiHelper {
    public static Integer validPathParamId(Context ctx, String param) {
        return ctx.pathParamAsClass(param, Integer.class)
                .check(id -> id > 0, "ID must be greater than 0 ").get();
    }

    public static Integer validEmployeeId(Context ctx) {
        return validPathParamId(ctx, "employeeId");
    }
    public static Integer validDepartmentId(Context ctx) {
        return validPathParamId(ctx, "departmentId");
    }

    public static Integer validAdminId(Context ctx) {
        return validPathParamId(ctx, "personId");
    }

    
    public static Optional<LoginDTO> validLogin(Context ctx) {
        LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
        return EmailValidator.getInstance().isValid(loginDTO.getEmail())
                ? Optional.of(loginDTO) : Optional.empty();
    }

    public static NewEmployeeDTO validNewEmployeeDTO(Context ctx) {
        NewEmployeeDTO newExpenseDTO = ctx.bodyAsClass(NewEmployeeDTO.class);
        verifyAdminExists(newExpenseDTO.getAdminId());
        return newExpenseDTO;
    }

    public static NewDepartmentDTO validNewDepartmentDTO(Context ctx) {
        NewDepartmentDTO newDepartmentDTO = ctx.bodyAsClass(NewDepartmentDTO.class);
        verifyAdminExists(newDepartmentDTO.getAdminId());
        return newDepartmentDTO;
    }

    private static Admin verifyAdminExists(Integer personId) {
        return WebService.getAdmin(personId)
                    .orElseThrow(() -> new NotFoundResponse("Admin not found: " + personId));
    }

    private static Employee verifyEmployeeExists(Integer expenseId) {
        return WebService.getEmployee(expenseId)
                .orElseThrow(() -> new NotFoundResponse("Employee not found: " + expenseId));
    }

    private static Department verifyEDepartmentExists(Integer departmentId) {
        return WebService.getDepartment(departmentId)
                .orElseThrow(() -> new NotFoundResponse("Department not found: " + departmentId));
    }


    public static Admin validAdmin(Context ctx) {
        int id = validAdminId(ctx);
        return verifyAdminExists(id);
    }

    public static Employee validEmployee(Context ctx) {
        int id = validEmployeeId(ctx);
        return verifyEmployeeExists(id);
    }

    public static Department validDepartment(Context ctx) {
        int id = validDepartmentId(ctx);
        return verifyEDepartmentExists(id);
    }


}
