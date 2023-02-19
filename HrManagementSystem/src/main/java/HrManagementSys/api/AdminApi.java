package HrManagementSys.api;

import HrManagementSys.api.dto.LoginDTO;
import HrManagementSys.model.Admin;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.plugin.openapi.annotations.*;

import java.util.Optional;

public class AdminApi {
    @OpenApi(
            summary = "Find all people that use WeShare",
            operationId = "findAllPeople",
            path = "/people",
            method = HttpMethod.GET,
            tags = {"People"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Admin[].class)})
            })
    public static void getAll(Context ctx) {
        ctx.json(WebService.findAllAdmin());
    }

    @OpenApi(
            summary = "Login to WeShare",
            operationId = "login",
            path = "/people",
            method = HttpMethod.POST,
            tags = {"People"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = LoginDTO.class)}),
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Admin.class)}),
                    @OpenApiResponse(status = "400", description = "Bad email address")
            }
    )
    public static void create(Context ctx) {
        Optional<LoginDTO> loginDTO = ApiHelper.validLogin(ctx);
        if (loginDTO.isPresent()) {
            Admin person = WebService.findAdminByEmailOrCreate(loginDTO.get().getEmail(),loginDTO.get().getPassword());
            ctx.json(WebService.saveAdmin(person));
            ctx.status(HttpCode.OK);
        } else {
            ctx.status(HttpCode.BAD_REQUEST);
        }
    }

    @OpenApi(
            summary = "Find a person by ID",
            operationId = "findPersonById",
            path = "/people/{adminId}",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "adminId", description = "The ID of the person",
                    type = Integer.class, required = true )},
            tags = {"People"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Admin.class)}),
                    @OpenApiResponse(status = "404", description = "Person not found")
            }
    )
    public static void getOne(Context ctx) {
        Admin person = ApiHelper.validAdmin(ctx);
        ctx.json(person);
        ctx.status(HttpCode.OK);
    }
}
