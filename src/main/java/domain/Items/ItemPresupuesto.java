package domain.Items;

import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="item_presupuesto")
public class ItemPresupuesto extends Persistente {

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name="valor_unitario")
    private Double valorUnitario;

    @Column(name="tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoDeItem tipo;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="item_asociado", referencedColumnName = "id")
    private Item itemAsociado;

    public Double valorTotal(){
        return valorUnitario*cantidad;
    }

    public ItemPresupuesto(Integer unaCantidad, Double valorUnitario){
        this.cantidad = unaCantidad;
        this.valorUnitario = valorUnitario;
    }

    public ItemPresupuesto(){}

    public Boolean itemAsociadoEsIgual (){
        return (this.cantidad == this.getItemAsociado().getCantidad() &&
                this.tipo == this.getItemAsociado().getTipo());
    }

    public Integer getCantidad () {return this.cantidad;}
    public Double getValorUnitario() {return this.valorUnitario;}
    public TipoDeItem getTipo() {return this.tipo;}
    public Item getItemAsociado() {return itemAsociado;}

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public void setTipo(TipoDeItem tipo) {
        this.tipo = tipo;
    }

    public void setItemAsociado(Item itemAsociado) {
        this.itemAsociado = itemAsociado;
    }
}
