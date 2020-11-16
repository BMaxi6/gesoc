package domain.Pagos;

import javax.persistence.*;

@Entity
@DiscriminatorValue("tarjeta_debito")
public class TarjetaDebito extends MedioDePago{
    @Column(name="numero_tarjeta")
    private String numeroTarjeta;

    public TarjetaDebito(){
        super(TipoMedioPago.TARJETADEDEBITO);
    }
    public TarjetaDebito(String numero){
        super(TipoMedioPago.TARJETADEDEBITO);
        this.numeroTarjeta=numero;

    }

    @Override
    public String getInfo(){
        return this.descripcion +" "+ "Número de tarjeta: " + this.numeroTarjeta;
    }
}
