package HrManagementSys.api;

import HrManagementSys.api.dto.EmployeeDTO;
import HrManagementSys.api.dto.NewEmployeeDTO;
import HrManagementSys.model.Admin;
import HrManagementSys.model.Employee;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.plugin.openapi.annotations.*;

import java.util.Collection;
import java.util.List;

public class EmployeeApi {

    @OpenApi(
            summary = "Create a new employee for a person",
            tags = {"Employees"},
            operationId = "createEmployee",
            path = "/employees",
            method = HttpMethod.POST,
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = NewEmployeeDTO.class)}),
            responses = {
                    @OpenApiResponse(status = "201", content = {@OpenApiContent(from = EmployeeDTO.class)}),
                    @OpenApiResponse(status = "404", description = "Person not found")
            }
    )
    public static void create(Context ctx) {
        NewEmployeeDTO unsaved = ApiHelper.validNewEmployeeDTO(ctx);
        EmployeeDTO saved = WebService.createNewEmployee(unsaved);
        ctx.json(saved);
        ctx.status(HttpCode.CREATED);
    }

    @OpenApi(
            summary = "Find all employees",
            operationId = "findAllEmployees",
            path = "/employees",
            method = HttpMethod.GET,
            tags = {"Employees"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO[].class)})
            })    public static void getAll(Context ctx) {
        ctx.json(mapEmployees(WebService.findAllEmployees()));
        ctx.status(HttpCode.OK);
    }

    @OpenApi(
            summary = "Find all employees",
            operationId = "findAllEmployees",
            path = "/home",
            method = HttpMethod.GET,
            tags = {"Employees"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO[].class)})
            })
    public static void getAll1(Context ctx) {
        ctx.json(mapEmployees(WebService.findAllEmployees()));
        ctx.status(HttpCode.OK);
    }
    @OpenApi(
            summary = "Find an employee by ID",
            operationId = "findEmployeeById",
            path = "/employees/{employeeId}",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "employeeId", description = "The employee ID",
                    type = Integer.class, required = true)},
            tags = {"Employees"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO.class)}),
                    @OpenApiResponse(status = "404", description = "Employee not found")
            }
    )
    public static void getOne(Context ctx) {
        Employee employee = ApiHelper.validEmployee(ctx);
        ctx.json(EmployeeDTO.fromEmployee(employee));
        ctx.status(HttpCode.OK);
    }

    @OpenApi(
            summary = "Find employees for a person",
            operationId = "findEmployeesByPerson",
            path = "/employees/person/{employeeId}",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "employeeId",
                    type = Integer.class,
                    description = "The ID of the person",
                    required = true)},
            tags = {"Employees"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = EmployeeDTO[].class)})
            })
    public static void findByAdminId(Context ctx) {
        Admin person = ApiHelper.validAdmin(ctx);
        Collection<Employee> employees = WebService.findEmployeesForAdmin(person.getId());
        ctx.json(mapEmployees(employees));
        ctx.status(HttpCode.OK);
    }

    private static List<EmployeeDTO> mapEmployees(Collection<Employee> all) {
        return all.stream().map(EmployeeDTO::fromEmployee).toList();
    }
}
