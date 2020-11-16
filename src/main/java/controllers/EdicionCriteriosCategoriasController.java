package controllers;

import domain.Categorizacion.Criterio;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class EdicionCriteriosCategoriasController {
    public ModelAndView mostrarEdicionCriteriosCategorias (Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);

        return new ModelAndView(parametros, "edicion_criterios_categorias.hbs");
    }


}
