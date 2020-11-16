package DTOs;

import domain.ubicaciones.Pais;
import domain.ubicaciones.Provincia;

import java.util.List;
import java.util.stream.Collectors;

public class PaisJson {
    private int id;
    private String nombre;
    private List<ProvinciaJson> provincias;

    public PaisJson(Pais pais){
        this.id= pais.getId();
        this.nombre=pais.getNombre();
        this.provincias=pais.getProvincias().stream().map(p-> ProvinciaJson.toProvinciaJson(p)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ProvinciaJson> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<ProvinciaJson> provincias) {
        this.provincias = provincias;
    }

    public static PaisJson toPaisJson(Pais pais){
        return new PaisJson(pais);
    }
}
