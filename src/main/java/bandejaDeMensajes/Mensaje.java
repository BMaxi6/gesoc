package bandejaDeMensajes;

import repositorios.Persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="mensaje")
public class Mensaje extends Persistente {

    @Column(name="mensaje")
    private String mensaje;

    @Column(name="baja_logica")
    private boolean bajaLogica=false;

    @Column(name="fecha_creacion")
    //@Convert(converter = LocalDateConverter.class)
    private LocalDate fechaCreacion;

    @Column(name="fecha_leido")
    //@Convert(converter = LocalDateConverter.class)
    private LocalDate fechaLeido = null;

    public Mensaje( String msj, LocalDate fechaCrear){
        this.mensaje = msj;
        this.fechaCreacion = fechaCrear;
    }

    public Mensaje(){

    };

    public boolean fueLeido(){
        return this.fechaLeido!=null;
    }

    public void enviate(BandejaDeMensajes bandeja){
        bandeja.recibirMensaje(this);
    }

    public String mensaje(){
        return this.mensaje;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaLeido() {
        return fechaLeido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaLeido(LocalDate fechaLeido) {
        this.fechaLeido = fechaLeido;
    }

    public boolean isBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    public void invalidar(){
        this.bajaLogica=true;
    }

    public boolean estaEntreFecha(LocalDate fechaInicio, LocalDate fechaFin){
        return this.fechaCreacion.isAfter(fechaInicio) && this.fechaCreacion.isBefore(fechaFin);
    }
}
