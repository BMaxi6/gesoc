package AsociadorEgresoIngreso.Services;

import AsociadorEgresoIngreso.Conversiones.EgresoApi;
import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import AsociadorEgresoIngreso.Conversiones.IngresoApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


import java.util.HashMap;
import java.util.List;

public interface ApiHandler {
    @POST("/ingresos")
    Call<Void> guardarIngresos(@Body List<IngresoApi> ingresos);

    @POST("/egresos")
    Call<Void> guardarEgresos(@Body List<EgresoApi> egresos);

    @GET("/asociaciones")
    Call<List<EgresosAsociados>> asociarEgresosAIngresos(@Query("idCriterio") Integer idCriterio);

    @GET("/criterios")
    Call<HashMap<Integer, String>> obtenerCriteriosDeAsociacion();
}