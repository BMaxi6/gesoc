package controllers;

import builders.BuilderEgreso;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Categorizacion.Categoria;
import domain.Egresos.DocumentoComercial;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.TipoDocumentoComercial;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.Item;
import domain.Pagos.*;
import domain.Personas.Proveedor;
import domain.Personas.Usuario;
import domain.monedas.Moneda;
import domain.validadorDeTransparencia.CriterioDeSeleccionPresupuesto;
import domain.validadorDeTransparencia.DescriptorCriterioDeSeleccionPresupuesto;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.crypto.spec.OAEPParameterSpec;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdicionEgresoController implements Controlador {

    public ModelAndView mostrarEdicionEgreso(Request request, Response response){
        try{
        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        List<Moneda> monedas = FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class).buscarTodos();
        //List<String> mediosDePago = TipoMedioPago.obtenerTodos();
        List<String> tiposDePago = TipoDePago.obtenerTodos();
        List<String> opcionesDePago = OpcionDePago.obtenerTodos();

        boolean revisor = false;
        if(egreso.getRevisores().contains(usuario)){
            revisor = true;
        }


        Map<String, Object> parametros = new HashMap<>();

        String infoMedioPago= egreso.getPago().getMedioDePago().getInfo();

        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("monedas", monedas);
        parametros.put("info_medio_pago", infoMedioPago);
        parametros.put("moneda", egreso.getMoneda());
        //parametros.put("mediosDePago", mediosDePago);
        parametros.put("opciones_de_pago", opcionesDePago);
        parametros.put("tipos_de_pago", tiposDePago);
        parametros.put("tipos_documentos", TipoDocumentoComercial.obtenerTodos());
        parametros.put("revisor", revisor);
        if(egreso.getDocComercial()!=null)
            parametros.put("tipo_documento", egreso.getDocComercial().getTipo().toString());
        if(egreso.getCriterioSelecc()!=null){
            parametros.put("criterio_de_seleccion", egreso.getCriterioSelecc().getDescriptor().toString());
        }

        parametros.put("egreso", egreso);

        AsistenteDeModales.configurarParametros(this,request,parametros);

        return new ModelAndView(parametros, "editar_egreso.hbs");}catch(Exception e){
            return ControllerError.mostrarErrorInesperado(request,response);
        }
    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_edicion_egreso",true);
        request.session().attribute("aviso_modal_edicion_egreso", mensaje);
        request.session().attribute("modal_titulo_edicion_egreso", titulo);
        request.session().attribute("modal_color_edicion_egreso", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_edicion_egreso")==null)
            return false;
        return request.session().attribute("alerta_edicion_egreso");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_edicion_egreso");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_edicion_egreso");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_edicion_egreso");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_edicion_egreso",false);
    }


/*public Response editarCategoriasEgreso(Request request, Response response){

        System.out.println("ENTRE");
        int idEgreso;
        List<IntDeserializable> idsCategorias;

        try{
            idEgreso = Integer.parseInt(request.queryParams("idEgreso"));
            System.out.println("id egreso" + idEgreso);
            Type listType = new TypeToken<ArrayList<IntDeserializable>>(){}.getType();
            System.out.println("El json que me llego: " + request.queryParams("lectura_categorias"));
            idsCategorias = new Gson().fromJson(request.queryParams("lectura_categorias"), listType);

        }catch(Exception e){

            this.setearModalAlerta(request, "Error en la modificación de categorías","Ha ocurrido un problema con la actualización de las categorías de su operación de egreso. Verifique los datos ingresados.", AsistenteDeColores.getError()  );
            response.redirect("/gesoc/categorizar_egreso");
            System.out.println("-----------------------ERROR ACTUALIZACIÓN EGRESO---------------");
            return response;
        }
        List<Categoria> categorias = new ArrayList<>();
        for(int i = 0; i<idsCategorias.size(); i++){
            Categoria categoria = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscar(idsCategorias.get(i).getValor());
            categorias.add(categoria);
        }

        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);
        egreso.setCategorias(categorias);
        Repositorio<OperacionDeEgreso> repoEgresos=FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).modificar(egreso);



        response.redirect("/gesoc/categorizar_egreso");
        this.setearModalAlerta(request,"Carga exitosa","La modificación de la operación de egreso ha sido exitosa.", AsistenteDeColores.getExito());
        return response;
    }*/

 public Response editarEgreso(Request request, Response response) throws IOException {
     int idUsuario= request.session().attribute("usuario_id");
     Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);

        Repositorio<OperacionDeEgreso> repoEgresos= FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        int idEgreso=Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso=repoEgresos.buscar(idEgreso);
        int numeroPago;


        String descPago, pathDocComercial;
        TipoDocumentoComercial tipoDocComercial;
        String revisor, numeroDocComercial;
        try{
            numeroPago=Integer.parseInt(request.queryParams("numero_pago"));

            descPago= request.queryParams("descripcion_pago");
            tipoDocComercial = TipoDocumentoComercial.toTipoDocumentoComercial(request.queryParams("tipo_doc_comercial"));
            numeroDocComercial= request.queryParams("nro_doc_comercial");
            pathDocComercial=request.queryParams("archivo_doc_comercial");


            revisor = request.queryParams("revisor");


        }catch(Exception e){
            response.redirect("/gesoc/editar_egreso?id_egreso=" + idEgreso);
            this.setearModalAlerta(request, "Error en la edición de la Operación de Egreso.", "Ha ocurrido un error al confirmar la edición de la operación, revise los datos cargados y vuelva a intentar.",AsistenteDeColores.getError());

            return  response;
        }



        egreso.getPago().setNumeroPago(numeroPago);

        egreso.getPago().setDato(descPago);
        if(egreso.getDocComercial()==null){
            egreso.setDocComercial(new DocumentoComercial());
        }
        egreso.getDocComercial().setTipo(tipoDocComercial);
        egreso.getDocComercial().setNumeroDocumento(numeroDocComercial);
        egreso.getDocComercial().setPath(pathDocComercial);
        egreso.getDocComercial().setPath(ArchivoController.obtenerNombreArchivo(pathDocComercial));



     if(!revisor.equals("si")){
         egreso.eliminarRevisor(usuario);
     }

     repoEgresos.modificar(egreso);
     if(egreso.getDocComercial().getPath()!=null){
         egreso.getDocComercial().setUbicacion(ArchivoController.asociarArchivoAEgreso(egreso.getId(), usuario.getOrganizacion().getId(), egreso.getDocComercial().getPath()));
         repoEgresos.modificar(egreso);
     }

     this.setearModalAlerta(request, "Éxito en la edición de la Operación de Egreso.", "La operación ha sido editada exitosamente.",AsistenteDeColores.getExito());
     response.redirect("/gesoc/editar_egreso?id_egreso=" + idEgreso);
        return response;
 }
}
