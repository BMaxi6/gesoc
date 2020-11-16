package controllers;

import bandejaDeMensajes.Mensaje;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerPruebaPhp {
    public ModelAndView mostrarPhp (Request request, Response response){

        return new ModelAndView(null, "/index.hbs");
    }
}
