package AsociadorEgresoIngreso.Core.Actions;

import AsociadorEgresoIngreso.Core.Domain.AsociadorEgresoIngreso;

import java.io.IOException;
import java.util.HashMap;


public class ObtenerCriteriosDeAsociacion {
    private AsociadorEgresoIngreso asociador;
    public ObtenerCriteriosDeAsociacion(AsociadorEgresoIngreso asociador){
        this.asociador=asociador;
    }
    public HashMap<Integer, String> invocar() throws IOException {
        return asociador.obtenerCriteriosDeAsociacion();
    }
}
