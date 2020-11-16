package domain.Pagos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static domain.Pagos.TipoMedioPago.CHEQUE;

@Entity
@DiscriminatorValue("cheque")
public class Cheque extends MedioDePago{
    public Cheque(){
        super(CHEQUE);
    }
}
