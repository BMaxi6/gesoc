package bandejaDeMensajes;

import java.time.LocalDate;

public class CriterioMensajeFecha implements CriterioMensaje{

    private LocalDate fechaFiltrar;

    public CriterioMensajeFecha(LocalDate fechaAFiltrar) {
        this.fechaFiltrar = fechaAFiltrar;
    }

    @Override
    public Boolean cumpleCriterio(Mensaje msj) {
        return (msj.getFechaCreacion() == fechaFiltrar);
    }
}
