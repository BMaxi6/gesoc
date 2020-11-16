package DTOs;

import domain.ubicaciones.Pais;
import domain.ubicaciones.Provincia;

import java.util.List;
import java.util.stream.Collectors;

public class PaisDto {
    public String id;
    public String name;
    public String currency_id;
    public String time_zone;
    public List<ProvinciaDto> states;
    public Pais generarPais(){
        List<Provincia> provincias= states.stream().map(provinciaDto -> provinciaDto.generarProvincia()).collect(Collectors.toList());
        return new Pais(id, name, currency_id,time_zone,provincias);
    }
}
