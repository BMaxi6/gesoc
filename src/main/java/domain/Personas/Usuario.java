package domain.Personas;
import domain.Categorizacion.Criterio;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import bandejaDeMensajes.BandejaDeMensajes;

import javax.persistence.*;

@Entity
@DiscriminatorValue("usuario")
public class Usuario extends Ingresado {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_entidad_juridica", referencedColumnName = "id")
    private EntidadJuridica organizacion;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_bandeja_mensajes")
    private BandejaDeMensajes bandejaDeMensajes = new BandejaDeMensajes();

    public Usuario(String nombre, EntidadJuridica organizacion) {
        super(nombre);
        this.organizacion = organizacion;
    }
    public Usuario(String nombre){
    super(nombre);

    }
    public Usuario(){}

    // ------------------------ Funciones del usuario ------------------------ //



    public void agregarCriterio(Criterio criterio){
        this.organizacion.agregarCriterio(criterio);
    }


    // --------------   ---------- Getter y Setter ------------------------ //

    //public void filtrarBandejaDeMensajespor(unCriterio){ }

    protected EntidadJuridica getEntidadJuridica() {
        return organizacion;
    }

    public BandejaDeMensajes getBandejaDeMensajes() {
        return bandejaDeMensajes;
    }

    public EntidadJuridica getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(EntidadJuridica organizacion) {
        this.organizacion = organizacion;
    }

    public void setBandejaDeMensajes(BandejaDeMensajes bandejaDeMensajes) {
        this.bandejaDeMensajes = bandejaDeMensajes;
    }
}