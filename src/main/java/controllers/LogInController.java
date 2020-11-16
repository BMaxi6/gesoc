package controllers;

import domain.Personas.Administrador;
import domain.Personas.Usuario;
import repositorios.CriteriaQueryFactory;
import repositorios.DaoHibernate;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import validadorContrasenias.EncriptadorDeContrasenias;

import java.util.Formatter;
import java.util.HashMap;

public class LogInController implements Controlador{
    public ModelAndView inicio(Request request, Response response){
        HashMap<String, Object> parametros= new HashMap<>();
        AsistenteDeModales.configurarParametros(this,request,parametros);
        return new ModelAndView(parametros, "log_in.hbs");
    }

    public Response login(Request request, Response response) {
        //DaoHibernate<Usuario> daoUsuario= new DaoHibernate<Usuario>(Usuario.class);
         //Repositorio<Usuario> repoUsuarios= new Repositorio<Usuario>(daoUsuario);
        boolean inicioCorrecto=false;
        Repositorio<Usuario> repoUsuarios = FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class);
        Repositorio<Administrador> repoAdmins = FactoryRepositorio.instancia().obtenerRepositorio(Administrador.class);


        String nombreDeUsuario = request.queryParams("nombre_usuario");
        String contrasenia = request.queryParams("contrasenia_usuario");


        Usuario usuarioEncontrado = repoUsuarios.buscar(new CriteriaQueryFactory().busquedaPorNombre(Usuario.class, nombreDeUsuario));
        //System.out.println(usuarioEncontrado.getNombre());
        if (usuarioEncontrado != null && usuarioEncontrado.isActivo()) {

            if (EncriptadorDeContrasenias.instancia().contraseniaCoincide(contrasenia, usuarioEncontrado.getContrasenia())) {
                request.session(true);
                request.session().attribute("usuario_id", usuarioEncontrado.getId());
                response.redirect(new Formatter().format("gesoc/home", usuarioEncontrado.getId()).toString());
                inicioCorrecto=true;
            } else {
                response.redirect("/");
            }


        } else {
            Administrador administradorEncontrado = repoAdmins.buscar(new CriteriaQueryFactory().busquedaPorNombre(Administrador.class, nombreDeUsuario));
            if (administradorEncontrado != null && administradorEncontrado.isActivo()) {
                if (EncriptadorDeContrasenias.instancia().contraseniaCoincide(contrasenia, administradorEncontrado.getContrasenia())) {
                    request.session(true);
                    request.session().attribute("usuario_id", administradorEncontrado.getId());
                    response.redirect("/gesoc/administracion");
                    inicioCorrecto=true;
                } else {
                    response.redirect("/");
                }
            } else {
                response.redirect("/");
            }
        }

        if(!inicioCorrecto)
            this.setearModalAlerta(request, "Error en el inicio de sesión", "Nombre de usuario y/o contraseña incorrectos.", AsistenteDeColores.getError());

        return response;
    }

    public Response cerrarSesion(Request request, Response response){
        System.out.println("---------------------------------");
        request.session().invalidate();
        response.redirect("/");
        return response;
    }


    public void setearModalAlerta(Request request, String titulo, String mensaje, String color){
        request.session().attribute("alerta_login",true);
        request.session().attribute("aviso_modal_login", mensaje);
        request.session().attribute("modal_titulo_login", titulo);
        request.session().attribute("modal_color_login", color);
    }

    public boolean mostrarModalAlerta(Request request){
        if(request.session().attribute("alerta_login")==null)
            return false;
        return request.session().attribute("alerta_login");
    }

    public String obtenerTituloModalAlerta(Request request){
        return request.session().attribute("modal_titulo_login");
    }

    public String obtenerMensajeModalAlerta(Request request){
        return request.session().attribute("aviso_modal_login");
    }

    public String obtenerColorModalAlerta(Request request){
        return request.session().attribute("modal_color_login");
    }

    public void cancelarModalAlerta(Request request){
        request.session().attribute("alerta_login",false);
    }
}
