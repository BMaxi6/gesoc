package controllers;

import domain.validadorDeTransparencia.AsistentePlanificacionValidadorTransparencia;
import domain.validadorDeTransparencia.ValidadorDeTransparencia;
import spark.ModelAndView;
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
import Scheduler.*;

public class ValidadorTransparenciaController implements Controlador{
    private Scheduler scheduler=new Scheduler();
    public ModelAndView mostrarPaginaValidador(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        //HashMap<Integer, String> criterios = ActionProvider.obtenerCriteriosDeAsociacion().invocar();

        return new ModelAndView(parametros, "validacion_transparencia.hbs");
    }

    public ModelAndView mostrarPaginaPlanificacionValidador(Request request, Response response){

        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        List <String> tipoPlazo = new ArrayList<String>();
        int i;

        List<Integer> minutos= new ArrayList<Integer>();
        for(int j=0;j<60;j++){
            minutos.add(j);
        }

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
        parametros.put("minutos", minutos);

        return new ModelAndView(parametros, "planificacion_validador.hbs");
    }

    public Response modificarPlanificacionValidador(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        LocalDate fecha;
        int minuto;
        try{
            minuto=Integer.parseInt(request.queryParams("minuto"));

        }catch(Exception e){
            response.redirect("/gesoc/planificacion_validador");
            return response;
        }

        int tipoPlazo = Integer.parseInt(request.queryParams("tipo_plazo"));
        Periodo periodo = Periodo.values()[tipoPlazo];
        int horario = Integer.parseInt(request.queryParams("horario"));
        List<Dia> dias = new ArrayList<Dia>();
        List<DiasJSON> diasNumero = new ArrayList<DiasJSON>();
        Type listType = new TypeToken<ArrayList<DiasJSON>>(){}.getType();
        diasNumero = new Gson().fromJson(request.queryParams("dias"), listType);
        if(diasNumero.size() == 0){
            this.setearModalAlerta(request, "Error en la modificación del horario de validación", "Debe ingresar dias de ejecución.", AsistenteDeColores.getError());
            response.redirect("/gesoc/hora_vinculo");
            return response;
        }
        for(int i=0; i<diasNumero.size(); i++){
            dias.add(Dia.values()[diasNumero.get(i).numero]);
        }

        Date fechita= new Date();
        fechita.setHours(horario);
        fechita.setMinutes(minuto);
        fechita.setSeconds(0);




        ValidadorDeTransparencia validador= ValidadorDeTransparencia.instancia();
        System.out.println("---------------------------------VINE A PLANIFICAR");
        AsistentePlanificacionValidadorTransparencia.instancia().replanificar(periodo,dias,fechita);

        this.setearModalAlerta(request, "Planificación modificada exitosamente", "Ahora la validación de transparencia se realizara " + periodo.toString() , AsistenteDeColores.getExito());
        response.redirect("/gesoc/planificacion_validador");
        return response;
    }

    public Response ejecucionManualValidador(Request request, Response response){
        try{
            ValidadorDeTransparencia.instancia().ejecutarAccionPeriodica();
        }catch(Exception e){
            this.setearModalAlerta(request, "Error en la ejecución del validador de transparencia", "Ocurrió un error inesperado en la ejecución del validador de transparencia.", AsistenteDeColores.getError());
            response.redirect("/gesoc/validacion_transparencia");
            return response;
        }
        this.setearModalAlerta(request, "Éxito en la ejecución del validador de transparencia", "La validación de transparencia se ejecutó correctamente.", AsistenteDeColores.getExito());

        response.redirect("/gesoc/validacion_transparencia");
        return response;
    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_validador",true);
        request.session().attribute("aviso_modal_validador", mensaje);
        request.session().attribute("modal_titulo_validador", titulo);
        request.session().attribute("modal_color_validador", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_validador")==null)
            return false;
        return request.session().attribute("alerta_validador");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_validador");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_validador");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_validador");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_validador",false);
    }
}
