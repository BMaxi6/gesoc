package DTOs;

import domain.ubicaciones.Ciudad;
import domain.ubicaciones.Provincia;

import java.util.List;
import java.util.stream.Collectors;

public class ProvinciaDto {
    public String id;
    public String name;
    public List<CiudadDto> cities;
    public Provincia generarProvincia(){
        List<Ciudad> ciudades= cities.stream().map(ciudadDto->ciudadDto.generarCiudad()).collect(Collectors.toList());
        return new Provincia(id, name, ciudades);
    }
}
