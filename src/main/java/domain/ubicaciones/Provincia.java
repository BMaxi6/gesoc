package domain.ubicaciones;

import repositorios.Persistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="provincia")
public class Provincia extends Persistente {

    @Column(name="idProvincia")
    private String idProvincia;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_provincia")
    private List<Ciudad> ciudades;

    public Provincia(String id, String nombre, List<Ciudad> ciudades) {
        this.idProvincia = id;
        this.nombre = nombre;
        this.ciudades = ciudades;
    }

    public Provincia(){}

    public String getIdProvincia() {
        return idProvincia;
    }

    public void setId(String id) {
        this.idProvincia = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
}
