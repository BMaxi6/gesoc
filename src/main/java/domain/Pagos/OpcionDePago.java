package domain.Pagos;

import Scheduler.Periodo;
import domain.Egresos.TipoDocumentoComercial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OpcionDePago {
    TRES_PAGOS ("3 pagos"), SEIS_PAGOS ("6 pagos"), DOCE_PAGOS ("12 pagos");
    private String nombre;
    public String toString(){
        return this.nombre;
    }

    OpcionDePago(String nombre){
        this.nombre=nombre;
    }

    public static OpcionDePago toOpcionDePago(String nombre){
        List<OpcionDePago> enums= Arrays.asList(OpcionDePago.values().clone());
        List<String> strings=  Arrays.stream(OpcionDePago.values()).map(e->e.toString()).collect(Collectors.toList());
        int index=strings.indexOf(nombre);
        return index!=-1?enums.get(index):null;
    }
    public static List<String> obtenerTodos(){
        return Arrays.stream(OpcionDePago.values()).map(e->e.toString()).collect(Collectors.toList());
    }
}
