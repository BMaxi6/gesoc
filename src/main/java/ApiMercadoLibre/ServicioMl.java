package ApiMercadoLibre;

import domain.monedas.Moneda;
import DTOs.MonedaDto;
import repositorios.Repositorio;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import domain.ubicaciones.*;
import DTOs.PaisDto;
import DTOs.ProvinciaDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ServicioMl {
    private static ServicioMl instancia=null;
    private Retrofit retrofit;
    Properties prop;

    public static ServicioMl instancia(){
        if(instancia==null){
            instancia= new ServicioMl();
        }
        return instancia;
    }

    private ServicioMl(){
        prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("configServiciosML.properties");
            prop.load(is);
        } catch(IOException e) {
            System.out.println(e.toString());
        }

        this.retrofit=new Retrofit.Builder().baseUrl(this.prop.getProperty("PATH_BASE_API_ML"))
                .addConverterFactory(GsonConverterFactory.create()).build();

    }

    public List<Pais> listaPaises() throws IOException {
        mlServices ml_services= this.retrofit.create(mlServices.class);
        Call<List<PaisDto>> requestListaPaises=ml_services.paises();
        Response<List<PaisDto>> responseListaPaises= requestListaPaises.execute();
        List<PaisDto> listaPaises= responseListaPaises.body();

        for(PaisDto pais: listaPaises){
            this.cargarProvinciasAlPais(pais);

            for(ProvinciaDto provincia: pais.states){
;
                this.cargarCiudadesAProvincia(provincia);
            }
        }


        return listaPaises.stream().map(paisDto->paisDto.generarPais()).collect(Collectors.toList());
    }
    public Repositorio<String> cargarPaisesAlRepositorio(Repositorio<String> repo) throws IOException {
        this.listaPaises().stream().forEach(pais->repo.agregar(pais));
        return repo;
    }

    public PaisDto cargarProvinciasAlPais(PaisDto pais) throws IOException {
        mlServices ml_services= this.retrofit.create(mlServices.class);
        Call<PaisDto> requestListaProvincias=ml_services.obtenerInfoPais(pais.id);
        Response<PaisDto> responseListaProvincias= requestListaProvincias.execute();
        PaisDto paisResponse= responseListaProvincias.body();
        pais.states=paisResponse.states;
        pais.time_zone=paisResponse.time_zone;

        return pais;
    }

    public ProvinciaDto cargarCiudadesAProvincia(ProvinciaDto provincia) throws IOException {
        mlServices ml_services= this.retrofit.create(mlServices.class);
        Call<ProvinciaDto> requestInfoProvincia=ml_services.obtenerInfoProvincia(provincia.id);
        Response<ProvinciaDto> responseInfoProvincia= requestInfoProvincia.execute();
        ProvinciaDto provinciaResponse= responseInfoProvincia.body();
        provincia.cities=provinciaResponse.cities;


        return provincia;
    }

    public List<Moneda> obtenerMonedas() throws IOException {
        mlServices ml_services= this.retrofit.create(mlServices.class);
        Call<List<MonedaDto>> requestMonedas=ml_services.monedas();
        Response<List<MonedaDto>> responseMonedas= requestMonedas.execute();
        List<MonedaDto> monedasResponse= responseMonedas.body();

        return monedasResponse.stream().map(monedaDto->monedaDto.generarMoneda()).collect(Collectors.toList());

    }

    public Repositorio<String> cargarMonedasAlRepositorio(Repositorio<String> repo) throws IOException {
        this.obtenerMonedas().stream().forEach(moneda->repo.agregar(moneda));
        return repo;
    }
}
