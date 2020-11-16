package domain.Pagos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TipoDePago {
    SIN_INTERES("s/i"), CON_INTERES("c/i");
    private String nombre;
    public String toString(){
        return this.nombre;
    }

    TipoDePago(String nombre){
        this.nombre=nombre;
    }

    public static TipoDePago toTipoDePago(String nombre){
        List<TipoDePago> enums= Arrays.asList(TipoDePago.values().clone());
        List<String> strings=  Arrays.stream(TipoDePago.values()).map(e->e.toString()).collect(Collectors.toList());
        int index=strings.indexOf(nombre);
        return index!=-1?enums.get(index):null;
    }
    public static List<String> obtenerTodos(){
        return Arrays.stream(TipoDePago.values()).map(e->e.toString()).collect(Collectors.toList());
    }
}
