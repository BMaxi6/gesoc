package domain.Items;

import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item extends Persistente {

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name="valor_unitario")
    private Double valorUnitario;

    @Column(name="tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoDeItem tipo;

    @Column(name="descripcion")
    private String descripcion;

    public Double valorTotal(){
        return valorUnitario*cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public void setTipo(TipoDeItem tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {return this.cantidad;}
    public Double getValorUnitario() {return this.valorUnitario;}
    public TipoDeItem getTipo() {return this.tipo;}
    public Item(){}

    public Item(Integer cantidad, Double valorUnitario, Integer tipoItem, String descripcion){
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        //this.tipo
        this.descripcion = descripcion;

    }

}
