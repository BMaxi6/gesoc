package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.DTO.DiasJSON;
import controllers.DTO.PersonasJson;
import domain.Categorizacion.Categoria;
import domain.Categorizacion.Criterio;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.CategorizadorDeEmpresas;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.Rubro;
import domain.EntidadesOrganizacionales.Empresa;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.EntidadesOrganizacionales.Osc;
import domain.Personas.Administrador;
import domain.Personas.Usuario;
import domain.ubicaciones.Ciudad;
import domain.ubicaciones.Direccion;
import domain.ubicaciones.Pais;
import domain.ubicaciones.Provincia;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadorContrasenias.ValidadorDeContrasenias;


import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class AdministradorController implements Controlador{

    public ModelAndView mostrarHome(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");


        Administrador administrador= FactoryRepositorio.instancia().obtenerRepositorio(Administrador.class).buscar(idUsuario);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuario", administrador);

        return new ModelAndView(parametros, "menu_administrador.hbs");
    }

    public ModelAndView mostrarPerfil(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        Administrador admin = FactoryRepositorio.instancia().obtenerRepositorio(Administrador.class).buscar(idUsuario);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuario", admin);
        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "mi_perfil_admin.hbs");
    }

    public Response cambiarContrasenia(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        int idUsuario= request.session().attribute("usuario_id");
        Repositorio<Administrador> repoAdmin= FactoryRepositorio.instancia().obtenerRepositorio(Administrador.class);
        Administrador admin = repoAdmin.buscar(idUsuario);
        String contraseniaNueva=request.queryParams("contrasenia_nueva");
        String contraseniaNuevaConfirmacion=request.queryParams("contrasenia_nueva_confirmacion");
        String contraseniaAnterior=request.queryParams("contrasenia_anterior");
        if(Objects.equals(contraseniaNueva, contraseniaNuevaConfirmacion)){
            if(admin.contraseniaCorrecta(contraseniaAnterior)){
                admin.setContrasenia(contraseniaNueva);
                repoAdmin.modificar(admin);
                this.setearModalAlerta(request, "contraseña modificada exitosamente", "Su contraseña ha sido modificada con exito", AsistenteDeColores.getExito());
                response.redirect("/gesoc/administracion/admin");
                //response.redirect(new Formatter().format("/gesoc/administracion/admin/%d", admin.getId()).toString());
            }else{
                this.setearModalAlerta(request, "Error al modificar contraseña", "La contraseña ingresada es incorrecta", AsistenteDeColores.getError());
                response.redirect("/gesoc/administracion/admin");
            }
        }else {
            this.setearModalAlerta(request, "Error al modificar contraseña", "Las contraseñas no coinciden", AsistenteDeColores.getError());
            response.redirect("/gesoc/administracion/admin");
        }
        return response;
    }

    public ModelAndView mostrarAltaUsuario(Request request, Response response){
        Repositorio<EntidadJuridica> repoOrgs=FactoryRepositorio.instancia().obtenerRepositorio(EntidadJuridica.class);
        List<EntidadJuridica> organizaciones= repoOrgs.buscarTodos();
        Map<String,Object> parametros= new HashMap<>();
        parametros.put("organizaciones", organizaciones);
        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "alta_usuario.hbs");
    }
    public ModelAndView mostrarBajaUsuario(Request request, Response response){
        List<Usuario> usuarios=FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscarTodos().stream().filter(usuario->usuario.isActivo()).collect(Collectors.toList());
        Map<String,Object> parametros=new HashMap<>();
        parametros.put("usuarios", usuarios);
        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "bajaUsuario.hbs");
    }
    public ModelAndView mostrarAltaOrganizacion(Request request, Response response){
        return new ModelAndView(null, "alta_organizacion.hbs");
    }
    public ModelAndView mostrarAltaEmpresa(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<Pais> paises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class).buscarTodos();
        List<Provincia> provincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class).buscarTodos();
        List<Ciudad> ciudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class).buscarTodos();
        List<Categoria> categoriasEntidad = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscarTodos();
        List<Rubro> rubros= Arrays.asList(Rubro.values());
        List<String> rubrosString=rubros.stream().map(e->e.name()).collect(Collectors.toList());
        parametros.put("rubros", rubrosString);
        parametros.put("paises", paises);
        parametros.put("provincias", provincias);
        parametros.put("ciudades", ciudades);
        parametros.put("categoriasEntidad", categoriasEntidad);

        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "alta_empresa.hbs");
    }
    public ModelAndView mostrarAltaOsc(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        List<Pais> paises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class).buscarTodos();
        List<Provincia> provincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class).buscarTodos();
        List<Ciudad> ciudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class).buscarTodos();
        List<Categoria> categoriasEntidad = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class).buscarTodos();
        List<Rubro> rubros= Arrays.asList(Rubro.values());
        List<String> rubrosString=rubros.stream().map(e->e.name()).collect(Collectors.toList());
        parametros.put("rubros", rubrosString);
        parametros.put("paises", paises);
        parametros.put("provincias", provincias);
        parametros.put("ciudades", ciudades);
        parametros.put("categoriasEntidad", categoriasEntidad);

        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "alta_osc.hbs");
    }
    public ModelAndView mostrarBajaOrganizacion(Request request, Response response){
        Repositorio<EntidadJuridica> repoOrganizaciones= FactoryRepositorio.instancia().obtenerRepositorio(EntidadJuridica.class);
        List<EntidadJuridica> organizaciones= repoOrganizaciones.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones", organizaciones);
        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "baja_organizacion.hbs");
    }

    public ModelAndView mostrarAdministracionCriteriosYCategorias(Request request, Response response){
        Map<String,Object> parametros=new HashMap<>();
        AsistenteDeModales.configurarParametros(this,request,parametros);
        Repositorio<Criterio> repoCriterios = FactoryRepositorio.instancia().obtenerRepositorio(Criterio.class);
        List<Criterio> criterios = repoCriterios.buscarTodos();
        Repositorio<Categoria> repoCategorias = FactoryRepositorio.instancia().obtenerRepositorio(Categoria.class);
        List<Categoria> categorias = repoCategorias.buscarTodos();
        parametros.put("criterios", criterios);
        parametros.put("categorias", categorias);
        return new ModelAndView(parametros, "AdminCritYCat.hbs");
    }

    public Response modificarCriterios(Request request, Response response) throws InterruptedException {
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        int idCrit = Integer.parseInt(request.queryParams("critName"));
        int tipo = Integer.parseInt(request.queryParams("critPos"));
        int idCritSec = Integer.parseInt(request.queryParams("critSecName"));
        Repositorio<Criterio> repoCriterio = FactoryRepositorio.obtenerRepositorio(Criterio.class);
        Criterio criterio = repoCriterio.buscar(idCrit);
        Criterio criterioSec = repoCriterio.buscar(idCritSec);
        List<Criterio> criterios = repoCriterio.buscarTodos();

        if(tipo == 3){
            for(int i=0; i<criterios.size(); i++){
                if(criterios.get(i).getCriteriosHijos().contains(criterio)){
                    criterios.get(i).getCriteriosHijos().remove(criterio);
                    repoCriterio.modificar(criterios.get(i));
                }
            }
            this.setearModalAlerta(request, "Criterio modificado exitosamente", "Se ha modificado el criterio " + criterio.getNombre() + " exitosamente", AsistenteDeColores.getExito());
            response.redirect("/gesoc/admin_crit_cat");
            return response;
        }

        if(idCrit == idCritSec){
            this.setearModalAlerta(request, "No se pudo modificar el crierio", "Debes seleccionar criterios diferentes.", AsistenteDeColores.getError());
            response.redirect("/gesoc/admin_crit_cat");
            return response;
        }

        if(tipo == 2){
            for(int i=0; i<criterios.size(); i++){
                if(criterios.get(i).getCriteriosHijos().contains(criterio)) {
                    criterios.get(i).getCriteriosHijos().remove(criterio);
                    repoCriterio.modificar(criterios.get(i));
                }
            }
            criterioSec.agregarSubCriterio(criterio);
            repoCriterio.modificar(criterioSec);
            this.setearModalAlerta(request, "Criterio modificado exitosamente", "Se ha agregado el subcriterio " + criterio.getNombre() + " al criterio " + criterioSec.getNombre() + " exitosamente", AsistenteDeColores.getExito());
            response.redirect("/gesoc/admin_crit_cat");
        }

        if(tipo == 1){
            for(int i=0; i<criterios.size(); i++){
                if(criterios.get(i).getCriteriosHijos().contains(criterioSec)) {
                    criterios.get(i).getCriteriosHijos().remove(criterioSec);
                    repoCriterio.modificar(criterios.get(i));
                }
            }
            criterio.agregarSubCriterio(criterioSec);
            repoCriterio.modificar(criterio);
            this.setearModalAlerta(request, "Criterio modificado exitosamente", "Se ha agregado el subcriterio " + criterioSec.getNombre() + " al criterio " + criterio.getNombre() + " exitosamente", AsistenteDeColores.getExito());
            response.redirect("/gesoc/admin_crit_cat");
        }


        return response;
    }

    public Response modificarCategorias(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this, request, parametros);
        int idCat = Integer.parseInt(request.queryParams("catName"));
        int idCritSec = Integer.parseInt(request.queryParams("name"));
        Repositorio<Criterio> repoCriterio = FactoryRepositorio.obtenerRepositorio(Criterio.class);
        Criterio criterio = repoCriterio.buscar(idCritSec);
        Repositorio<Categoria> repoCategoria = FactoryRepositorio.obtenerRepositorio(Categoria.class);
        Categoria categoria = repoCategoria.buscar(idCat);
        if(criterio.getCategorias().contains(categoria)){
            this.setearModalAlerta(request, "No se pudo realizar el cambio", "El crierio seleccioando ya contiene a la categoría.", AsistenteDeColores.getError());
            response.redirect("/gesoc/admin_crit_cat");
            return response;
        }
        criterio.agregarCategoria(categoria);
        repoCriterio.modificar(criterio);
        this.setearModalAlerta(request, "Categoría modificado exitosamente", "Se ha agregado la categoria " + categoria.getNombre() + " al criterio " + criterio.getNombre(), AsistenteDeColores.getExito());
        response.redirect("/gesoc/admin_crit_cat");
        return response;
    }

    public Response darAltaOsc(Request request, Response response){
        String razonSocial, nombreFicticio, codigoInscripcionIGJ;
        Rubro rubro;
        Direccion direccion;
        int idRubro;
        List<PersonasJson> empAux;
        List<PersonasJson> finAux;
        List<String> empleados = new ArrayList<String>();
        List<String> financieros = new ArrayList<String>();;
        long cuit;
        int cantidadPersonal;
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this,request,parametros);
        try {
            razonSocial = request.queryParams("razon_social");
            nombreFicticio = request.queryParams("nombre_ficticio");
            codigoInscripcionIGJ = request.queryParams("codigo_inscripcion_igj");
            cantidadPersonal = Integer.parseInt(request.queryParams("cantidad_personal"));
            idRubro = Integer.parseInt(request.queryParams("actividad"));
            rubro = Rubro.values()[idRubro];
            direccion = new Gson().fromJson(request.queryParams("direccion"), Direccion.class);
            Type listType = new TypeToken<ArrayList<PersonasJson>>(){}.getType();
            empAux = new Gson().fromJson(request.queryParams("empleadosRet"), listType);
            finAux = new Gson().fromJson(request.queryParams("financiadoresRet"), listType);
        for (PersonasJson pers: empAux) {
            empleados.add(pers.getName());
        }
        for (PersonasJson pers: finAux) {
            financieros.add(pers.getName());
        }
            this.setearModalAlerta(request, "OSC agregada exitosamente", "Se ha agregado la osc " + razonSocial, AsistenteDeColores.getExito());
        }catch(Exception e) {
            this.setearModalAlerta(request, "Error al agregar OSC", "No se ha podido generar una nueva OSC. Intentelo de nuevo", AsistenteDeColores.getError());
            response.redirect("/alta_osc");
            return response;
        }

        Osc osc = new Osc(razonSocial, nombreFicticio, direccion, codigoInscripcionIGJ, null, cantidadPersonal, rubro, empleados, financieros);
        // ------------------------------------------------------------------------- >>>>> ---------------------------- | Arreglar este Rubro //
        Repositorio<Osc> repo = FactoryRepositorio.instancia().obtenerRepositorio(Osc.class);
        repo.agregar(osc);

        response.redirect("/gesoc/alta_osc");
        return response;
    }

    public Response darAltaEmpresa(Request request, Response response){
        String razonSocial, nombreFicticio, codigoInscripcionIGJ;
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this,request,parametros);
        Direccion direccion;
        Rubro rubro;
        long cuit;
        int cantidadPersonal, idRubro;
        Double ingresosAnuales;
        try{
            razonSocial = request.queryParams("razon_social");
            nombreFicticio = request.queryParams("nombre_ficticio");
            codigoInscripcionIGJ = request.queryParams("codigo_inscripcion_igj");
            cantidadPersonal = Integer.parseInt(request.queryParams("cantidad_personal"));
            idRubro = Integer.parseInt(request.queryParams("actividad"));
            rubro = Rubro.values()[idRubro];
            ingresosAnuales=Double.parseDouble(request.queryParams("ingresos_anuales"));
            direccion = new Gson().fromJson(request.queryParams("direccion"), Direccion.class);
            this.setearModalAlerta(request, "Empresa agregada exitosamente", "Se ha agregado la empresa " + razonSocial, AsistenteDeColores.getExito());
        }catch(Exception e){
            this.setearModalAlerta(request, "Error al generar empresa nueva", "No se ha podido agregar la empresa. Intentelo de nuevo", AsistenteDeColores.getExito());
            response.redirect("/gesoc/alta_empresa");
            return response;
        }
        Repositorio<Empresa> repo=FactoryRepositorio.instancia().obtenerRepositorio(Empresa.class);
        Empresa empresa= new Empresa();
        empresa.setRazonSocial(razonSocial);
        empresa.setNombreFicticio(nombreFicticio);
        empresa.setCodigoInscripcionIGJ(codigoInscripcionIGJ);
        empresa.setCantidadPersonal(cantidadPersonal);
        empresa.setPromedioVentas(ingresosAnuales);
        empresa.setActividad(rubro); // CAMBIAR ESTO POR EL RUBRO ENUM
        repo.agregar(empresa);

        response.redirect("/alta_empresa");
        return response;
    }

    public Response eliminarUsuario(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this,request,parametros);
        int idUsuario=Integer.parseInt(request.params("usuario_id"));
        Repositorio<Usuario> repoUsuarios=FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class);
        Usuario usuario=FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        usuario.darBajaLogica();
        repoUsuarios.modificar(usuario);
        this.setearModalAlerta(request, "Usuario eliminado exitosamente", "Se ha eliminado el usuario " + usuario.getNombre(), AsistenteDeColores.getExito());
        response.redirect("/gesoc/baja_usuario");
        return response;
    }

    public Response crearUsuario(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        AsistenteDeModales.configurarParametros(this,request,parametros);
        String nombreUsuario, contrasenia, contraseniaRepeticion, tipoUsuario, organizacion;
        int idOrganizacion;
        try{
            organizacion=request.queryParams("organizacion");
            nombreUsuario= request.queryParams("nombre_usuario");
            contrasenia= request.queryParams("contrasenia");
            contraseniaRepeticion=request.queryParams("repeticion_contrasenia");
            tipoUsuario=request.queryParams("tipo_usuario");

            if(contrasenia.equals(contraseniaRepeticion)){
                if(ValidadorDeContrasenias.instancia().esContraseniaSegura(contrasenia)){
                    if(tipoUsuario.equals("ADMINISTRADOR")){
                        Administrador admin= new Administrador();
                        admin.setNombre(nombreUsuario);
                        admin.setContrasenia(contrasenia);
                        Repositorio<Administrador> repoAdmin= FactoryRepositorio.instancia().obtenerRepositorio(Administrador.class);
                        repoAdmin.agregar(admin);
                        this.setearModalAlerta(request, "Nuevo Admininstrador Generado", "Se ha generado el administrador " + nombreUsuario + " exitosamente", AsistenteDeColores.getExito());
                    }else{
                        int iend = organizacion.indexOf(" ");
                        organizacion=organizacion.substring(0, iend);
                        idOrganizacion= Integer.parseInt(organizacion);
                        Usuario usuario= new Usuario();
                        usuario.setNombre(nombreUsuario);
                        usuario.setContrasenia(contrasenia);
                        EntidadJuridica org= FactoryRepositorio.instancia().obtenerRepositorio(EntidadJuridica.class).buscar(idOrganizacion);
                        usuario.setOrganizacion(org);
                        Repositorio<Usuario> repoAdmin= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class);
                        repoAdmin.agregar(usuario);
                        this.setearModalAlerta(request, "Nuevo Admininstrador Generado", "Se ha generado el administrador " + nombreUsuario + " exitosamente.", AsistenteDeColores.getExito());
                    }
                }else{
                    this.setearModalAlerta(request, "Error al generar nuevo usuario", "La contraseña ingresada es insegura. " + ValidadorDeContrasenias.instancia().validacionesContrasenia(), AsistenteDeColores.getError());
                }
            }else{
                this.setearModalAlerta(request, "Error al generar nuevo usuario", "Las contraseñas deben coincidir.", AsistenteDeColores.getError());
            }
        }catch(Exception e){
            this.setearModalAlerta(request, "Error al generar nuevo usuario", "No se ha podido generar el usuario. Intentelo nuevamente.", AsistenteDeColores.getError());
            response.redirect("/gesoc/alta_usuario");
            return response;
        }
        response.redirect("/gesoc/alta_usuario");
        return response;
    }

    @Override
    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_vinculo",true);
        request.session().attribute("aviso_modal_vinculo", mensaje);
        request.session().attribute("modal_titulo_vinculo", titulo);
        request.session().attribute("modal_color_vinculo", color);
    }

    @Override
    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_vinculo")==null)
            return false;
        return request.session().attribute("alerta_vinculo");
    }

    @Override
    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_vinculo");
    }

    @Override
    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_vinculo");
    }

    @Override
    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_vinculo");
    }

    @Override
    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_vinculo",false);
    }
}
