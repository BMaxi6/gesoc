package domain.validadorDeTransparencia;

import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;

import java.util.Optional;

public class ValidarCriterioSeleccion implements TipoValidacionTransparencia{

    @Override
    public Boolean validar(OperacionDeEgreso op) {

        Optional<Presupuesto> elegido =  op.getPresupuestos().stream().filter(p->p.getElegido().equals(Boolean.TRUE)).findFirst();
        System.out.println("Criterio egreso " + op.getCriterioSelecc());
        Presupuesto cumpleCriterio = op.getCriterioSelecc().seleccionarPresupuesto(op);

        if(cumpleCriterio!=null){
            return cumpleCriterio.equals(elegido.get());
        }else{
            return true;
        }


    }
    public String mensajeParaError(){
        return "Falló la validación por cantidad de criterio de seleccion";
    }
}
