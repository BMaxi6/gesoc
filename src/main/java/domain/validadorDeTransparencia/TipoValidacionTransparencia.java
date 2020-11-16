package domain.validadorDeTransparencia;

import domain.Egresos.OperacionDeEgreso;

public interface TipoValidacionTransparencia {

    public Boolean validar(OperacionDeEgreso op);
    public String mensajeParaError();
}
