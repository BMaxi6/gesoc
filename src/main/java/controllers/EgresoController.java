package controllers;

import DTOs.*;
import builders.BuilderEgreso;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Categorizacion.Categoria;
import domain.Categorizacion.Criterio;
import domain.Egresos.DocumentoComercial;
import domain.Egresos.OperacionDeEgreso;

import domain.Egresos.TipoDocumentoComercial;

import domain.Egresos.Presupuesto;

import domain.EntidadesOrganizacionales.CategoriaEmpresa.Rubro;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.Item;
import domain.Items.ItemPresupuesto;
import domain.Pagos.*;
import domain.Personas.Proveedor;
import domain.Personas.Usuario;
import domain.monedas.Moneda;
import domain.ubicaciones.Ciudad;
import domain.ubicaciones.Direccion;
import domain.ubicaciones.Pais;
import domain.ubicaciones.Provincia;
import domain.validadorDeTransparencia.CriterioDeSeleccionPresupuesto;
import domain.validadorDeTransparencia.DescriptorCriterioDeSeleccionPresupuesto;
import org.springframework.ui.Model;
import repositorios.CriteriaQueryFactory;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.print.Doc;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EgresoController implements Controlador{

    public ModelAndView mostrarCargaEgreso(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");

        List<Pais> paises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class).buscarTodos();//ordenar alfabeticamente por nombre
        List<Provincia> provincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class).buscarTodos();//ordenar alfabeticamente por nombre
        List<Ciudad> ciudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class).buscarTodos();//ordenar alfabeticamente por nombre
        List<Moneda> monedas = FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class).buscarTodos();
        List<String> mediosDePago = TipoMedioPago.obtenerTodos();
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();
        List<Proveedor> proveedores = FactoryRepositorio.instancia().obtenerRepositorio(Proveedor.class).buscarTodos();
        List<Proveedor> proveedoresOrganizacion=proveedores.stream().filter(proveedor -> entidadUsuario.getProveedores().contains(proveedor)).collect(Collectors.toList());
        List<DescriptorCriterioDeSeleccionPresupuesto> criterios= Arrays.asList(DescriptorCriterioDeSeleccionPresupuesto.values());
        List<String> criteriosPresupuestoString=criterios.stream().map(e->e.toString()).collect(Collectors.toList());
        List<String> tiposDePago= TipoDePago.obtenerTodos();
        List<String> opcionesDePago= OpcionDePago.obtenerTodos();
        System.out.println("----------------------------------------------cant proveedores");
        System.out.println(proveedoresOrganizacion.size());


        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("paises", paises);
        parametros.put("provincias", provincias);
        parametros.put("ciudades", ciudades);
        parametros.put("monedas", monedas);
        parametros.put("mediosDePago", mediosDePago);
        parametros.put("opciones_de_pago", opcionesDePago);
        parametros.put("tipos_de_pago", tiposDePago);
        parametros.put("proveedores", proveedoresOrganizacion);
        parametros.put("criterios_presupuesto", criteriosPresupuestoString);
        parametros.put("tipos_documentos", TipoDocumentoComercial.obtenerTodos());


       AsistenteDeModales.configurarParametros(this,request,parametros);


        return new ModelAndView(parametros, "cargar_egreso.hbs");
    }

    public Response nuevoEgreso(Request request, Response response) throws IOException, ServletException {
        System.out.println("-----------------------------");
        System.out.println(request.queryParams("lectura_items"));

        int idUsuario= request.session().attribute("usuario_id");

        int nroOperacionEgreso, cantPresupuestosRequeridos, idProveedor, idMoneda;
        TipoMedioPago tipoMedioPago;
        long numeroPago;
        LocalDate fecha;
        //Double valorTotal;
        String  nroDocComercial, pathDocComercial, revisor;
        TipoDocumentoComercial tipoDocComercial;
        List<Item> items = new ArrayList<Item>();
        CriterioDeSeleccionPresupuesto criterioPresupuesto;
        EntidadJuridica entidad;
        String descripcionPago;

        MedioDePago medioDePago;
        try{
            nroOperacionEgreso = Integer.parseInt(request.queryParams("nro_operacion_egreso"));
            cantPresupuestosRequeridos = Integer.parseInt(request.queryParams("cantidad_presupuestos_requeridos"));
            idProveedor = Integer.parseInt(request.queryParams("proveedor_id"));
            fecha = LocalDate.parse(request.queryParams("fecha"));
            //valorTotal = Double.parseDouble(request.queryParams("valor_total"));

            descripcionPago=request.queryParams("descripcion_pago");
            idMoneda = Integer.parseInt(request.queryParams("moneda_id"));
            tipoDocComercial = TipoDocumentoComercial.toTipoDocumentoComercial(request.queryParams("tipo_doc_comercial"));
            nroDocComercial = request.queryParams("nro_doc_comercial");
            numeroPago= Long.parseLong(request.queryParams("numero_pago"));
            pathDocComercial = request.queryParams("archivo_doc_comercial");
            Type listType = new TypeToken<ArrayList<Item>>(){}.getType();
            items = new Gson().fromJson(request.queryParams("lectura_items"), listType);
            revisor = request.queryParams("revisor");
            criterioPresupuesto= DescriptorCriterioDeSeleccionPresupuesto.MENOR_VALOR.obtenerCriterioDeSeleccionPresupuesto(request.queryParams("criterio_seleccion_presupuesto"));
            entidad= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario).getOrganizacion();

            tipoMedioPago = TipoMedioPago.toTipoMedioPago(request.queryParams("medioDePago_id"));
            String numeroTarjeta;

            switch(tipoMedioPago){
                case TARJETADECREDITO:
                    numeroTarjeta= request.queryParams("numero_tarjeta");
                    OpcionDePago opcionDePago= OpcionDePago.toOpcionDePago(request.queryParams("pagos_tarjeta"));
                    TipoDePago tipoDePago= TipoDePago.toTipoDePago(request.queryParams("tipo_pagos_tarjeta"));
                    medioDePago= new TarjetaCredito(numeroTarjeta,opcionDePago,tipoDePago);
                    break;
                case TARJETADEDEBITO:
                    numeroTarjeta= request.queryParams("numero_tarjeta");
                    medioDePago=new TarjetaDebito(numeroTarjeta);
                    break;
                default:
                    medioDePago=MedioDePago.instanciarMedioDePago(tipoMedioPago);
                    break;

            }

        }catch(Exception e){

            this.setearModalAlerta(request, "Error en la carga de operación de egreso","Ha ocurrido un problema con la carga de su operación de egreso. Verifique los datos ingresados.", AsistenteDeColores.getError()  );
            response.redirect("/gesoc/cargar_egreso");
            System.out.println("-----------------------ERROR CARGA EGRESO---------------");
            return response;
        }


        //List<Item> listaItems = request.queryParams("lista_items");
        //MedioDePago medioDePago= FactoryRepositorio.instancia().obtenerRepositorio(MedioDePago.class).buscar(idMedioPago);





        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();
        Repositorio<Moneda> repoMonedas= FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class);
        Moneda moneda = repoMonedas.buscar(idMoneda);
        Repositorio<Proveedor> repoProveedores= FactoryRepositorio.instancia().obtenerRepositorio(Proveedor.class);
        Proveedor proveedor = repoProveedores.buscar(idProveedor);
        Repositorio<OperacionDeEgreso> repoEgresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        Repositorio<DocumentoComercial> repoDocsComercial = FactoryRepositorio.instancia().obtenerRepositorio(DocumentoComercial.class);




        Pago pago= new Pago(medioDePago, descripcionPago, numeroPago, items.stream().mapToDouble(i->i.valorTotal()).sum(), moneda);



        OperacionDeEgreso operacion = new BuilderEgreso().cantidadPresupuestosRequeridos(cantPresupuestosRequeridos).fecha(fecha).
                numeroOp(nroOperacionEgreso).items(items).criterioSeleccionPresupuesto(criterioPresupuesto).moneda(moneda).proveedor(proveedor).entidadJuridica(entidad).pago(pago).build();

        DocumentoComercial documentoComercial;

            documentoComercial=new DocumentoComercial(nroDocComercial, tipoDocComercial, operacion,ArchivoController.obtenerNombreArchivo(pathDocComercial));



        operacion.setDocComercial(documentoComercial);


        if(revisor.equals("si")){
            operacion.agregarRevisor(usuario);
        }

        repoEgresos.agregar(operacion);

        if(operacion.getDocComercial().getPath()!=null){
            operacion.getDocComercial().setUbicacion(ArchivoController.asociarArchivoAEgreso(operacion.getId(), usuario.getOrganizacion().getId(), operacion.getDocComercial().getPath()));
            repoEgresos.modificar(operacion);
        }


        response.redirect("/gesoc/cargar_egreso");
         this.setearModalAlerta(request,"Carga exitosa","La carga de la operación de egreso ha sido exitosa.", AsistenteDeColores.getExito());
        return response;
    }

    public ModelAndView mostrarListadoEgresos(Request request, Response response){

        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        //List<OperacionDeEgreso> egresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos();
        //List<OperacionDeEgreso> egresosOrganizacion = egresos.stream().filter(egreso -> egreso.getOrganizacion().getId() == entidadUsuario.getId()).collect(Collectors.toList()); //PONER UN EQUALS, EN LUGAR DEL GETID
        List<OperacionDeEgreso> egresosOrganizacion = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarGrupo(new CriteriaQueryFactory().busquedaPorAtributo(OperacionDeEgreso.class, "organizacion",entidadUsuario));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("egresosOrganizacion", egresosOrganizacion);

        return new ModelAndView(parametros, "operaciones_de_egreso.hbs");
    }

    public ModelAndView mostrarCategorizacionEgreso(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();

        List<OperacionDeEgreso> egresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos();
        List<OperacionDeEgreso> egresosOrganizacion = egresos.stream().filter(egreso -> egreso.getOrganizacion().getId() == entidadUsuario.getId()).collect(Collectors.toList());
        List<Categoria> categorias = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscarTodos();
        List<Criterio> criterios = FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class).buscarTodos();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("egresos",egresosOrganizacion);
        parametros.put("categoriasTotales", categorias);
        parametros.put("criterios", criterios);
        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "asociacion_egreso_categoria.hbs");
    }

    public ModelAndView mostrarInfoEgreso (Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();
        Map<String, Object> parametros = new HashMap<>();



        return new ModelAndView(parametros, "asociacion_egreso_categoria.hbs");
    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_carga_egreso",true);
        request.session().attribute("aviso_modal_carga_egreso", mensaje);
        request.session().attribute("modal_titulo_carga_egreso", titulo);
        request.session().attribute("modal_color_carga_egreso", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_carga_egreso")==null)
            return false;
        return request.session().attribute("alerta_carga_egreso");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_carga_egreso");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_carga_egreso");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_carga_egreso");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_carga_egreso",false);
    }

    public String obtenerItem(Request request, Response response){
        System.out.println("llegoooooo");
        int idItem=Integer.parseInt(request.queryParams("id_item"));
        System.out.println(idItem);
        Item item=FactoryRepositorio.instancia().obtenerRepositorio(Item.class).buscar(idItem);

        System.out.println(item.getDescripcion());
        String json=(new Gson()).toJson(item);
        System.out.println(json);
        response.type("application/json");
        return json;
    }


    public  String obtenerEgreso (Request request, Response response){


        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        System.out.println("id egreso: " + idEgreso);
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        EgresoDto egresoDto= new EgresoDto(egreso);
        Gson gson = new Gson();
        String jsonEgreso = gson.toJson(egresoDto, EgresoDto.class);
        response.type("application/json");
        System.out.println("json" + jsonEgreso);
        return jsonEgreso;

    }

    public  String obtenerEgresoCategorias (Request request, Response response){

        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        EgresoCategoriasDTO egresoCategoriasDTO= new EgresoCategoriasDTO(egreso);
        Gson gson = new Gson();
        String jsonEgreso = gson.toJson(egresoCategoriasDTO, EgresoCategoriasDTO.class);
        response.type("application/json");
        System.out.println("json" + jsonEgreso);
        return jsonEgreso;

    }

    //--------------------------------------VISTA: OPERACIONES DE EGRESO-----------------------------------
    public  String obtenerPresupuestos (Request request, Response response){

        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        List<PresupuestoDTO> presupuestos = egreso.getPresupuestos().stream().map(p -> PresupuestoDTO.toPresupuestoDto(p)).collect(Collectors.toList());
        Gson gson = new Gson();
        String jsonPresupuestos = gson.toJson(presupuestos);
        response.type("application/json");

        return jsonPresupuestos;
    }

    public  String obtenerItems (Request request, Response response){

        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        List<ItemDto> itemsDto = egreso.getItems().stream().map(i -> ItemDto.toItemDto(i)).collect(Collectors.toList());
        Gson gson = new Gson();
        String jsonItems = gson.toJson(itemsDto);
        response.type("application/json");

        return jsonItems;
    }

    public  String obtenerCategorias (Request request, Response response){

        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);

        List<Categoria> categorias = egreso.getCategorias();
        Gson gson = new Gson();
        String jsonCategorias = gson.toJson(categorias);
        response.type("application/json");

        return jsonCategorias;
    }

    public String obtenerRevisores (Request request, Response response){
        int idEgreso = Integer.parseInt(request.queryParams("id_egreso"));
        OperacionDeEgreso egreso = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscar(idEgreso);
        List<Usuario> revisores = egreso.getRevisores();

        List<RevisorDto> revisoresDto = revisores.stream().map(i->RevisorDto.toRevisorDto(i)).collect(Collectors.toList());

        Type listType = new TypeToken<ArrayList<RevisorDto>>(){}.getType();
        Gson gson = new Gson();
        String jsonRevisores = gson.toJson(revisoresDto, listType);
        response.type("application/json");

        return jsonRevisores;
    }


    public Response editarCategoriasEgreso(Request request, Response response){

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
    }

}
