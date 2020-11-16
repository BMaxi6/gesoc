package DTOs;

import domain.Categorizacion.Categoria;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoCategoriasDTO {
    private int id;
    private int numeroEgreso;
    private String proveedor;
    private String elegido;
    private Double valorTotal;
    private String fecha;
    private List<Categoria> categorias = new ArrayList<Categoria>();
    private List<Categoria> categoriasEgresos = new ArrayList<>();

    public PresupuestoCategoriasDTO(Presupuesto presupuesto){
        this.id= presupuesto.getId();
        this.numeroEgreso= presupuesto.getOperacion().getNumeroOp();
        try{
            this.proveedor = presupuesto.getProveedorP().getNombre();
        }catch(Exception e){
            this.proveedor="---";
        }
        this.valorTotal = presupuesto.valorTotal();
        if(presupuesto.getElegido()){
            this.elegido = "Si";
        }else {
            this.elegido = "No";
        }
        this.categorias = presupuesto.getCategorias();
        this.fecha = presupuesto.getFecha().toString();
        this.categoriasEgresos = presupuesto.getOperacion().getCategorias();
    }

    public PresupuestoCategoriasDTO(){}

    public int getNumeroEgreso() {
        return numeroEgreso;
    }

    public void setNumeroEgreso(int numeroEgreso) {
        this.numeroEgreso = numeroEgreso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroOp() {
        return numeroEgreso;
    }

    public void setNumeroOp(int numeroOp) {
        this.numeroEgreso = numeroOp;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Categoria> getCategoriasEgresos() { return categoriasEgresos; }

    public void setCategoriasEgresos(List<Categoria> categoriasEgresos) { this.categoriasEgresos = categoriasEgresos; }

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
