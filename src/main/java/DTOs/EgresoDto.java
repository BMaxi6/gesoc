package DTOs;

import domain.Egresos.OperacionDeEgreso;
import domain.Personas.Proveedor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EgresoDto {
    private int id;
    private int numeroOp;
    private String proveedor;
    private LocalDate fecha;
    private Double valorTotal;

    public EgresoDto(OperacionDeEgreso egreso){
        this.id= egreso.getId();
        this.numeroOp= egreso.getNumeroOp();
        try{
            this.proveedor= egreso.getProveedor().getNombre();
        }catch(Exception e){
            this.proveedor="---";
        }

        this.fecha=egreso.getFecha();
        this.valorTotal= egreso.getValorTotal();
    }

    public EgresoDto(){}

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

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public static EgresoDto toEgresoDto(OperacionDeEgreso egreso){
        return new EgresoDto(egreso);
    }
}
