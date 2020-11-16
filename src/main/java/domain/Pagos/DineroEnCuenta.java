package domain.Pagos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static domain.Pagos.TipoMedioPago.DINEROENCUENTA;
@Entity
@DiscriminatorValue("dinero_en_cuenta")
public class DineroEnCuenta extends MedioDePago{
    public DineroEnCuenta(){
        super(DINEROENCUENTA);
    }
}
