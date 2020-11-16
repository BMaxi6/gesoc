package AsociadorEgresoIngreso;

import AsociadorEgresoIngreso.Core.Domain.AsociadorEgresoIngreso;
import AsociadorEgresoIngreso.Infrastructure.ApiAsociadorEgresoIngreso;

public class DomainProvider {
    public static AsociadorEgresoIngreso obtenerAsociadorEgresoIngreso(){
        return new ApiAsociadorEgresoIngreso();
    }
}
