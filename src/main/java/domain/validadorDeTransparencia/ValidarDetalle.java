package domain.validadorDeTransparencia;

import domain.Items.ItemPresupuesto;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;

import java.util.Optional;

public class ValidarDetalle implements TipoValidacionTransparencia{
    private Optional<Presupuesto> presupuestoElegido;

    @Override
    public Boolean validar(OperacionDeEgreso op) {
        presupuestoElegido = op.getPresupuestos().stream().filter(x->x.getElegido().equals(Boolean.TRUE)).findFirst();
        try{
            return presupuestoElegido.get().getItems().stream().allMatch(ItemPresupuesto::itemAsociadoEsIgual);
        }catch (Exception e){
            return true;
        }

    }
    public String mensajeParaError(){
        return "Falló la validación por detalle";
    }
}
