/*package domain.Detalles;
import domain.Items.Item;
import repositorios.Persistente;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="detalle")
public class Detalle extends Persistente{

    @Column(name="valor_total")
    private Double valorTotal;

    @OneToMany(mappedBy = "detalle", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Item> items=new ArrayList<Item>();

    void agregarItem(Item item){
        items.add(item);
    }
    public Double valorTotal(){
       return items.stream().mapToDouble(item -> item.valorTotal()).sum();
    }

}*/
