package domain.Pagos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("atm")
public class ATM extends MedioDePago{
    public ATM(){
        super(TipoMedioPago.ATM);
    }
}
