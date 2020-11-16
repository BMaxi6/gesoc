package middlewares;
import spark.Request;
import spark.Response;
import spark.Spark;

public class MiddleWare {
    private static MiddleWare instancia=null;

    public Response verificarSesion(Request request, Response response){
        boolean authenticated = request.session().attribute("usuario_id") != null;

        if (!authenticated) {

            //Spark.halt(401, "Debes loguearte para acceder a esta página");
            response.redirect("/error_logueo");

        }
        return response;
    }

    public static MiddleWare instancia(){
        if(instancia==null)
            instancia=new MiddleWare();
        return instancia;
    }
}
