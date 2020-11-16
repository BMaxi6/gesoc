package domain.EntidadesOrganizacionales;

import repositorios.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="organizacion")
public class Organizacion extends Persistente {
    @Column(name="nombre")
    String nombre;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion")
    List<EntidadJuridica> entidadesJuridicas=new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="id_organizacion")
    List<EntidadBase> entidadesBase= new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<EntidadJuridica> getEntidadesJuridicas() {
        return entidadesJuridicas;
    }

    public void setEntidadesJuridicas(List<EntidadJuridica> entidadesJuridicas) {
        this.entidadesJuridicas = entidadesJuridicas;
    }

    public List<EntidadBase> getEntidadesBase() {
        return entidadesBase;
    }

    public void setEntidadesBase(List<EntidadBase> entidadesBase) {
        this.entidadesBase = entidadesBase;
    }

    public void agregarEntidadJuridica(EntidadJuridica entidad){
        entidadesJuridicas.add(entidad);
    }

    public void agregarEntidadBase(EntidadBase entidad){
        entidadesBase.add(entidad);
    }

}
