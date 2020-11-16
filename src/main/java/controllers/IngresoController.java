package controllers;

import DTOs.EgresoDto;
import DTOs.EgresoDtoFechaString;
import DTOs.IngresoDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.Item;
import domain.Personas.Usuario;
import domain.monedas.Moneda;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class IngresoController implements Controlador{
    public ModelAndView mostrarCargaIngreso(Request request, Response response) {
        int idUsuario= request.session().attribute("usuario_id");

        List<Moneda> monedas = FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class).buscarTodos();
        List<OperacionDeEgreso> egresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos();

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        List<OperacionDeEgreso> egresosOrganizacion = egresos.stream().filter(egreso-> egreso.getOrganizacion().getId()==usuario.getOrganizacion().getId() && egreso.noPoseeIngresoAsociado()).collect(Collectors.toList());
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("monedas", monedas);
        parametros.put("egresos", egresosOrganizacion);

        AsistenteDeModales.configurarParametros(this,request, parametros);
        return new ModelAndView(parametros, "cargar_ingreso.hbs");
    }

    public Response nuevoIngreso(Request request, Response response){
        System.out.println("lectura_egresos");
        Double valorTotal;
        String descripcion;
        int idMoneda, idUsuario;
        idUsuario= request.session().attribute("usuario_id");
        Type listType;
        List<IntDeserializable> idsEgresos;
        LocalDate fecha, fechaAceptabilidad;
        System.out.println("---------------------------");
        System.out.println(request.queryParams("lectura_egresos"));
        try{
            valorTotal = Double.parseDouble(request.queryParams("valor_total"));
            descripcion = request.queryParams("descripcion");
            idMoneda = Integer.parseInt(request.queryParams("moneda_id"));

            listType = new TypeToken<ArrayList<IntDeserializable>>(){}.getType();
            idsEgresos= new Gson().fromJson(request.queryParams("lectura_egresos"), listType);
            fecha = LocalDate.parse(request.queryParams("fecha"));
            fechaAceptabilidad = LocalDate.parse(request.queryParams("fecha_aceptabilidad"));
        }catch(Exception e){
            this.setearModalAlerta(request,"Error", "Ha ocurrido un porblema en la carga del ingreso. Revise los datos ingresados.", AsistenteDeColores.getError());
            response.redirect("/gesoc/cargar_ingreso");
            return response;
        }

        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();


        Repositorio<Moneda> repoMonedas = FactoryRepositorio.instancia().obtenerRepositorio(Moneda.class);
        Moneda moneda = repoMonedas.buscar(idMoneda);
        Repositorio<OperacionDeEgreso> repoEgresos= FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);


        List<OperacionDeEgreso> egresosAAsociar= idsEgresos.stream().map(id-> repoEgresos.buscar(id.valor)).collect(Collectors.toList());

        Ingreso ingreso = new Ingreso(valorTotal, entidadUsuario, descripcion, fechaAceptabilidad, fecha, moneda);
        Repositorio<Ingreso> repoIngresos = FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class);

        repoIngresos.agregar(ingreso);
        System.out.println("------------------------id ingreso");
        System.out.println(ingreso.getId());
        if(ingreso.puedoAsociarmeAEgresos(egresosAAsociar)){

            for(OperacionDeEgreso e : egresosAAsociar){
                e.setIngreso(ingreso);
                repoEgresos.modificar(e);
            }
            ingreso.setEgresos(egresosAAsociar);
            repoIngresos.modificar(ingreso);
            this.setearModalAlerta(request,"Éxito en la carga del ingreso", "El ingreso ha sido cargado con éxito.", AsistenteDeColores.getExito());
        }else{
            this.setearModalAlerta(request,"Error en la carga del ingreso", "El ingreso no puede ser asociado a los egresos seleccionados. Se ha cargado el ingreso pero no se realizó la asociación.", AsistenteDeColores.getError());
        }



        response.redirect("/gesoc/cargar_ingreso");
        return response;

    }

    public ModelAndView mostrarListadoIngresos(Request request, Response response){

        int idUsuario= request.session().attribute("usuario_id");
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario= usuario.getOrganizacion();

        List<Ingreso> ingresos = FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class).buscarTodos();
        List<Ingreso> ingresosOrganizacion = ingresos.stream().filter(ingreso -> ingreso.getOrganizacion().equals(entidadUsuario)).collect(Collectors.toList());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("ingresosOrganizacion", ingresosOrganizacion);

        return new ModelAndView(parametros, "operaciones_de_ingreso.hbs");
    }

    public ModelAndView mostrarAsociacionIngresoEgreso(Request request, Response response) {
        int idUsuario= request.session().attribute("usuario_id");

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();
        List<Ingreso> ingresos= FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class).buscarTodos().stream().filter(i->i.getOrganizacion().getId()==entidadUsuario.getId()).filter(i->i.disponibleParaAsociacion()).collect(Collectors.toList());
        List<OperacionDeEgreso> egresos= FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class).buscarTodos().stream().filter(e->e.getOrganizacion()!=null & e.getOrganizacion().getId()==entidadUsuario.getId()).filter(e->e.noPoseeIngresoAsociado()).collect(Collectors.toList());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizacion", entidadUsuario);
        parametros.put("usuario", usuario);
        parametros.put("egresos", egresos);
        parametros.put("ingresos", ingresos);

        if (this.mostrarModalAlertaVinculacion(request)){
            boolean mostrarModal=true;
            parametros.put("mostrar_modal", mostrarModal);
            parametros.put("aviso_modal", this.obtenerMensajeModalAlertaVinculacion(request));
            parametros.put("modal_titulo", this.obtenerTituloModalAlertaVinculacion(request));
            parametros.put("color_modal", this.obtenerColorModalAlertaVinculacion(request));
            this.cancelarModalAlertaVinculacion(request);
        }

        return new ModelAndView(parametros, "asociacion_ingreso_egreso.hbs");
    }

    public  String obtenerIngreso (Request request, Response response){

        System.out.println("entreeeeeeeeeee");
        int idIngreso = Integer.parseInt(request.queryParams("id_ingreso"));
        System.out.println("id ingreso: " + idIngreso);
        Ingreso ingreso = FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class).buscar(idIngreso);

        IngresoDto ingresoDto= new IngresoDto(ingreso);
        Gson gson = new Gson();
        String jsonIngreso = gson.toJson(ingresoDto, IngresoDto.class);
        response.type("application/json");
        System.out.println("json ingreso" + jsonIngreso);
        return jsonIngreso;

    }

    public Response asociarIngresoEgresos(Request request, Response response){

        int idIngreso;
        Type listType;
        List<IntDeserializable> idsEgresos;

        try{
            listType = new TypeToken<ArrayList<IntDeserializable>>(){}.getType();
            idsEgresos= new Gson().fromJson(request.queryParams("egresos_elegidos"), listType);
            idIngreso=Integer.parseInt(request.queryParams("ingreso_elegido"));

        }catch(Exception e){
            this.setearModalAlertaVinculacion(request, "Error en la vinculación ingreso - egresos.", "Hubo un error inesperado en la vinculación.", AsistenteDeColores.getError());
            response.redirect("/gesoc/asociar_ingreso_egreso");
            return response;
        }
        Repositorio<OperacionDeEgreso> repoEgresos= FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
        List<OperacionDeEgreso> egresos= idsEgresos.stream().map(o->repoEgresos.buscar(o.getValor())).collect(Collectors.toList());
        Repositorio<Ingreso> repoIngresos= FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class);
        Ingreso ingreso= repoIngresos.buscar(idIngreso);
        try{
            ingreso.asociarEgresos(egresos);

        }catch(Exception e){
            this.setearModalAlertaVinculacion(request, "Error en la vinculación ingreso - egresos.", "Hubo un error en la vinculación: el ingreso no puede ser vinculado a los egresos seleccionados.", AsistenteDeColores.getError());
            response.redirect("/gesoc/asociar_ingreso_egreso");
            return response;
        }

        egresos.stream().forEach(egreso->repoEgresos.modificar(egreso));
        repoIngresos.modificar(ingreso);
        this.setearModalAlertaVinculacion(request, "Éxito en la vinculación ingreso - egresos.", "La vinculación fue exitosa", AsistenteDeColores.getExito());
        response.redirect("/gesoc/asociar_ingreso_egreso");



        return response;

    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_carga_ingreso",true);
        request.session().attribute("aviso_modal_carga_ingreso", mensaje);
        request.session().attribute("modal_titulo_carga_ingreso", titulo);
        request.session().attribute("modal_color_carga_ingreso", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_carga_ingreso")==null)
            return false;
        return request.session().attribute("alerta_carga_ingreso");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_carga_ingreso");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_carga_ingreso");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_carga_ingreso");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_carga_ingreso",false);
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


    public void setearModalAlertaVinculacion(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_vinculacion",true);
        request.session().attribute("aviso_modal_vinculacion", mensaje);
        request.session().attribute("modal_titulo_vinculacion", titulo);
        request.session().attribute("modal_color_vinculacion", color);
    }

    public boolean mostrarModalAlertaVinculacion(Request request){
        if(request.session().attribute("alerta_vinculacion")==null)
            return false;
        return request.session().attribute("alerta_vinculacion");
    }

    public String obtenerTituloModalAlertaVinculacion(Request request){
        return request.session().attribute("modal_titulo_vinculacion");
    }

    public String obtenerMensajeModalAlertaVinculacion(Request request){
        return request.session().attribute("aviso_modal_vinculacion");
    }

    public String obtenerColorModalAlertaVinculacion(Request request){
        return request.session().attribute("modal_color_vinculacion");
    }

    public void cancelarModalAlertaVinculacion(Request request){
        request.session().attribute("alerta_vinculacion",false);
    }

    public String mostrarIngresoEgresos(Request request, Response response){
        int idIngreso = Integer.parseInt(request.queryParams("id_ingreso"));
        Ingreso ingreso = FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class).buscar(idIngreso);

        List<OperacionDeEgreso> egresos = ingreso.getEgresos();

        List<EgresoDtoFechaString> egresosDto = egresos.stream().map(e -> EgresoDtoFechaString.toEgresoDtoFechaString(e)).collect(Collectors.toList());;
        Gson gson = new Gson();
        String jsonEgresos = gson.toJson(egresosDto);
        response.type("application/json");
        return jsonEgresos;
    }
}
