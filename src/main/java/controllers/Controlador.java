package controllers;

import spark.Request;

public interface Controlador {
    public void setearModalAlerta(Request request, String titulo, String mensaje, String color);

    public boolean mostrarModalAlerta(Request request);

    public String obtenerTituloModalAlerta(Request request);

    public String obtenerMensajeModalAlerta(Request request);

    public String obtenerColorModalAlerta(Request request);

    public void cancelarModalAlerta(Request request);
}
