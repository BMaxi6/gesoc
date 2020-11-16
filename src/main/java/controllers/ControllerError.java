package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ControllerError {
    public static ModelAndView mostrarErrorInesperado(Request request, Response response){
        return new ModelAndView(null, "error.hbs");
    }
    public static ModelAndView mostrarBloqueoLogin(Request request, Response response){
        boolean bloqueo_logueo=true;
        HashMap<String, Object> parametros= new HashMap<>();
        parametros.put("bloqueo_logueo", bloqueo_logueo);
        return new ModelAndView(parametros, "error.hbs");
    }
}
