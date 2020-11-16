package controllers;

import DTOs.EgresoDto;
import DTOs.EgresoDtoFechaString;
import DTOs.PresupuestoDtoPorCategoria;
import com.google.gson.Gson;
import domain.Categorizacion.Categoria;
import domain.Categorizacion.Criterio;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriasController implements Controlador{

    public ModelAndView mostrarEdicionCriteriosCategorias (Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        AsistenteDeModales.configurarParametros(this,request,parametros);
        parametros.put("usuario", usuario);

        return new ModelAndView(parametros, "edicion_criterios_categorias.hbs");
    }

    public ModelAndView mostrarListado (Request request, Response response){

        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        List<Criterio> criteriosOrganizacion = entidadUsuario.getCriterios();
        System.out.println("------------------------criterios");
        System.out.println(entidadUsuario.getCriterios().size());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("criterios_organizacion", criteriosOrganizacion);

        return new ModelAndView(parametros, "listado_criterios_categorias.hbs");
    }

    public ModelAndView mostrarPresupuestosCategoria (Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);

        return new ModelAndView(parametros, "presupuestos_por_categoria.hbs");
    }

    public ModelAndView mostrarEgresosCategoria (Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        List<Criterio> criteriosOrganizacion = entidadUsuario.getCriterios();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("criteriosOrganizacion", criteriosOrganizacion);

        return new ModelAndView(parametros, "egresos_por_categoria.hbs");
    }

    public ModelAndView mostrarListadoConCategorias (Request request, Response response){

        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();
        Repositorio<Criterio> repoCriterios=FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class);

        List<Criterio> criteriosOrganizacion = entidadUsuario.getCriterios();
        System.out.println("------------------------criterios");
        System.out.println(entidadUsuario.getCriterios().size());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("criterios_organizacion", criteriosOrganizacion);

        Criterio criterio;
        List<Categoria> categorias;
        int idCriterio;
        try{
            idCriterio=Integer.parseInt(request.queryParams("criterio"));
            criterio= FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class).buscar(idCriterio);
            categorias=criterio.getCategorias();

        }catch(Exception e){
            return new ModelAndView(parametros, "listado_criterios_categorias.hbs");
        }
        parametros.put("categorias_del_criterio",categorias);
        parametros.put("criterio_seleccionado",categorias);
        return new ModelAndView(parametros, "listado_criterios_categorias.hbs");
    }

    public String mostrarCategorias(Request request, Response response){
        int idCriterio = Integer.parseInt(request.queryParams("id_criterio"));
        Criterio criterio = FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class).buscar(idCriterio);
        List<Categoria> categorias = criterio.getCategorias();
        Gson gson = new Gson();
        String jsonCategorias = gson.toJson(categorias);
        response.type("application/json");
        return jsonCategorias;
    }

    public String obtenerEgresosCategoria(Request request, Response response){
        int idCategoria = Integer.parseInt(request.queryParams("id_categoria"));
        int idUsuario = Integer.parseInt(request.queryParams("id_usuario"));
        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();

        Categoria categoria = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscar(idCategoria);

        List<OperacionDeEgreso> egresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos();
        List<OperacionDeEgreso> egresosOrganizacion = egresos.stream().
                filter(egreso -> egreso.getOrganizacion().getId() == entidadUsuario.getId()).
                filter(egreso -> egreso.getCategorias().contains(categoria)).collect(Collectors.toList());

        List<EgresoDtoFechaString> egresosDto = egresosOrganizacion.stream().map(e -> EgresoDtoFechaString.toEgresoDtoFechaString(e)).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonEgresos = gson.toJson(egresosDto);
        response.type("application/json");
        return jsonEgresos;
    }

    public String obtenerPresupuestosCategoria(Request request, Response response){
        int idCategoria = Integer.parseInt(request.queryParams("id_categoria"));
        int idUsuario = Integer.parseInt(request.queryParams("id_usuario"));
        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();

        Categoria categoria = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscar(idCategoria);

        List<Presupuesto> presupuestos = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscarTodos();
        List<Presupuesto> presupuestosOrganizacion = presupuestos.stream().
                filter(presupuesto -> presupuesto.getOrganizacion().getId() == entidadUsuario.getId()).
                filter(presupuesto -> presupuesto.getCategorias().contains(categoria)).collect(Collectors.toList());

        List<PresupuestoDtoPorCategoria> presupuestosDto = presupuestosOrganizacion.stream().map(e -> PresupuestoDtoPorCategoria.toPresupuestoDtoPorCategoria(e)).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonPresupuestos = gson.toJson(presupuestosDto);
        response.type("application/json");
        return jsonPresupuestos;
    }

    public Response nuevaCategoria(Request request, Response response){
        System.out.println("LLEGUE");
        String nombreCategoria;
        int idCriterio;
        Repositorio<Criterio> repoCriterios= FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class);
        Repositorio<Categoria> repoCategorias = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class);

        try{
            idCriterio = Integer.parseInt(request.queryParams("nombre_criterio"));
            nombreCategoria = request.queryParams("nombre_categoria");
            Categoria categoria =  new Categoria();
            categoria.setNombre(nombreCategoria);
            Criterio criterio = repoCriterios.buscar(idCriterio);
            repoCategorias.agregar(categoria);
            criterio.agregarCategoria(categoria);
            repoCriterios.modificar(criterio);

        }catch (Exception e){
            this.setearModalAlerta(request, "Error en la creación de la categoría", "Ha ocurrido un error inesperado a la hora de crear la categoría. Vuelva a intentarlo", AsistenteDeColores.getError());
            response.redirect("/gesoc/edicion_criterios_categorias");
            return response;
        }

        this.setearModalAlerta(request, "Éxito en la creación de la categoría", "Se ha creado la categoría con éxito.", AsistenteDeColores.getExito());
        response.redirect("/gesoc/edicion_criterios_categorias");

        return response;

    }

    public Response nuevoCriterio(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica org = usuario.getOrganizacion();
        String nombreCriterio;
        Repositorio<Criterio> repoCriterios= FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class);
        Repositorio<EntidadJuridica> repoOrgs= FactoryRepositorio.instancia().obtenerRepositorio(EntidadJuridica.class);
        try{
            nombreCriterio=request.queryParams("nombre_criterio");
            Criterio criterio=  new Criterio();
            criterio.setNombre(nombreCriterio);
            repoCriterios.agregar(criterio);
            org.agregarCriterio(criterio);
            repoOrgs.modificar(org);

        }catch (Exception e){
            this.setearModalAlerta(request, "Error en la creación del criterio", "Ha ocurrido un error inesperado a la hora de crear el criterio.", AsistenteDeColores.getError());
            response.redirect("/gesoc/edicion_criterios_categorias");
            return response;
        }

        this.setearModalAlerta(request, "Éxito en la creación del criterio", "Se ha creado el criterio con éxito.", AsistenteDeColores.getExito());
        response.redirect("/gesoc/edicion_criterios_categorias");

        return response;

    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_carga_criterio",true);
        request.session().attribute("aviso_modal_carga_criterio", mensaje);
        request.session().attribute("modal_titulo_carga_criterio", titulo);
        request.session().attribute("modal_color_carga_criterio", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_carga_criterio")==null)
            return false;
        return request.session().attribute("alerta_carga_criterio");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_carga_criterio");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_carga_criterio");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_carga_criterio");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_carga_criterio",false);
    }
}
