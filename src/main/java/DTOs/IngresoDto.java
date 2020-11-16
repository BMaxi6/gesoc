package DTOs;

import domain.Egresos.Ingreso;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IngresoDto {
    private int id;
    private String descripcion;
    private BigDecimal valorTotal;
    private LocalDate fecha;
    private LocalDate fechaAceptabilidad;
    private BigDecimal valorRestante;


    public IngresoDto(Ingreso ingreso){
        this.descripcion=ingreso.getDescripcion();
        this.id=ingreso.getId();
        this.valorTotal= BigDecimal.valueOf(ingreso.getValorTotal());
        this.fecha= ingreso.getFecha();
        this.fechaAceptabilidad=ingreso.getFechaDeAceptabilidadEgreso();
        this.valorRestante= BigDecimal.valueOf(ingreso.getValorRestante());
    }




}
