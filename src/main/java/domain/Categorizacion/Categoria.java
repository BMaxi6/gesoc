package domain.Categorizacion;

import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="categoria")
public class Categoria extends Persistente {

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;


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



    public Categoria(){

    }
}
