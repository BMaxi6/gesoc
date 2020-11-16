package Spark.SparkServer;

import domain.validadorDeTransparencia.AsistentePlanificacionValidadorTransparencia;
import spark.Spark;
import spark.debug.DebugScreen;

public class SparkServer {

    public static void main(String[] args) {
        AsistentePlanificacionValidadorTransparencia.instancia();
        Spark.port(9187);
        SparkRouter.init();
        DebugScreen.enableDebugScreen();
    }
}
