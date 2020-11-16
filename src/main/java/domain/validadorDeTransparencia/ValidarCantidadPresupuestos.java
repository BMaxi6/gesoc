package domain.validadorDeTransparencia;

import domain.Egresos.OperacionDeEgreso;

public class ValidarCantidadPresupuestos implements TipoValidacionTransparencia{

    @Override
    public Boolean validar(OperacionDeEgreso op) {
        int cantidadPresupuestosAgregados = op.getPresupuestos().size();
        int presupuestosEsperados = op.getCantidadPresupuestosRequeridos();
        return cantidadPresupuestosAgregados >= presupuestosEsperados;

    }
    public String mensajeParaError(){
        return "Falló la validación por cantidad de presupuestos";
    }
}
