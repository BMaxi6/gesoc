package Scheduler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Periodo {
    //SEGUNDO("Una vez por segundo", 1000), //comentado, se usa para pruebas pero no hay que mostrarlo al usuario
    HORARIO ("Una vez por hora", 1000*60*60), DOCE_HS("Una vez cada 12 horas", 12*1000*60*60), DIARIO ("Una vez por día", 24*1000*60*60), SEMANAL("Una vez semana", 7*24*1000*60*60), QUINCENAL("Una vez por quincena", 7*24*1000*60*60*2), MENSUAL("Una vez por mes", 7*24*1000*60*60*4);

    private String nombre;
    private long intervalo;

    Periodo(String nombre, long intervalo) {
        this.nombre = nombre;
        this.intervalo=intervalo;
    }

    @Override
    public String toString(){
        return nombre;
    }
    public long intervalo(){
        return intervalo;
    }
    public static Periodo toPeriodo(String nombre){
        List<Periodo> enums= Arrays.asList(Periodo.values().clone());
        List<String> strings=  Arrays.stream(Periodo.values()).map(e->e.toString()).collect(Collectors.toList());
        int index=strings.indexOf(nombre);
        return index!=-1?enums.get(index):null;
    }


}
