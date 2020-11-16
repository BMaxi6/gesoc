package domain.Pagos;

import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="medio_de_pago")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_medio_pago")
public abstract class MedioDePago extends Persistente {

    @Column(name="tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoMedioPago tipo;

    @Column(name="descripcion")
    protected String descripcion;

    public MedioDePago(TipoMedioPago unTipo){
        this.tipo = unTipo;
        this.descripcion=this.tipo.toString();

    }

    public MedioDePago(){}
    public static MedioDePago instanciarMedioDePago(TipoMedioPago tipo){
        switch(tipo){
            case TARJETADECREDITO:
                return new TarjetaCredito();
            case TARJETADEDEBITO:
                return new TarjetaDebito();
            case CHEQUE:
                return new Cheque();
            case TICKET:
                return new Ticket();
            case DINEROENCUENTA:
                return new DineroEnCuenta();
            default:
                return null;
        }
    }
    public TipoMedioPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedioPago tipo) {
        this.tipo = tipo;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInfo(){
        return this.getDescripcion();
    }
}
