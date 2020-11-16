package DTOs;

import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PresupuestoDTO {
    private int id;
    private String proveedor;
    private String elegido;
    private Double valorTotal;
    private String fecha;

    public PresupuestoDTO(Presupuesto presupuesto){
        this.id= presupuesto.getId();
        try{
            this.proveedor = presupuesto.getProveedorP().getNombre();
        }catch(Exception e){
            this.proveedor="---";
        }
        this.valorTotal = presupuesto.valorTotal();
        if(presupuesto.getElegido().equals(Boolean.TRUE)){
           this.elegido = "Si";
        }else{
            this.elegido = "No";
        }
        this.fecha = presupuesto.getFecha().toString();
    }

    public PresupuestoDTO(){}

    public static PresupuestoDTO toPresupuestoDto(Presupuesto presupuesto){
        return new PresupuestoDTO(presupuesto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getElegido() {
        return elegido;
    }

    public void setElegido(String elegido) {
        this.elegido = elegido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
