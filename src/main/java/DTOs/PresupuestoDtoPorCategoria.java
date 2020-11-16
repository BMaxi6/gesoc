package DTOs;

import domain.Egresos.Presupuesto;

public class PresupuestoDtoPorCategoria {
    private int id;
    private int numeroEgreso;
    private String fecha;
    private Double valorTotal;

    public PresupuestoDtoPorCategoria(Presupuesto presupuesto){
        this.id = presupuesto.getId();
        this.numeroEgreso = presupuesto.getOperacion().getNumeroOp();
        this.fecha = presupuesto.getFecha().toString();
        this.valorTotal = presupuesto.valorTotal();
    }

    public PresupuestoDtoPorCategoria(){}

    public static PresupuestoDtoPorCategoria toPresupuestoDtoPorCategoria(Presupuesto presupuesto){
        return new PresupuestoDtoPorCategoria(presupuesto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroEgreso() {
        return numeroEgreso;
    }

    public void setNumeroEgreso(int numeroEgreso) {
        this.numeroEgreso = numeroEgreso;
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
