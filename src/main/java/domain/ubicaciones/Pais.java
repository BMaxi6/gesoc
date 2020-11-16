package domain.ubicaciones;

import repositorios.Persistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="pais")
public class Pais extends Persistente {

    @Column(name="id_pais")
    private String idPais;

    @Column(name="nombre")
    private String nombre;

    @Column(name="id_moneda")
    private String idMoneda;

    @Column(name="zona_tiempo")
    private String zonaTiempo;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_pais")
    private List<Provincia> provincias;

    public Pais(String id, String nombre, String idMoneda, String zonaTiempo, List<Provincia> provincias) {
        this.idPais = id;
        this.nombre = nombre;
        this.idMoneda = idMoneda;
        this.zonaTiempo = zonaTiempo;
        this.provincias = provincias;
    }

    public Pais(){}

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getZonaTiempo() {
        return zonaTiempo;
    }

    public void setZonaTiempo(String zonaTiempo) {
        this.zonaTiempo = zonaTiempo;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }
}
