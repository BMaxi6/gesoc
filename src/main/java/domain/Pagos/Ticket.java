package domain.Pagos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ticket")
public class Ticket extends MedioDePago{
    public Ticket(){
        super(TipoMedioPago.TICKET);
    }
}
