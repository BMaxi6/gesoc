package domain.validadorDeTransparencia;

import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;

import java.util.Comparator;
import java.util.stream.Collectors;

import static domain.validadorDeTransparencia.DescriptorCriterioDeSeleccionPresupuesto.MENOR_VALOR;

public class CriterioMenorValorPresupuesto extends CriterioDeSeleccionPresupuesto{

    private static CriterioMenorValorPresupuesto instancia = null;

    public static CriterioMenorValorPresupuesto instancia(){
        if(instancia==null){
            instancia=new CriterioMenorValorPresupuesto();
            instancia.setDescriptor(MENOR_VALOR);
        }

        return instancia;
    }

    @Override
    public Presupuesto seleccionarPresupuesto(OperacionDeEgreso ope) {
        System.out.println("Egreso: " + ope.getNumeroOp());
        System.out.println("Presupuestos: " + ope.getPresupuestos().size());
        try{
            return ope.getPresupuestos().stream().sorted(Comparator.comparing(p -> p.valorTotal())).collect(Collectors.toList()).get(0);
        }
        catch(Exception e){
            return null;
        }

    }
}