package controllers;

import com.google.gson.Gson;
import domain.Categorizacion.Criterio;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.Request;
import spark.Response;
import java.util.List;

public class CriteriosController implements  Controlador{

    public String mostrarCriterios(Request request, Response response){
        int idUsuario = Integer.parseInt(request.queryParams("id_usuario"));
        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidad = usuario.getOrganizacion();
        List<Criterio> criterios = entidad.getCriterios();

        Gson gson = new Gson();
        String jsonCriterios = gson.toJson(criterios);

        response.type("application/json");

        return jsonCriterios;
    }

    public String mostrarSubCriterios(Request request, Response response){
        int idCriterio = Integer.parseInt(request.queryParams("id_criterio"));
        Criterio criterio = FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class).buscar(idCriterio);
        List<Criterio> subCriterios = criterio.getCriteriosHijos();

        Gson gson = new Gson();
        String jsonSubCriterios = gson.toJson(subCriterios);
        response.type("application/json");

        return jsonSubCriterios;
    }



    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_criterios",true);
        request.session().attribute("aviso_modal_criterios", mensaje);
        request.session().attribute("modal_titulo_criterios", titulo);
        request.session().attribute("modal_color_criterios", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_criterios")==null)
            return false;
        return request.session().attribute("alerta_criterios");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_criterios");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_criterios");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_criterios");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_criterios",false);
    }
}
