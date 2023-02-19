package HrManagementSys.server;

import HrManagementSys.HRException;
import HrManagementSys.api.AdminApi;
import HrManagementSys.api.DepartmentApi;
import HrManagementSys.api.EmployeeApi;
import HrManagementSys.api.WebService;
import HrManagementSys.model.Admin;
import HrManagementSys.model.Department;
import HrManagementSys.model.Employee;
import HrManagementSys.persistence.AdminDAO;
import HrManagementSys.persistence.DepartmentDAO;
import HrManagementSys.persistence.EmployeeDAO;
import HrManagementSys.persistence.collectionbased.AdminDAOImpl;
import HrManagementSys.persistence.collectionbased.DepartmentDAOImpl;
import HrManagementSys.persistence.collectionbased.EmployeeDAOImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.core.plugin.Plugin;
import io.javalin.http.HttpCode;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.json.JsonMapper;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HRWebServer {
    private static final String PUBLIC_DIR = "/public";
    private final Javalin appServer;

    public static void main(String[] args) {
        HRWebServer server = new HRWebServer();
        try {
            seedDemoData();
        } catch (HRException e) {
            throw new RuntimeException(e);
        }
        server.start(5050);
    }

    public HRWebServer() {
        ServiceRegistry.configure(AdminDAO.class, new AdminDAOImpl());
        ServiceRegistry.configure(EmployeeDAO.class, new EmployeeDAOImpl());
        ServiceRegistry.configure(DepartmentDAO.class, new DepartmentDAOImpl());

        appServer = Javalin.create(config -> {
            config.registerPlugin(getConfiguredOpenApiPlugin());
            config.addStaticFiles(PUBLIC_DIR, Location.CLASSPATH);
            config.jsonMapper(createGsonMapper());
        }).routes(configureApi());

        appServer.exception(HRException.class, (exception, ctx) -> {
            ctx.json(exception.getMessage());
            ctx.status(HttpCode.BAD_REQUEST);
        });
    }

    @NotNull
    private EndpointGroup configureApi() {
        return () -> {
            path("people", () -> {
                get(AdminApi::getAll);
                post(AdminApi::create);
                path("/{personId}", () -> get(AdminApi::getOne));
            });
            path("employees", () -> {
                get(EmployeeApi::getAll);
                post(EmployeeApi::create);
                path("/person/{personId}", () -> get(EmployeeApi::findByAdminId));
                path("/{employeeId}", () -> get(EmployeeApi::getOne));
            });

            path("home", () -> {
                get(EmployeeApi::getAll1);
            });

            path("departments", () -> {
                get(DepartmentApi::getAll);
                post(DepartmentApi::create);
                path("/person/{personId}", () -> get(DepartmentApi::findByAdminId));
                path("/{departmentId}", () -> get(DepartmentApi::getOne));
            });

        };
    }

    private static Plugin getConfiguredOpenApiPlugin() {
        Info info = new Info().version("1.0").description("WeShare API");
        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor("weshare.api")
                .path("/docs/openapi")
                .swagger(new SwaggerOptions("/docs/swagger"))
                .reDoc(new ReDocOptions("/docs/redoc"))
                .defaultDocumentation(doc -> {
                    doc.result("400");
                });
        return new OpenApiPlugin(options);
    }

    private static void seedDemoData() throws HRException {
        Admin student1 = new Admin("hradmin@test.com","TestPass1234");
        for (Admin person : Arrays.asList(student1)) {
            WebService.saveAdmin(person);
        }



        String depName = "Data Science";
        String depName1 = "Software Engineering";

        Employee employee1 = new Employee(student1,"Zolani","Gumede","zolani@gmail.com","085342990","Philani","Active","1212312e");
        Employee employee2 = new Employee(student1,"Zi","mede","ani@gmail.com","085342990","Philani","Active","1212312e");

        Department department1 = new Department(employee1.getAdmin(),employee1.getSTATUS(),employee1.getEMPLOYEEMANAGER(),depName);
        Department department2 = new Department(employee1.getAdmin(),employee1.getSTATUS(),employee1.getEMPLOYEEMANAGER(),depName1);

        for (Employee employee : Arrays.asList(employee1, employee2)) {
            WebService.saveEmployee(employee);
        }

        for (Department department : Arrays.asList(department1, department2)) {
            WebService.saveDepartment(department);
        }

    }

    /**
     * Use GSON for serialisation instead of Jackson
     * because GSON allows for serialisation of objects without noargs constructors.
     *
     * @return A JsonMapper for Javalin
     */
    private static JsonMapper createGsonMapper() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return new JsonMapper() {
            @NotNull
            @Override
            public String toJsonString(@NotNull Object obj) {
                return gson.toJson(obj);
            }

            @NotNull
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Class<T> targetClass) {
                return gson.fromJson(json, targetClass);
            }
        };
    }

    public void start(int port) {
        this.appServer.start(port);
    }

    public void stop() {
        this.appServer.stop();
    }

    public int port() {
        return appServer.port();
    }
}
