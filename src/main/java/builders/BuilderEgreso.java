package builders;

import domain.Egresos.DocumentoComercial;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Egresos.OperacionDeEgreso;
import domain.Items.Item;
import domain.Pagos.Pago;
import domain.Personas.Proveedor;
import domain.monedas.Moneda;
import domain.validadorDeTransparencia.CriterioDeSeleccionPresupuesto;

import java.time.LocalDate;
import java.util.List;

public class BuilderEgreso {

    OperacionDeEgreso operacion= new OperacionDeEgreso();
    public BuilderEgreso numeroOp(int numeroOp){
        operacion.setNumeroOp(numeroOp);
        return this;
    }
    public BuilderEgreso fecha(LocalDate date){
        if(date!=null){
            operacion.setFecha(date);
        }else{
            operacion.setFecha(LocalDate.now());
        }
        return this;
    }
    public BuilderEgreso valorTotal(Double valorTotal){
        operacion.setValorTotal(valorTotal);
        return this;
    }
    public BuilderEgreso organizacion(EntidadJuridica organizacion){
        operacion.setOrganizacion(organizacion);
        return this;
    }
    public BuilderEgreso items(List<Item> items){
        operacion.setItems(items);
        return this;
    }
    public BuilderEgreso cantidadPresupuestosRequeridos(int cant){
        operacion.setCantidadPresupuestosRequeridos(cant);
        return this;
    }
    public BuilderEgreso proveedor(Proveedor proveedor){
        operacion.setProveedor(proveedor);
        return this;
    }
    public BuilderEgreso documentoComercial(DocumentoComercial doc){
        operacion.setDocComercial(doc);
        return this;
    }
    public BuilderEgreso moneda(Moneda moneda){
        operacion.setMoneda(moneda);
        return this;
    }
    public BuilderEgreso entidadJuridica(EntidadJuridica org){
        operacion.setOrganizacion(org);
        return this;
    }

    public BuilderEgreso criterioSeleccionPresupuesto(CriterioDeSeleccionPresupuesto criterio){
        operacion.setCriterioSelecc(criterio);
        return this;
    }
    public BuilderEgreso pago(Pago pago){
        operacion.setPago(pago);
        return this;
    }
    public OperacionDeEgreso build(){
        return this.operacion;
    }
    public BuilderEgreso() {

    }
}
