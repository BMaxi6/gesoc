/*package domain.Detalles;

import domain.Items.ItemPresupuesto;
import repositorios.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="detalle_presupuesto")
public class DetallePresupuesto extends Persistente {

    @Column(name="valor_total")
    private Double valorTotal;

    @OneToMany(mappedBy = "detalle_presupuesto", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<ItemPresupuesto> items = new ArrayList<ItemPresupuesto>();

    public Double valorTotal(){
        return items.stream().mapToDouble(p->p.valorTotal()).sum();
    }

    public void agregarItem(ItemPresupuesto unItem){
        this.items.add(unItem);
    }

    public List<ItemPresupuesto> getItems() {return items;}
}
*/