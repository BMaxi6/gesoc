package domain.Pagos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TipoMedioPago {
    TARJETADECREDITO ("Tarjeta de crédito"), TARJETADEDEBITO ("Tarjeta de débito"), CHEQUE ("Cheque"), TICKET ("Ticket"), ATM("ATM"), DINEROENCUENTA("Dinero en cuenta"),EFECTIVO("Efectivo");
    private String nombre;
    public String toString(){
        return this.nombre;
    }

   TipoMedioPago(String nombre){
        this.nombre=nombre;
    }

    public static TipoMedioPago toTipoMedioPago(String nombre){
        List<TipoMedioPago> enums= Arrays.asList(TipoMedioPago.values().clone());
        List<String> strings=  Arrays.stream(TipoMedioPago.values()).map(e->e.toString()).collect(Collectors.toList());
        int index=strings.indexOf(nombre);
        return index!=-1?enums.get(index):null;
    }
    public static List<String> obtenerTodos(){
        return Arrays.stream(TipoMedioPago.values()).map(e->e.toString()).collect(Collectors.toList());
    }

}
