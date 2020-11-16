package bandejaDeMensajes;

import org.apache.commons.lang3.ObjectUtils;

public class CriterioMensajeLeido implements CriterioMensaje{

    @Override
    public Boolean cumpleCriterio(Mensaje msj) {
        return (msj.getFechaLeido() != null);
    }
}
