package domain.validadorDeTransparencia;

import Scheduler.Dia;
import Scheduler.Periodo;
import repositorios.FactoryRepositorio;
import repositorios.Persistente;
import repositorios.Repositorio;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name="info_asistente_validador_transparencia")
public class InfoAsistentePlanificacionTransparencia extends Persistente {
    @Column(name="periodo")
    @Enumerated(value = EnumType.STRING)
    private Periodo periodo;
    @Column
    @Enumerated
    @ElementCollection(targetClass = Dia.class, fetch= FetchType.EAGER)
    private List<Dia> diasDeEjecucion;
    @Column(name="fecha")
    private Date fecha;

    public static InfoAsistentePlanificacionTransparencia obtenerInfo(){
        Repositorio<InfoAsistentePlanificacionTransparencia> repo= FactoryRepositorio.instancia().obtenerRepositorio(InfoAsistentePlanificacionTransparencia.class);
        List<InfoAsistentePlanificacionTransparencia> info= repo.buscarTodos();
        if(info.size()>0){
            return info.get(0);
        }else{
            return null;
        }
    }

    public void actualizate(){
        Repositorio<InfoAsistentePlanificacionTransparencia> repo= FactoryRepositorio.instancia().obtenerRepositorio(InfoAsistentePlanificacionTransparencia.class);

        if(estoyCargado()){
            List<InfoAsistentePlanificacionTransparencia> info= repo.buscarTodos();
            InfoAsistentePlanificacionTransparencia infoEncontrada= info.get(0);
            infoEncontrada.setDiasDeEjecucion(this.diasDeEjecucion);
            infoEncontrada.setFecha(this.fecha);
            infoEncontrada.setPeriodo(this.periodo);
            repo.modificar(infoEncontrada);
        }else{
            repo.agregar(this);
        }



    }

    public boolean estoyCargado(){
        Repositorio<InfoAsistentePlanificacionTransparencia> repo= FactoryRepositorio.instancia().obtenerRepositorio(InfoAsistentePlanificacionTransparencia.class);
        List<InfoAsistentePlanificacionTransparencia> info= repo.buscarTodos();
        return  info.size()>0;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<Dia> getDiasDeEjecucion() {
        return diasDeEjecucion;
    }

    public void setDiasDeEjecucion(List<Dia> diasDeEjecucion) {
        this.diasDeEjecucion = diasDeEjecucion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
