package AsociadorEgresoIngreso.Core.Actions;

import AsociadorEgresoIngreso.Core.Domain.AsociadorEgresoIngreso;
import domain.Egresos.Ingreso;

import java.io.IOException;
import java.util.List;

public class GuardarIngresos {
    private AsociadorEgresoIngreso asociador;
    public GuardarIngresos(AsociadorEgresoIngreso asociador){
        this.asociador=asociador;
    }
    public void invocar(List<Ingreso> ingresos) throws IOException {
        asociador.guardarIngresos(ingresos);
    }
}
