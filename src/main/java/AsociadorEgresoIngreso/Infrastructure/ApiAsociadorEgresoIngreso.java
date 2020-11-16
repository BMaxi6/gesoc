package AsociadorEgresoIngreso.Infrastructure;

import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import AsociadorEgresoIngreso.Core.Domain.AsociadorEgresoIngreso;
import AsociadorEgresoIngreso.Services.ApiService;
import AsociadorEgresoIngreso.Conversiones.EgresoApi;
import AsociadorEgresoIngreso.Conversiones.IngresoApi;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;
import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ApiAsociadorEgresoIngreso implements AsociadorEgresoIngreso {

    private final ApiService apiService = ApiService.instancia();

    public void guardarIngresos(List<Ingreso> ingresos) throws IOException {
        List<IngresoApi> ingresosApi = ingresos.stream().map(i -> {
            return new IngresoApi(i.getId(), i.getValor(), i.getFecha().toString(), i.getFechaDeAceptabilidadEgreso().toString(), i.getValorRestante());
        }).collect(Collectors.toList());
        apiService.guardarIngresos(ingresosApi);
    }
    public void guardarEgresos(List<OperacionDeEgreso> egresos) throws IOException {
        List<EgresoApi> egresosApi = egresos.stream().map(e -> {
            return new EgresoApi(e.getNumeroOp(), e.getId(), e.getValorTotal(), e.getFecha().toString());
        }).collect(Collectors.toList());
        apiService.guardarEgresos(egresosApi);
    }
    public List<EgresosAsociados> asociarEgresosAIngresos(Integer idCriterio) throws IOException{
        List<EgresosAsociados> operacionesAsociadas = apiService.asociarEgresosAIngresos(idCriterio);
        return operacionesAsociadas;
    }

    public HashMap<Integer, String> obtenerCriteriosDeAsociacion() throws IOException {
        return apiService.obtenerCriteriosDeAsociacion();
    }
}