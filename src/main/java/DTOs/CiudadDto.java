package DTOs;

import domain.ubicaciones.Ciudad;

public class CiudadDto {
    public String id;
    public String name;
    public Ciudad generarCiudad(){
        return new Ciudad(id, name);
    }
}
