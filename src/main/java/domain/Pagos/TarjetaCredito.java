package domain.Pagos;

import javax.persistence.*;

@Entity
@DiscriminatorValue("tarjeta_credito")
public class TarjetaCredito extends MedioDePago{
    @Column(name="numero_tarjeta")
    private String numeroTarjeta;
    @Column(name="opcion_pago")
    @Enumerated(value = EnumType.STRING)
    private OpcionDePago opcionDePago;
    @Column(name="tipo_pago")
    @Enumerated(value = EnumType.STRING)
    private TipoDePago tipoDePago;

    public TarjetaCredito(){
        super(TipoMedioPago.TARJETADECREDITO);
    }

    public TarjetaCredito(String numero, OpcionDePago opcion,  TipoDePago tipo){
        super(TipoMedioPago.TARJETADECREDITO);
        this.numeroTarjeta=numero;
        this.opcionDePago=opcion;
        this.tipoDePago=tipo;
        this.descripcion=this.getTipo().toString() + " " + this.opcionDePago.toString() + " " + this.tipoDePago.toString();
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public OpcionDePago getOpcionDePago() {
        return opcionDePago;
    }

    public void setOpcionDePago(OpcionDePago opcionDePago) {
        this.opcionDePago = opcionDePago;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    @Override
    public String getInfo(){
        return this.descripcion +" "+ "Número de tarjeta: " + this.numeroTarjeta;
    }

}
