package domain.Pagos;

import domain.monedas.Moneda;
import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="pago")
public class Pago extends Persistente {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_medio_de_pago", referencedColumnName = "id")
    private MedioDePago medioDePago;

    @Column(name="dato")
    private String dato;

    @Column(name="numero_de_pago")
    private long numeroPago;

    @Column(name="monto")
    private Double montoPago;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_moneda", referencedColumnName = "id")
    private Moneda moneda;

    public Pago(){}

    public Pago(MedioDePago medioDePago, String dato, long numeroPago, Double montoPago, Moneda moneda) {
        this.medioDePago = medioDePago;
        this.dato = dato;
        this.numeroPago = numeroPago;
        this.montoPago = montoPago;
        this.moneda = moneda;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public long getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(long numeroPago) {
        this.numeroPago = numeroPago;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public void registrarPago(MedioDePago medioPago, Double monto){
        this.montoPago = monto;
        this.medioDePago = medioPago;
    }

    public void setMedioDePago(MedioDePago medioDePago){
        this.medioDePago = medioDePago;
    }

    public void setMontoPago(Double unMonto){
        this.montoPago = unMonto;
    }

    public MedioDePago getMedioDePago(){ return this.medioDePago; }

    public Double getMontoPago(){ return this.montoPago; }


}
