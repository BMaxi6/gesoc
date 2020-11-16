package domain.Egresos;

import Scheduler.Periodo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TipoDocumentoComercial {
    NINGUNO("-"),FACTURA_A("Factura A"), FACTURA_B("Factura B"), FACTURA_C("Factura C"),OTROS("Otros");
    private String nombre;


    TipoDocumentoComercial(String nombre) {
        this.nombre = nombre;

    }

    @Override
    public String toString(){
        return nombre;
    }
    public static TipoDocumentoComercial toTipoDocumentoComercial(String nombre){
        List<TipoDocumentoComercial> enums= Arrays.asList(TipoDocumentoComercial.values().clone());
        List<String> strings=  Arrays.stream(TipoDocumentoComercial.values()).map(e->e.toString()).collect(Collectors.toList());
        int index=strings.indexOf(nombre);
        return index!=-1?enums.get(index):null;
    }
    public static List<String> obtenerTodos(){
        return Arrays.stream(TipoDocumentoComercial.values()).map(e->e.toString()).collect(Collectors.toList());
    }
}
