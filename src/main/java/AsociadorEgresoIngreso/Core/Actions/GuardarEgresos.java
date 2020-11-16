package AsociadorEgresoIngreso.Core.Actions;

import AsociadorEgresoIngreso.Core.Domain.AsociadorEgresoIngreso;
import domain.Egresos.OperacionDeEgreso;

import java.io.IOException;
import java.util.List;

public class GuardarEgresos {
    private AsociadorEgresoIngreso asociador;
    public GuardarEgresos(AsociadorEgresoIngreso asociador){
        this.asociador=asociador;
    }
    public void invocar(List<OperacionDeEgreso> egresos) throws IOException {
        asociador.guardarEgresos(egresos);
    }
}
