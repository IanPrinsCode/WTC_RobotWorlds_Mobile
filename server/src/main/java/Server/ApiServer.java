package Server;

import Handlers.ApiClientHandler;
import World.World;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.swagger.v3.oas.models.info.Info;
import io.javalin.plugin.openapi.ui.SwaggerOptions;

public class ApiServer {
    private final Javalin server;
    private final int port;

    public ApiServer(World world, int port){
        this.port = port;
        this.server = Javalin.create(config -> {
            config.showJavalinBanner = false;
            config.enableCorsForAllOrigins();
            config.defaultContentType = "application/json";
            config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
            config.autogenerateEtags = true;
        });
        this.server.get("/world", context -> ApiClientHandler.returnWorld(context, world));
        this.server.get("/admin/worlds", context -> ApiClientHandler.getAllWorlds(context, world));
        this.server.get("/world/{name}", context -> ApiClientHandler.restoreWorld(context, world));
        this.server.get("/admin/load/{name}", context -> ApiClientHandler.restoreWorld(context, world));
        this.server.get("/admin/robots", context -> ApiClientHandler.getRobots(context, world));
        this.server.get("/admin/obstacles/", context -> ApiClientHandler.getAllObstacles(context, world));
        this.server.post("/robot/{name}", context -> ApiClientHandler.doCommand(context, world));
        this.server.post("/admin/save/{world-name}", context -> ApiClientHandler.saveWorld(context, world));
        this.server.post("/admin/obstacles/", context -> ApiClientHandler.addObstacles(context, world));
        this.server.delete("/admin/obstacles/", context -> ApiClientHandler.removeObstacles(context, world));
        this.server.delete("/admin/robot/{name}", context -> ApiClientHandler.removeRobot(context, world));
    }

    public void start() {this.server.start(port);}

    public void stop() {
        this.server.stop();
    }

    private OpenApiOptions getOpenApiOptions() {
        Info info = new Info().version("1.0").description("RobotWorlds API");
        return new OpenApiOptions(info)
                .activateAnnotationScanningFor("io.javalin.example.java")
                .path("/swagger-docs") // endpoint for OpenAPI json
                .swagger(new SwaggerOptions("/swagger-ui"));}
}
