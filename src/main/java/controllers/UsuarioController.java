package controllers;

import AsociadorEgresoIngreso.ActionProvider;
import AsociadorEgresoIngreso.Asociador;
import Scheduler.Dia;
import Scheduler.Periodo;
import Scheduler.Scheduler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.DTO.DiasJSON;
import controllers.DTO.PersonasJson;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadorContrasenias.ValidadorDeContrasenias;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

public class UsuarioController implements Controlador{
    public Asociador asociador = new Asociador();

    public ModelAndView mostrarPerfil(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");


        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("organizacion", entidadUsuario);

        AsistenteDeModales.configurarParametros(this,request,parametros);

        return new ModelAndView(parametros, "mi_perfil.hbs");
    }
    public Response cambiarContrasenia(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Repositorio<Usuario> repoUsuarios= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class);
        Usuario usuario= repoUsuarios.buscar(idUsuario);
        try {

            String contraseniaNueva=request.queryParams("contrasenia_nueva");
            String contraseniaNuevaConfirmacion=request.queryParams("contrasenia_nueva_confirmacion");
            String contraseniaAnterior=request.queryParams("contrasenia_anterior");
            if(Objects.equals(contraseniaNueva, contraseniaNuevaConfirmacion)){

                if(usuario.contraseniaCorrecta(contraseniaAnterior)){

                    if(ValidadorDeContrasenias.instancia().esContraseniaSegura(contraseniaNueva)){
                        usuario.setContrasenia(contraseniaNueva);
                        repoUsuarios.modificar(usuario);
                        this.setearModalAlerta(request, "Cambio de contraseña exitoso", "Se ha cambiado su contraseña.", AsistenteDeColores.getExito());
                    }else{
                        this.setearModalAlerta(request, "Error en el cambio de contraseña", "La contraseña ingresada no es segura. Reintente con otra contraseña.", AsistenteDeColores.getError());
                    }



                    response.redirect(new Formatter().format("/gesoc/home/perfil", usuario.getId()).toString());
                }else{
                    this.setearModalAlerta(request, "Error en el cambio de contraseña", "Ingresó mal la contraseña anterior.", AsistenteDeColores.getError());
                    response.redirect(new Formatter().format("/gesoc/home/perfil", usuario.getId()).toString());//falta avisar que ingreso mal la contraseña anterior
                }
            }else{
                this.setearModalAlerta(request, "Error en el cambio de contraseña", "Las contraseñas ingresadas no coinciden.", AsistenteDeColores.getError());
                response.redirect(new Formatter().format("/gesoc/home/perfil", usuario.getId()).toString());//falta avisar que ignreso mal la confirmacion de la nueva contrasepña y agregar validaciones de contraseña segura
            }
        }catch(Exception e){
            this.setearModalAlerta(request, "Error en el cambio de contraseña", "Revise los datos ingresados.", AsistenteDeColores.getError());
            response.redirect(new Formatter().format("/gesoc/home/perfil", usuario.getId()).toString());
        }

        return response;
    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_perfil",true);
        request.session().attribute("aviso_modal_perfil", mensaje);
        request.session().attribute("modal_titulo_perfil", titulo);
        request.session().attribute("modal_color_perfil", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_perfil")==null)
            return false;
        return request.session().attribute("alerta_perfil");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_perfil");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_perfil");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_perfil");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_perfil",false);
    }

    public ModelAndView vincularOperaciones(Request request, Response response) throws IOException {
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        HashMap<Integer, String> criterios = ActionProvider.obtenerCriteriosDeAsociacion().invocar();
        List<String> criteriosHbs = new ArrayList<String>();
        for(int i = 0; i < criterios.size(); i++){
            criteriosHbs.add(criterios.get(i+1));
        }
        parametros.put("criterios", criterios);
        return new ModelAndView(parametros, "vinculo_operaciones.hbs");
    }

    public Response modificarCriterioPermanente(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        int idCriterio = Integer.parseInt(request.queryParams("criterioPerm"));
        asociador.setIdCriterio(idCriterio);
        this.setearModalAlerta(request, "Criterio modificado exitosamente", "Se ha modificado el criterio de selección exitosamente", AsistenteDeColores.getExito());
        response.redirect("/gesoc/vinculo_operaciones");
        return response;
    }

    public ModelAndView modificarHoraVinculo(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        List <String> tipoPlazo = new ArrayList<String>();
        int i;

        for(i=0; i<Periodo.values().length; i++){
            tipoPlazo.add(Periodo.values()[i].toString());
        }
        parametros.put("tipoPlazo", tipoPlazo);

        List<Integer> horarios = new ArrayList<Integer>();
        for(i=0; i<24; i++){
            horarios.add(i);
        }
        parametros.put("horarios", horarios);
        List<String> diasPlantilla = new ArrayList<String>();
        for(i=0; i<Dia.values().length; i++){
            diasPlantilla.add(Dia.values()[i].toString());
        }
        parametros.put("dias", diasPlantilla);

        return new ModelAndView(parametros, "hora_vinculo.hbs");
    }

    public Response modificarFechaVinculo(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(request.queryParams("fecha"));
        } catch (Exception e){
            fecha = LocalDate.now();
        }
        int tipoPlazo = Integer.parseInt(request.queryParams("tipo_plazo"));
        Periodo periodo = Periodo.values()[tipoPlazo];
        int horario = Integer.parseInt(request.queryParams("horario"));
        List<Dia> dias = new ArrayList<Dia>();
        List<DiasJSON> diasNumero = new ArrayList<DiasJSON>();
        Type listType = new TypeToken<ArrayList<DiasJSON>>(){}.getType();
        diasNumero = new Gson().fromJson(request.queryParams("dias"), listType);
        if(diasNumero.size() == 0){
            this.setearModalAlerta(request, "No se pudo vincular", "Al menos debes seleccionar un día para realizar el vinculo", AsistenteDeColores.getError());
            response.redirect("/gesoc/hora_vinculo");
            return response;
        }
        for(int i=0; i<diasNumero.size(); i++){
            dias.add(Dia.values()[diasNumero.get(i).numero]);
        }
        Date elegida = new Date(fecha.getYear(), fecha.getMonthValue()-1, fecha.getDayOfMonth(), horario, 00);
        //new Scheduler(periodo, dias, asociador, elegida); --- JOYA
        this.setearModalAlerta(request, "Planificación modificada exitosamente", "Ahora la vinculación comienza el " + fecha.toString() + " y se realizara " + periodo.toString() , AsistenteDeColores.getExito());
        response.redirect("/gesoc/hora_vinculo");
        return response;
    }

    public Response vinculoRealizado(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        int idCriterio = Integer.parseInt(request.queryParams("criterioDef"));
        asociador.setIdOrganizacion(entidadUsuario.getId());
        asociador.setIdCriterio(idCriterio);
        boolean conexion = asociador.ejecucionManual(idCriterio);
        if(conexion) {
            this.setearModalAlerta(request, "Operaciones vinculadas exitosamente", "Se han vinculado las operaciones correctamente", AsistenteDeColores.getExito());
            response.redirect("/gesoc/vinculo_operaciones");
        } else {
            this.setearModalAlerta(request, "Error al vincular operaciones", "Hubo un error en la conexion con el servidor. Reintente mas tarde.", AsistenteDeColores.getError());
            response.redirect("/gesoc/vinculo_operaciones");
        }
        return response;
    }
}
