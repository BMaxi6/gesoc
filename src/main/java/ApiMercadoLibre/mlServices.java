package ApiMercadoLibre;

import DTOs.MonedaDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import DTOs.PaisDto;
import DTOs.ProvinciaDto;

import java.util.List;

public interface mlServices {
    @GET("classified_locations/countries")
    Call<List<PaisDto>> paises() ;

    @GET("classified_locations/countries/{idPais}")
    Call <PaisDto> obtenerInfoPais (@Path("idPais") String idPais);

    @GET("classified_locations/states/{idProvincia}")
    Call <ProvinciaDto> obtenerInfoProvincia(@Path("idProvincia") String idProvincia);

    @GET("currencies")
    Call <List<MonedaDto>> monedas();
}
