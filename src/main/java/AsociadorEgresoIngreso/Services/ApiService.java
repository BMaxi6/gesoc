package AsociadorEgresoIngreso.Services;

import AsociadorEgresoIngreso.Conversiones.EgresoApi;
import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import AsociadorEgresoIngreso.Conversiones.IngresoApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ApiService {
    private static ApiService instancia=null;
    private Retrofit retrofit;
    Properties prop;

    public static ApiService instancia(){
        if(instancia==null){
            instancia= new ApiService();
        }
        return instancia;
    }

    private ApiService(){
        prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("configAsociadorApi.properties");
            prop.load(is);
        } catch(IOException e) {
            System.out.println(e.toString());
        }

        if(this.prop.getProperty("PATH_BASE_API") == null){
            this.retrofit=new Retrofit.Builder().baseUrl("https://gesoc-componente-asociador.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            this.retrofit=new Retrofit.Builder().baseUrl(this.prop.getProperty("PATH_BASE_API"))
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
    }

    public void guardarIngresos(List<IngresoApi> ingresos) throws IOException {
        ApiHandler apiHandler = this.retrofit.create(ApiHandler.class);
        Call<Void> request = apiHandler.guardarIngresos(ingresos);
        request.execute();
    }

    public void guardarEgresos(List<EgresoApi> egresos) throws IOException{
        ApiHandler apiHandler = this.retrofit.create(ApiHandler.class);
        Call<Void> request = apiHandler.guardarEgresos(egresos);
        request.execute();
    }

    public List<EgresosAsociados> asociarEgresosAIngresos(Integer idCriterio) throws IOException{
        ApiHandler apiHandler = this.retrofit.create(ApiHandler.class);
        Call<List<EgresosAsociados>> request = apiHandler.asociarEgresosAIngresos(idCriterio);
        Response<List<EgresosAsociados>> response = request.execute();
        List<EgresosAsociados> responseValores = response.body();

        return responseValores;
    }

    public HashMap<Integer, String> obtenerCriteriosDeAsociacion() throws IOException{
        ApiHandler apiHandler = this.retrofit.create(ApiHandler.class);
        Call<HashMap<Integer, String>> request = apiHandler.obtenerCriteriosDeAsociacion();
        Response<HashMap<Integer, String>> response = request.execute();
        HashMap<Integer, String> responseValores = response.body();

        return responseValores;
    }
}
