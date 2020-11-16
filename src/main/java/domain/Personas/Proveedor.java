package domain.Personas;

import repositorios.Persistente;
import domain.ubicaciones.Direccion;

import javax.persistence.*;

@Entity
@Table(name="proveedor")
public class Proveedor extends Persistente {

    @Column(name="nombre")
    private String nombre;

    @Column(name="cuit")
    private long cuit;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_direccion", referencedColumnName = "id")
    private Direccion direccion;

    public Proveedor(String unNombre, long cuitProveedor, Direccion direcc){
        this.nombre = unNombre;
        this.cuit = cuitProveedor;
        this.direccion = direcc;
    }

    public Proveedor(){}

    // ------------------------ Getter y Setter ------------------------ //


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long cuit) {
        this.cuit = cuit;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
