package domain.validadorDeTransparencia;

import Scheduler.Dia;
import Scheduler.Periodo;
import Scheduler.Scheduler;
import repositorios.FactoryRepositorio;
import repositorios.Persistente;
import repositorios.Repositorio;

import java.util.Date;
import java.util.List;


public class AsistentePlanificacionValidadorTransparencia extends Persistente {


    private static AsistentePlanificacionValidadorTransparencia instancia= null;

    Scheduler scheduler=null;

    public static AsistentePlanificacionValidadorTransparencia instancia(){
        if(instancia==null)
            instancia=new AsistentePlanificacionValidadorTransparencia();
        return instancia;
    }

    public AsistentePlanificacionValidadorTransparencia(){
        InfoAsistentePlanificacionTransparencia infoPlani= InfoAsistentePlanificacionTransparencia.obtenerInfo();
        if(infoPlani!=null){
            this.replanificar(infoPlani.getPeriodo(), infoPlani.getDiasDeEjecucion(), infoPlani.getFecha());
        }
    }

    public void replanificar(Periodo periodo, List<Dia> diasDeEjecucion, Date fecha){
        Repositorio<AsistentePlanificacionValidadorTransparencia> repoAsistente= FactoryRepositorio.instancia().obtenerRepositorio(AsistentePlanificacionValidadorTransparencia.class);
        if(scheduler==null){
            scheduler= new Scheduler();
        }

        InfoAsistentePlanificacionTransparencia infoAsistentePlanificacionTransparencia= new InfoAsistentePlanificacionTransparencia();

        infoAsistentePlanificacionTransparencia.setPeriodo(periodo);
        infoAsistentePlanificacionTransparencia.setDiasDeEjecucion(diasDeEjecucion);
        infoAsistentePlanificacionTransparencia.setFecha(fecha);


        infoAsistentePlanificacionTransparencia.actualizate();


        scheduler.replanificar(periodo,diasDeEjecucion,ValidadorDeTransparencia.instancia(),fecha);
    }




}
