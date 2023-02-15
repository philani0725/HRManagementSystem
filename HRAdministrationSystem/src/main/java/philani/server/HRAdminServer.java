package philani.server;

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


public class HRAdminServer {
    private static final String PUBLIC_DIR = "/public";
    private final Javalin appServer;

    public static void main(String[] args) {
        HRAdminServer server = new HRAdminServer();
        server.start(5050);
    }

    public HRAdminServer() {
        appServer = Javalin.create(config -> {
            config.registerPlugin(getConfiguredOpenApiPlugin());
            config.addStaticFiles(PUBLIC_DIR, Location.CLASSPATH);
            config.jsonMapper(createGsonMapper());
        }).routes(configureApi());

    }

    @NotNull
    private EndpointGroup configureApi() {
        return () -> {
            path("people", () -> {
                get(PersonApi::getAll);
                post(PersonApi::create);
                path("/{personId}", () -> get(PersonApi::getOne));
            });
            path("expenses", () -> {
                get(ExpenseApi::getAll);
                post(ExpenseApi::create);
                path("/person/{personId}", () -> get(ExpenseApi::findByPersonId));
                path("/{expenseId}", () -> get(ExpenseApi::getOne));
            });
            path("paymentrequests", () -> {
                get(PaymentRequestApi::getAll);
                post(PaymentRequestApi::create);
                path("/sent/{personId}", () -> get(PaymentRequestApi::findPaymentRequestsSent));
                path("/received/{personId}", () -> get(PaymentRequestApi::findPaymentRequestsReceived));
                path("/{paymentRequestId}", () -> {
                    get(PaymentRequestApi::getOne);
                    delete(PaymentRequestApi::recall);
                });
            });
            path("payments", () -> {
                post(PaymentApi::pay);
                path("/madeby/{personId}", () -> get(PaymentApi::getPaymentsMadeBy));
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
