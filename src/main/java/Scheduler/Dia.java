package Scheduler;

import domain.validadorDeTransparencia.CriterioDeSeleccionPresupuesto;
import domain.validadorDeTransparencia.CriterioMenorValorPresupuesto;

import java.time.DayOfWeek;

public enum Dia {
    LUNES("lunes"), MARTES("martes"), MIERCOLES("miércoles"), JUEVES("jueves"), VIERNES("viernes"), SABADO("sábado"), DOMINGO("domingo");
    private String nombre;

    Dia(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }

    public Dia toDia(String nombre){
        switch (nombre){
            case "lunes":
                return LUNES;
            case "martes":
                return MARTES;
            case "miércoles":
                return MIERCOLES;
            case "jueves":
                return JUEVES;
            case "viernes":
                return VIERNES;
            case "sábado":
                return SABADO;
            case "domingo":
                return DOMINGO;
            default:
                return null;

        }
    }

    public static Dia convertirADia(DayOfWeek day){
        switch(day){
            case MONDAY:
                return LUNES;
            case TUESDAY:
                return MARTES;
            case WEDNESDAY:
                return MIERCOLES;
            case THURSDAY:
                return JUEVES;
            case FRIDAY:
                return VIERNES;
            case SATURDAY:
                return SABADO;
            case SUNDAY:
                return DOMINGO;
            default:
                return null;
        }
    }
}
