package controllers;

import DTOs.PresupuestoCategoriasDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Categorizacion.Categoria;
import domain.Egresos.DocumentoComercial;
import domain.Egresos.OperacionDeEgreso;
import domain.Egresos.Presupuesto;
import domain.Egresos.TipoDocumentoComercial;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Usuario;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EdicionPresupuestoController implements Controlador{
     public ModelAndView mostrarEdicionPresupuesto(Request request, Response response){
         try{
        int idPresupuesto= Integer.parseInt(request.queryParams("id_presupuesto"));
        Presupuesto presupuesto= FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscar(idPresupuesto);

        HashMap<String, Object> parametros= new HashMap();
        parametros.put("presupuesto", presupuesto);
        parametros.put("tipos_documentos", TipoDocumentoComercial.obtenerTodos());
         if(presupuesto.getDocumento()!=null)
             parametros.put("tipo_documento", presupuesto.getDocumento().getTipo().toString());
        AsistenteDeModales.configurarParametros(this, request,parametros);
        return new ModelAndView(parametros, "editar_presupuesto.hbs");}catch(Exception e){
             return ControllerError.mostrarErrorInesperado(request, response);
         }
    }

    public Response editarPresupuesto(Request request, Response response) throws IOException {
        int idUsuario= request.session().attribute("usuario_id");
         int idPresupuesto=Integer.parseInt(request.queryParams("id_presupuesto"));
         String numDoc;
         TipoDocumentoComercial tipoDoc;
        LocalDate fechaDoc;
        String pathDoc;
        LocalDate fechaP;
         try{
             fechaP= LocalDate.parse(request.queryParams("fecha"));
             pathDoc= ArchivoController.obtenerNombreArchivo(request.queryParams("archivo_doc_comercial"));
             numDoc= request.queryParams("numero_documento");
             tipoDoc= TipoDocumentoComercial.toTipoDocumentoComercial(request.queryParams("tipo_documento"));
             fechaDoc= LocalDate.parse(request.queryParams("fecha_documento"));

         }catch(Exception e){
             this.setearModalAlerta(request, "Error en la edición del presupuesto", "Ha ocurrido un error al editar el presupuesto. Revise haber ingresado los campos del documento comercial.", AsistenteDeColores.getError());
             response.redirect("/gesoc/editar_presupuesto?id_presupuesto=" + idPresupuesto);
             return response;
         }

        Repositorio<Presupuesto> repoPresupuestos= FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class);

         Presupuesto p= repoPresupuestos.buscar(idPresupuesto);
         if(p.getDocumento()==null){
            p.setDocumento(new DocumentoComercial());
         }
         p.setFecha(fechaP);
         p.getDocumento().setPath(pathDoc);
         p.getDocumento().setNumeroDocumento(numDoc);
         p.getDocumento().setTipo(tipoDoc);
         p.getDocumento().setFecha(fechaDoc);

         repoPresupuestos.modificar(p);

        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        if(p.getDocumento().getPath()!=null){
            p.getDocumento().setUbicacion(ArchivoController.asociarArchivoAPresupuesto(p.getId(), usuario.getOrganizacion().getId(), p.getDocumento().getPath()));
            repoPresupuestos.modificar(p);
        }

         this.setearModalAlerta(request, "Éxito en la edición del presupuesto", "La edición del presupuesto se ha realizado exitosamente.", AsistenteDeColores.getExito());

         response.redirect("/gesoc/editar_presupuesto?id_presupuesto=" + idPresupuesto);
         return response;

    }


    public ModelAndView mostrarCategorizacionPrespuesto (Request request, Response response){
         System.out.println("ENTREEE");
        int idUsuario= request.session().attribute("usuario_id");

        Usuario usuario = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica entidadUsuario = usuario.getOrganizacion();

        List<Presupuesto> presupuestos = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscarTodos();
        List<Presupuesto> presupuestosOrganizacion = presupuestos.stream().filter(presupuesto -> presupuesto.getOrganizacion().equals(entidadUsuario)).collect(Collectors.toList());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("entidadUsuario", entidadUsuario);
        parametros.put("presupuestos",presupuestosOrganizacion);

        AsistenteDeModales.configurarParametros(this, request, parametros);

        return new ModelAndView(parametros, "asociacion_presupuesto_categoria.hbs");
    }

    public String obtenerPresupuestoCategorias (Request request, Response response){

        int idPresupuesto = Integer.parseInt(request.queryParams("id_presupuesto"));
        Presupuesto presupuesto = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscar(idPresupuesto);

        PresupuestoCategoriasDTO presupuestoCategoriasDTO = new PresupuestoCategoriasDTO(presupuesto);
        Gson gson = new Gson();
        String jsonPresupuesto = gson.toJson(presupuestoCategoriasDTO, PresupuestoCategoriasDTO.class);
        response.type("application/json");
        System.out.println("json" + jsonPresupuesto);
        return jsonPresupuesto;

    }

    public Response editarCategoriasPresupuesto(Request request, Response response){

        int idPresupuesto;
        List<IntDeserializable> idsCategorias;

        try{
            idPresupuesto = Integer.parseInt(request.queryParams("idPresupuesto"));
            Type listType = new TypeToken<ArrayList<IntDeserializable>>(){}.getType();
            idsCategorias = new Gson().fromJson(request.queryParams("lectura_categorias"), listType);

        }catch(Exception e){

            this.setearModalAlerta(request, "Error en la modificación de categorías","Ha ocurrido un problema con la actualización de las categorías de su presupuesto. Verifique los datos ingresados.", AsistenteDeColores.getError()  );
            response.redirect("/gesoc/categorizar_presupuesto");
            return response;
        }
        List<Categoria> categorias = new ArrayList<>();
        for(int i = 0; i<idsCategorias.size(); i++){
            Categoria categoria = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscar(idsCategorias.get(i).getValor());
            categorias.add(categoria);
        }

        Presupuesto presupuesto = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).buscar(idPresupuesto);
        presupuesto.setCategorias(categorias);
        Repositorio<Presupuesto> repopresupuestos = FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class);
        FactoryRepositorio.instancia().obtenerRepositorio(Presupuesto.class).modificar(presupuesto);

        this.setearModalAlerta(request,"Carga exitosa","La modificación del presupuesto ha sido exitosa.", AsistenteDeColores.getExito());
        response.redirect("/gesoc/categorizar_presupuesto");
        return response;
    }

    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_edicion_presupuesto",true);
        request.session().attribute("aviso_modal_edicion_presupuesto", mensaje);
        request.session().attribute("modal_titulo_edicion_presupuesto", titulo);
        request.session().attribute("modal_color_edicion_presupuesto", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_edicion_presupuesto")==null)
            return false;
        return request.session().attribute("alerta_edicion_presupuesto");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_edicion_presupuesto");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_edicion_presupuesto");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_edicion_presupuesto");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_edicion_presupuesto",false);
    }
}

