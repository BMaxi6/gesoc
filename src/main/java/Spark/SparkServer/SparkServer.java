package Spark.SparkServer;

import domain.validadorDeTransparencia.AsistentePlanificacionValidadorTransparencia;
import spark.Spark;
import spark.debug.DebugScreen;

public class SparkServer {

    public static void main(String[] args) {
        //AsistentePlanificacionValidadorTransparencia.instancia();
        Spark.port(getHerokuAssignedPort());
        SparkRouter.init();
        DebugScreen.enableDebugScreen();
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
