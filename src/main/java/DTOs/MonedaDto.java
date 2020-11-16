package DTOs;

import domain.monedas.Moneda;

public class MonedaDto {
    public String id;
    public String description;
    public String symbol;
    public int decimal_places;
    public Moneda generarMoneda(){
        return new Moneda(id, description, symbol, decimal_places);
    }
}
