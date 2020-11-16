package domain.validadorDeTransparencia;

import Scheduler.Planificable;
import bandejaDeMensajes.Mensaje;
import domain.Egresos.OperacionDeEgreso;
import repositorios.CriteriaQueryFactory;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;

import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

public class ValidadorDeTransparencia implements Planificable {
    private static ValidadorDeTransparencia instancia=null;
    private List<TipoValidacionTransparencia> validaciones = new ArrayList<TipoValidacionTransparencia>();

    private Object Null;
    //private List<OperacionDeEgreso>egresosAValidar=new ArrayList<OperacionDeEgreso>();

    public static ValidadorDeTransparencia instancia(){
        if(instancia==null)
            instancia=new ValidadorDeTransparencia();
        return instancia;
    }

    public Boolean calcularResultadoValidacion(OperacionDeEgreso operacion){
        return validaciones.stream().map(x->x.validar(operacion)).allMatch(x->x.equals(TRUE));
    }

    public Mensaje generarMensaje(OperacionDeEgreso operacion){
        Mensaje msg;
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd/MM/YYYY");
        for(TipoValidacionTransparencia v: this.validaciones){
            if(!v.validar(operacion)){

                msg=new Mensaje(new Formatter()
                        .format("Validación de la operación %d (Fecha: %s) incorrecta: %s", operacion.getNumeroOp(),dateTimeFormatter.format(operacion.getFecha()),v.mensajeParaError()).toString(),LocalDate.now());
                return msg;
            }
        }
        String stringMensaje;
        LocalDate fechaCreacion = LocalDate.now();
        stringMensaje = new Formatter().format("Validación de la operación de egreso %d (Fecha: %s) exitosa.", operacion.getNumeroOp() , dateTimeFormatter.format(operacion.getFecha())).toString(); //operacion.getNumeroOp(), "correcta";
        msg = new Mensaje(stringMensaje, fechaCreacion);
        return msg;

    }

    public void agregarValidacion(TipoValidacionTransparencia nuevaValidacion){
        validaciones.add(nuevaValidacion);
    }

    /*public void agregarEgresoAValidar(OperacionDeEgreso op){
        egresosAValidar.add(op);
    }*/


    public void enviarResultado (OperacionDeEgreso operacion){
        Mensaje mensajeAEnviar=this.generarMensaje(operacion);
        operacion.enviarMensajeARevisores(mensajeAEnviar);
    }


    @Override
    public void ejecutarAccionPeriodica() {
        this.validarTodasLasOperaciones();
    }
    public void validarTodasLasOperaciones(){
        List<OperacionDeEgreso> operacionesValidadas=new ArrayList<OperacionDeEgreso>();
        Repositorio<OperacionDeEgreso> repoEgresos=FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        //List<OperacionDeEgreso> egresosAValidar= repoEgresos.buscarTodos().stream().filter(op->!op.isValidada()).collect(Collectors.toList());//cambiarlo por una query
        List<OperacionDeEgreso> egresosAValidar= repoEgresos.buscarGrupo(new CriteriaQueryFactory().busquedaPorAtributo(OperacionDeEgreso.class,"validada",false));
        for(OperacionDeEgreso operacion :egresosAValidar){

                this.enviarResultado(operacion);
                operacionesValidadas.add(operacion);
                operacion.setValidada(true);
                repoEgresos.modificar(operacion);

        }

        //operacionesValidadas.stream().forEach(op -> egresosAValidar.remove(op));
    }


    public List<TipoValidacionTransparencia> getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(List<TipoValidacionTransparencia> validaciones) {
        this.validaciones = validaciones;
    }



    public Object getNull() {
        return Null;
    }

    public void setNull(Object aNull) {
        Null = aNull;
    }

    public ValidadorDeTransparencia() {
        validaciones.add(new ValidarCantidadPresupuestos());
        validaciones.add(new ValidarCriterioSeleccion());
        validaciones.add(new ValidarDetalle());


    }
/*public List<OperacionDeEgreso> getEgresosAValidar() {
        return egresosAValidar;
    }

    public void setEgresosAValidar(List<OperacionDeEgreso> egresosAValidar) {
        this.egresosAValidar = egresosAValidar;
    }*/
}