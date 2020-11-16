package bandejaDeMensajes;

import repositorios.FactoryRepositorio;
import repositorios.Persistente;
import repositorios.Repositorio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Entity
@Table(name="bandeja_de_mensajes")
public class BandejaDeMensajes extends Persistente {
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name= "id_bandeja_mensajes")
    private List<Mensaje> mensajes = new ArrayList<Mensaje>();

    public void recibirMensaje(Mensaje msj){
        Repositorio<BandejaDeMensajes> repoBandeja= FactoryRepositorio.obtenerRepositorio(BandejaDeMensajes.class);
        mensajes.add(msj);
        repoBandeja.modificar(this);
    }

    public List<Mensaje> filtrarBandejaDeMensajesPor(CriterioMensaje criterioFiltrar){
        return mensajes.stream().filter(criterioFiltrar::cumpleCriterio).collect(Collectors.toList());
    }

    public List<Mensaje> getMensajes(){
        return this.mensajes;
    }
}
