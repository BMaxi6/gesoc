package DTOs;

import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PresupuestoDTO {
    private int id;
    private String proveedor;
    private Boolean elegido;
    private BigDecimal valorTotal;
    private String fecha;

    public PresupuestoDTO(Presupuesto presupuesto){
        this.id= presupuesto.getId();
        try{
            this.proveedor = presupuesto.getProveedorP().getNombre();
        }catch(Exception e){
            this.proveedor="---";
        }
        this.valorTotal = BigDecimal.valueOf(presupuesto.valorTotal());
        this.elegido = presupuesto.getElegido();
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

    public Boolean getElegido() {
        return elegido;
    }

    public void setElegido(Boolean elegido) {
        this.elegido = elegido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
