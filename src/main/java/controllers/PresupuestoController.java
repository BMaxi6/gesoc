package controllers;

import DTOs.DocumentoComercialDto;
import DTOs.EgresoCategoriasDTO;
import DTOs.ItemPresupuestoDto;
import DTOs.PresupuestoCategoriasDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Categorizacion.Categoria;
import domain.Categorizacion.Criterio;
import domain.Egresos.DocumentoComercial;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;
import domain.Egresos.TipoDocumentoComercial;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.Item;
import domain.Items.ItemPresupuesto;
import domain.Personas.Proveedor;
import domain.Personas.Usuario;
import domain.monedas.Moneda;
import domain.ubicaciones.Ciudad;
import domain.ubicaciones.Pais;
import domain.ubicaciones.Provincia;
import org.springframework.cglib.core.Local;
import repositorios.CriteriaQueryFactory;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PresupuestoController implements Controlador{
    public ModelAndView mostrarCargaPresupuesto(Request request, Response response) {
        int idUsuario= request.session().attribute("usuario_id");

        //List<Prespuesto> presupuestos = FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class).buscarTodos();//ordenar alfabeticamente por nombre
        //List<OperacionDeEgreso> egresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos();

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        List<Pais> paises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class).buscarTodos();
        List<Provincia> provincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class).buscarTodos();
        List<Ciudad> ciudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class).buscarTodos();
        List<Categoria> categoriasEntidad = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscarTodos();
        List<OperacionDeEgreso> egresosEntidad = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarGrupo(new CriteriaQueryFactory().busquedaPorAtributo(OperacionDeEgreso.class,"organizacion", usuario.getOrganizacion()));

        List<String> tiposDocumento= TipoDocumentoComercial.obtenerTodos();

        EntidadJuridica entidadUsuario = usuario.getOrganizacion();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("paises", paises);
        parametros.put("provincias", provincias);
        parametros.put("ciudades", ciudades);
        parametros.put("proveedores", entidadUsuario.getProveedores());
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("categoriasEntidad", categoriasEntidad);
        parametros.put("egresosEntidad", egresosEntidad);
        parametros.put("tipos_documentos", tiposDocumento);

        AsistenteDeModales.configurarParametros(this,request,parametros);

        return new ModelAndView(parametros, "cargar_presupuesto.hbs");
    }

    public ModelAndView mostrarCargaPresupuestoConEgreso(Request request, Response response) {
        int idUsuario= request.session().attribute("usuario_id");
        int idEgreso=Integer.parseInt(request.params("id_egreso"));
        OperacionDeEgreso egresoAsociado=FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        List<Pais> paises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class).buscarTodos();
        List<Provincia> provincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class).buscarTodos();
        List<Ciudad> ciudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class).buscarTodos();
        List<Categoria> categoriasEntidad = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscarTodos();
        List<OperacionDeEgreso> egresosEntidad = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos();
        List<String> tiposDocumento= TipoDocumentoComercial.obtenerTodos();



        EntidadJuridica entidadUsuario = usuario.getOrganizacion();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("paises", paises);
        parametros.put("provincias", provincias);
        parametros.put("ciudades", ciudades);
        parametros.put("proveedores", entidadUsuario.getProveedores());
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("categoriasEntidad", categoriasEntidad);
        parametros.put("egresosEntidad", egresosEntidad);
        parametros.put("egreso_asociado", egresoAsociado);
        parametros.put("tipos_documentos", tiposDocumento);
        return new ModelAndView(parametros, "cargar_presupuesto.hbs");
    }

    public Response nuevoProveedor(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        String path=new Formatter().format("/gesoc/cargar_presupuesto", idUsuario).toString();

        return new EntidadJuridicaController().crearNuevoProveedor(request, response,path,path);
    }



    public ModelAndView mostrarListadoPresupuestos(Request request, Response response){

        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        List<Presupuesto> presupuestosOrganizacion = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscarGrupo(new CriteriaQueryFactory().busquedaPorAtributo(Presupuesto.class,"organizacion",entidadUsuario));


        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("presupuestosOrganizacion", presupuestosOrganizacion);

        return new ModelAndView(parametros, "presupuestos.hbs");
    }

    public Response nuevoPresupuesto(Request request, Response response) throws IOException {
        Gson gson = new Gson();
        int idUsuario= request.session().attribute("usuario_id");
        EntidadJuridica organizacion= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario).getOrganizacion();
        int idProveedor, idEgreso;
        boolean seleccionadoParaCompra;
        //Double valorTotal;
        Proveedor proveedor;
        List<ItemPresupuesto> items;
        DocumentoComercialDto docDto= new DocumentoComercialDto();
        OperacionDeEgreso egreso;
        LocalDate fecha;
        try{
            String lectura_items= request.queryParams("lectura_items");
            String documento=request.queryParams("lectura_documento");
            System.out.println("----------------itemsssss");
            System.out.println(lectura_items);
            idEgreso=Integer.parseInt(request.queryParams("id_egreso"));
            fecha= LocalDate.parse(request.queryParams("fecha"));
            Type listType = new TypeToken<ArrayList<ItemPresupuesto>>(){}.getType();
            items = gson.fromJson(lectura_items, listType);
            docDto= gson.fromJson(documento, DocumentoComercialDto.class);
            if(request.queryParams("seleccionado").equals("Si")){
                seleccionadoParaCompra=true;
            }else{
                seleccionadoParaCompra=false;
            }
            //valorTotal= Double.parseDouble(request.queryParams("valor_total"));
            idProveedor= Integer.parseInt(request.queryParams("proveedor_id"));

        }catch(Exception e){
            response.redirect("/gesoc/cargar_presupuesto");
            this.setearModalAlerta(request, "Error en la carga del presupuesto", "Hubo un error al carga el presupuesto. Revise los datos cargados y vuelva intentar.", AsistenteDeColores.getError());
            return response;
        }
        Repositorio<Item> repoItems =FactoryRepositorio.instancia().obtenerRepositorio(Item.class);
        for(ItemPresupuesto item: items){
            item.setItemAsociado(repoItems.buscar(item.getItemAsociado().getId()));
        }


        Repositorio<OperacionDeEgreso> repoEgreso=FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        egreso= repoEgreso.buscar(idEgreso);
        proveedor= FactoryRepositorio.instancia().obtenerRepositorio(Proveedor.class).buscar(idProveedor);

        Presupuesto presupuesto= new Presupuesto();
        DocumentoComercial docComercial= docDto.toDocumentoDomercial();
        docComercial.setEmisor(proveedor);
        docComercial.setReceptor(organizacion);
        docComercial.setOperacion(egreso);
        docComercial.setPath(ArchivoController.obtenerNombreArchivo(docComercial.getPath()));

        presupuesto.setOperacion(egreso);
        presupuesto.setItems(items);
        presupuesto.setProveedorP(proveedor);
        presupuesto.setOrganizacion(organizacion);
        presupuesto.setElegido(seleccionadoParaCompra);
        presupuesto.setDocumento(docComercial);
        presupuesto.setFecha(fecha);

        Repositorio<Presupuesto> repoPresupuesto= FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class);
        repoPresupuesto.agregar(presupuesto);
        egreso.agregarPresupuesto(presupuesto);
        repoEgreso.modificar(egreso);

        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        if(presupuesto.getDocumento().getPath()!=null){
            presupuesto.getDocumento().setUbicacion(ArchivoController.asociarArchivoAPresupuesto(presupuesto.getId(), usuario.getOrganizacion().getId(), presupuesto.getDocumento().getPath()));
            repoPresupuesto.modificar(presupuesto);
        }


        this.setearModalAlerta(request, "Cargar de presupuesto exitosa", "El presupuesto se ha cargado correctamente.", AsistenteDeColores.getExito());
        response.redirect("/gesoc/cargar_presupuesto");
        return response;
    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_carga_presupuesto",true);
        request.session().attribute("aviso_modal_carga_presupuesto", mensaje);
        request.session().attribute("modal_titulo_carga_presupuesto", titulo);
        request.session().attribute("modal_color_carga_presupuesto", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_carga_presupuesto")==null)
            return false;
        return request.session().attribute("alerta_carga_presupuesto");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_carga_presupuesto");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_carga_presupuesto");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_carga_presupuesto");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_carga_presupuesto",false);
    }

    public String obtenerPresupuestosAsociados (Request request, Response response){
        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        Gson gson = new Gson();
        String jsonPresupuestos = gson.toJson(egreso.getPresupuestos());
        response.type("application/json");
        return jsonPresupuestos;
    }

    //-------------------------PRESUPUESTOS EN MIS OPERACIONES

    public  String obtenerItems (Request request, Response response){

        int idPresupuesto = Integer.parseInt(request.queryParams("id_presupuesto"));
        Presupuesto presupuesto = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscar(idPresupuesto);

        Type listType = new TypeToken<ArrayList<ItemPresupuestoDto>>(){}.getType();
        List<ItemPresupuestoDto> items = presupuesto.getItems().stream().map(i -> ItemPresupuestoDto.toItemPresupuestoDto(i)).collect(Collectors.toList());
        Gson gson = new Gson();
        String jsonItems = gson.toJson(items, listType);
        response.type("application/json");

        return jsonItems;
    }

    public  String obtenerCategorias (Request request, Response response){

        int idPresupuesto = Integer.parseInt(request.queryParams("id_presupuesto"));
        Presupuesto presupuesto = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscar(idPresupuesto);

        List<Categoria> categorias = presupuesto.getCategorias();
        Gson gson = new Gson();
        String jsonCategorias = gson.toJson(categorias);
        response.type("application/json");

        return jsonCategorias;
    }
}