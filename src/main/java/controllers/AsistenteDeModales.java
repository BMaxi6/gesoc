package controllers;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
public class AsistenteDeModales {
    public static void configurarParametros(Controlador controlador,Request request, Map<String, Object> parametros){
        if (controlador.mostrarModalAlerta(request)){
            boolean mostrarModal=true;
            parametros.put("mostrar_modal", mostrarModal);
            parametros.put("aviso_modal", controlador.obtenerMensajeModalAlerta(request));
            parametros.put("modal_titulo", controlador.obtenerTituloModalAlerta(request));
            parametros.put("color_modal", controlador.obtenerColorModalAlerta(request));
            controlador.cancelarModalAlerta(request);
        }
    }
}
