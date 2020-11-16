package AsociadorEgresoIngreso.Core.Domain;

import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface AsociadorEgresoIngreso {
    public void guardarIngresos(List<Ingreso> ingresos) throws IOException;
    public void guardarEgresos(List<OperacionDeEgreso> egresos) throws IOException;
    public List<EgresosAsociados> asociarEgresosAIngresos(Integer idCriterio) throws IOException;
    public HashMap<Integer, String> obtenerCriteriosDeAsociacion() throws IOException;
}
