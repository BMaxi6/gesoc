package controllers;

import bandejaDeMensajes.Mensaje;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BandejaDeMensajesController {
    public ModelAndView mostrarBandeja (Request request, Response response){

        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();
        //List<Mensaje> mensajes = usuario.getBandejaDeMensajes().getMensajes().stream().filter(m->!m.isBajaLogica()).collect(Collectors.toList());
        //mensajes=  new ArrayList<>(new HashSet<>(mensajes));
        List<Mensaje> mensajes = FactoryRepositorio.instancia().obtenerRepositorio(Mensaje.class).buscarTodos().stream().filter(mensaje -> usuario.getBandejaDeMensajes().getMensajes().contains(mensaje) && !mensaje.isBajaLogica()).collect(Collectors.toList());
        //busco los mensajes asi para que el EM no se vuelva loco
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("mensajes", mensajes);

        return new ModelAndView(parametros, "/bandeja_de_mensajes.hbs");
    }

    public ModelAndView eliminarMensaje(Request request, Response response){
        int idMensaje= Integer.parseInt(request.queryParams("id_mensaje"));
        Repositorio<Mensaje> repoMensajes= FactoryRepositorio.instancia().obtenerRepositorio(Mensaje.class);
        Mensaje msg= repoMensajes.buscar(idMensaje);
        msg.invalidar();
        repoMensajes.modificar(msg);

        response.redirect("/gesoc/bandeja_de_mensajes");

        return mostrarBandeja(request, response);
    }

    public ModelAndView marcarLeido(Request request, Response response){
        int idMensaje= Integer.parseInt(request.queryParams("id_mensaje"));
        Repositorio<Mensaje> repoMensajes= FactoryRepositorio.instancia().obtenerRepositorio(Mensaje.class);
        Mensaje msg= repoMensajes.buscar(idMensaje);
        msg.setFechaLeido(LocalDate.now());
        repoMensajes.modificar(msg);

        response.redirect("/gesoc/bandeja_de_mensajes");
        return mostrarBandeja(request,response);
    }

    public ModelAndView filtrarMensajes(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        List<Mensaje> mensajes = FactoryRepositorio.instancia().obtenerRepositorio(Mensaje.class).buscarTodos().stream().filter(mensaje -> usuario.getBandejaDeMensajes().getMensajes().contains(mensaje) && !mensaje.isBajaLogica()).collect(Collectors.toList());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);


        List<Mensaje> mensajesFiltrados;
        LocalDate fechaInicio, fechaFinal;
        String criterioLeido;
        try{
            criterioLeido= request.queryParams("criterio_leido");
            switch(criterioLeido){
                case "LEIDOS":
                    mensajesFiltrados=mensajes.stream().filter(m->m.fueLeido()).collect(Collectors.toList());
                    break;
                case "NO_LEIDOS":
                    mensajesFiltrados=mensajes.stream().filter(m->!m.fueLeido()).collect(Collectors.toList());
                    break;
                default :
                    mensajesFiltrados=mensajes;
                    break;
            }
            fechaInicio= LocalDate.parse(request.queryParams("fecha_inicio"));
            fechaFinal= LocalDate.parse(request.queryParams("fecha_final"));

            mensajesFiltrados=mensajesFiltrados.stream().filter(m->m.estaEntreFecha(fechaInicio, fechaFinal)).collect(Collectors.toList());

        }catch(Exception e){
            mensajesFiltrados=mensajes;
        }

        parametros.put("mensajes", mensajesFiltrados);


        return new ModelAndView(parametros, "/bandeja_de_mensajes.hbs");
    }
}
