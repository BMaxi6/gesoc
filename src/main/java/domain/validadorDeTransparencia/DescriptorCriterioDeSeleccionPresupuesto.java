package domain.validadorDeTransparencia;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DescriptorCriterioDeSeleccionPresupuesto {
    MENOR_VALOR("Menor valor");
    private String nombre;

    DescriptorCriterioDeSeleccionPresupuesto(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }

    public CriterioDeSeleccionPresupuesto obtenerCriterioDeSeleccionPresupuesto(String nombre){
        switch (nombre){
            case "Menor valor":
                return CriterioMenorValorPresupuesto.instancia();

            default:
                return null;

        }
    }

}
