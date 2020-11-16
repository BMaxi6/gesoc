package AsociadorEgresoIngreso.Core.Actions;

import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import AsociadorEgresoIngreso.Core.Domain.AsociadorEgresoIngreso;
import domain.Egresos.OperacionDeEgreso;

import java.io.IOException;
import java.util.List;

public class AsociarEgresosAIngresos {
    private AsociadorEgresoIngreso asociador;
    public AsociarEgresosAIngresos(AsociadorEgresoIngreso asociador){
        this.asociador=asociador;
    }
    public List<EgresosAsociados> invocar(Integer idCriterio) throws IOException {
        return asociador.asociarEgresosAIngresos(idCriterio);
    }
}
