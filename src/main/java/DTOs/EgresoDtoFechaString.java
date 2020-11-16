package DTOs;

import domain.Egresos.OperacionDeEgreso;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EgresoDtoFechaString {
    private int id;
    private int numeroOp;
    private String fecha;
    private Double valorTotal;

    public EgresoDtoFechaString(OperacionDeEgreso egreso){
        this.id= egreso.getId();
        this.numeroOp= egreso.getNumeroOp();
        this.fecha=egreso.getFecha().toString();
        this.valorTotal = egreso.getValorTotal();
    }

    public EgresoDtoFechaString(){}

    public static EgresoDtoFechaString toEgresoDtoFechaString(OperacionDeEgreso egreso){
        return new EgresoDtoFechaString(egreso);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroOp() {
        return numeroOp;
    }

    public void setNumeroOp(int numeroOp) {
        this.numeroOp = numeroOp;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
