package DTOs;

import domain.Categorizacion.Categoria;
import domain.Egresos.OperacionDeEgreso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EgresoCategoriasDTO {
    private int id;
    private int numeroOp;
    private String proveedor;
    private LocalDate fecha;
    private BigDecimal valorTotal;
    private List<Categoria> categorias =new ArrayList<Categoria>();

    public EgresoCategoriasDTO(OperacionDeEgreso egreso){
        this.id= egreso.getId();
        this.numeroOp= egreso.getNumeroOp();
        try{
            this.proveedor= egreso.getProveedor().getNombre();
        }catch(Exception e){
            this.proveedor="---";
        }

        this.fecha=egreso.getFecha();
        this.valorTotal= BigDecimal.valueOf(egreso.getValorTotal());
        this.categorias = egreso.getCategorias();
    }

    public EgresoCategoriasDTO(){}

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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
