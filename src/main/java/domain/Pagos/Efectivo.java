package domain.Pagos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static domain.Pagos.TipoMedioPago.EFECTIVO;

@Entity
@DiscriminatorValue("efectivo")
public class Efectivo extends MedioDePago{
    public Efectivo(){
        super(EFECTIVO);
    }
}
