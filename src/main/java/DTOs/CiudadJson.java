package DTOs;

import domain.ubicaciones.Ciudad;

public class CiudadJson {
    private int id;
    private String nombre;

    private CiudadJson(Ciudad ciudad){
        this.id=ciudad.getId();
        this.nombre=ciudad.getNombre();
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

    public static CiudadJson toCiudadJson(Ciudad ciudad){
        return new CiudadJson(ciudad);
    }
}
