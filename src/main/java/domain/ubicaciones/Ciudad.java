package domain.ubicaciones;

import repositorios.Persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ciudad")
public class Ciudad extends Persistente {

    @Column(name="id_ciudad")
    private String idCiudad;

    @Column(name="nombre")
    private String nombre;

    public Ciudad(String id, String nombre) {
        this.idCiudad = id;
        this.nombre = nombre;
    }
    public Ciudad(){}

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setId(String id) {
        this.idCiudad = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
