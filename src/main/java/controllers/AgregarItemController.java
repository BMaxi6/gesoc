package controllers;

import domain.Egresos.OperacionDeEgreso;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.Item;
import domain.Items.TipoDeItem;
import domain.Personas.Usuario;
import domain.monedas.Moneda;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgregarItemController {

    public Response nuevoItemEgreso(Request request, Response response){

        /*int idUsuario= Integer.parseInt(request.params("usuario_id"));
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);

        Integer cantidad = Integer.parseInt(request.queryParams("cantidad"));
        Double valorUnitario = Double.parseDouble(request.queryParams("valor_unitario"));
        Integer tipoItem = Integer.parseInt(request.queryParams("tipo_item"));
        String descripcion = request.queryParams("descripcion");

        Item item = new Item(cantidad, valorUnitario, tipoItem, descripcion);


*/
        return response;

    }
}
