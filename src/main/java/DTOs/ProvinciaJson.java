package DTOs;

import domain.ubicaciones.Provincia;

import java.util.List;
import java.util.stream.Collectors;

public class ProvinciaJson {
    private int id;
    private String nombre;
    private List<CiudadJson> ciudades;

    public ProvinciaJson(Provincia provincia){
        this.id= provincia.getId();
        this.nombre= provincia.getNombre();
        this.ciudades= provincia.getCiudades().stream().map(c->CiudadJson.toCiudadJson(c)).collect(Collectors.toList());
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

    public List<CiudadJson> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<CiudadJson> ciudades) {
        this.ciudades = ciudades;
    }

    public static ProvinciaJson toProvinciaJson(Provincia provincia){
        return new ProvinciaJson(provincia);
    }
}
