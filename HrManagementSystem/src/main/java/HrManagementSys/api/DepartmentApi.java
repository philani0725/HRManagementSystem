package HrManagementSys.api;

import HrManagementSys.api.dto.DepartmentDTO;
import HrManagementSys.api.dto.EmployeeDTO;
import HrManagementSys.api.dto.NewDepartmentDTO;
import HrManagementSys.api.dto.NewEmployeeDTO;
import HrManagementSys.model.Admin;
import HrManagementSys.model.Department;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.plugin.openapi.annotations.*;

import java.util.Collection;
import java.util.List;

public class DepartmentApi {
    @OpenApi(
            summary = "Create a new department for a person",
            tags = {"Departments"},
            operationId = "createEmployee",
            path = "/departments",
            method = HttpMethod.POST,
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = NewEmployeeDTO.class)}),
            responses = {
                    @OpenApiResponse(status = "201", content = {@OpenApiContent(from = EmployeeDTO.class)}),
                    @OpenApiResponse(status = "404", description = "Person not found")
            }
    )
    public static void create(Context ctx) {
        NewDepartmentDTO unsaved = ApiHelper.validNewDepartmentDTO(ctx);
        DepartmentDTO saved = WebService.createNewEmployee(unsaved);
        ctx.json(saved);
        ctx.status(HttpCode.CREATED);
    }

    @OpenApi(
            summary = "Find all departments",
            operationId = "findAllDepartments",
            path = "/departments",
            method = HttpMethod.GET,
            tags = {"Departments"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO[].class)})
            })    public static void getAll(Context ctx) {
        ctx.json(mapDepartments(WebService.findAllDepartments()));
        ctx.status(HttpCode.OK);
    }
    @OpenApi(
            summary = "Find an department by ID",
            operationId = "findEmployeeById",
            path = "/departments/{departmentId}",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "departmentId", description = "The department ID",
                    type = Integer.class, required = true)},
            tags = {"Departments"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO.class)}),
                    @OpenApiResponse(status = "404", description = "Department not found")
            }
    )
    public static void getOne(Context ctx) {
        Department department = ApiHelper.validDepartment(ctx);
        ctx.json(DepartmentDTO.fromDepartment(department));
        ctx.status(HttpCode.OK);
    }

    @OpenApi(
            summary = "Find departments for a person",
            operationId = "findDepartmentsByPerson",
            path = "/departments/person/{departmentId}",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "departmentId",
                    type = Integer.class,
                    description = "The ID of the person",
                    required = true)},
            tags = {"Departments"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO[].class)})
            })
    public static void findByAdminId(Context ctx) {
        Admin person = ApiHelper.validAdmin(ctx);
        Collection<Department> departments = WebService.findDepartmentsForAdmin(person.getId());
        ctx.json(mapDepartments(departments));
        ctx.status(HttpCode.OK);
    }

    private static List<DepartmentDTO> mapDepartments(Collection<Department> all) {
        return all.stream().map(DepartmentDTO::fromDepartment).toList();
    }
}
