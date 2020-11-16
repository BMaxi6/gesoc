package domain.validadorDeTransparencia;

import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;


public abstract class CriterioDeSeleccionPresupuesto{

    protected DescriptorCriterioDeSeleccionPresupuesto descriptor;

    public abstract Presupuesto seleccionarPresupuesto(OperacionDeEgreso ope);

    public DescriptorCriterioDeSeleccionPresupuesto getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(DescriptorCriterioDeSeleccionPresupuesto descriptor) {
        this.descriptor = descriptor;
    }
}
