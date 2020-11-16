package domain.EntidadesOrganizacionales;

import repositorios.Persistente;
import domain.ubicaciones.Direccion;

import javax.persistence.*;

@Entity
@Table(name="entidad_base")
public class EntidadBase extends Persistente {

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_direccion", referencedColumnName = "id")
    private Direccion direccion;


    public EntidadBase(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }



    public EntidadBase(){}
}
