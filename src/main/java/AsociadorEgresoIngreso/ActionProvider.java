package AsociadorEgresoIngreso;

import AsociadorEgresoIngreso.Core.Actions.GuardarEgresos;
import AsociadorEgresoIngreso.Core.Actions.GuardarIngresos;
import AsociadorEgresoIngreso.Core.Actions.AsociarEgresosAIngresos;
import AsociadorEgresoIngreso.Core.Actions.ObtenerCriteriosDeAsociacion;

public class ActionProvider {
    public static GuardarIngresos obtenerGuardarIngresos(){
        return new GuardarIngresos(DomainProvider.obtenerAsociadorEgresoIngreso());
    }
    public static GuardarEgresos obtenerGuardarEgresos(){
        return new GuardarEgresos(DomainProvider.obtenerAsociadorEgresoIngreso());
    }
    public static AsociarEgresosAIngresos asociarEgresosAIngresos(){
        return new AsociarEgresosAIngresos(DomainProvider.obtenerAsociadorEgresoIngreso());
    }
    public static ObtenerCriteriosDeAsociacion obtenerCriteriosDeAsociacion(){
        return new ObtenerCriteriosDeAsociacion(DomainProvider.obtenerAsociadorEgresoIngreso());
    }
}


