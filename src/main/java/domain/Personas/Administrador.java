package domain.Personas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("administrador")
public class Administrador extends Ingresado {


    public Administrador(String nombre, Integer idIngresado) {
        super(nombre);
    }

    public Administrador(){}



    // -------------------------- Getter y Setter -------------------------- //

}
