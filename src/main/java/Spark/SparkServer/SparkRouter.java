package Spark.SparkServer;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.*;
import middlewares.MiddleWare;

import org.apache.commons.io.IOUtils;
import repositorios.FactoryRepositorio;
import spark.QueryParamsMap;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class SparkRouter {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        SparkRouter.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .withHelper("json",new GsonHelper())
                .withHelper("equals", new EqualsHelper())
                .withHelper("notEquals", new NotEqualsHelper())
                .build();
    }

    public static void init() {
        SparkRouter.initEngine();
        Spark.staticFileLocation("/public");

        SparkRouter.configure();
    }

    public static void main(String[] args) throws Exception {
        Spark.port(getHerokuAssignedPort());
        SparkRouter.init();
        DebugScreen.enableDebugScreen();

        LogInController logInController= new LogInController();
        HomeController homeController= new HomeController();
        UsuarioController usuarioController=new UsuarioController();
        EgresoController egresoController = new EgresoController();
        EntidadJuridicaController entidadJuridicaController= new EntidadJuridicaController();
        AdministradorController administradorController = new AdministradorController();
        IngresoController ingresoController = new IngresoController();
        PresupuestoController presupuestoController = new PresupuestoController();
        CategoriasController categoriasController = new CategoriasController();
        BandejaDeMensajesController bandejaDeMensajesController = new BandejaDeMensajesController();
        DireccionController direccionController= new DireccionController();
        EdicionEgresoController edicionEgresoController = new EdicionEgresoController();
        ValidadorTransparenciaController validadorTransparenciaController= new ValidadorTransparenciaController();
        CriteriosController criteriosController = new CriteriosController();
        EdicionPresupuestoController edicionPresupuestoController= new EdicionPresupuestoController();
        EdicionCriteriosCategoriasController edicionCriteriosCategoriasController = new EdicionCriteriosCategoriasController();
        GeSocController geSocController=new GeSocController();
        ArchivoController archivoController=new ArchivoController();


        //---------------------------------------------LOGIN - HOME - PERFIL
        Spark.get("/", logInController::inicio, SparkRouter.engine);
        Spark.post("/login", logInController::login);
        Spark.before("/gesoc/*", MiddleWare.instancia()::verificarSesion);
        Spark.get("/gesoc/home", homeController::mostrarHome,SparkRouter.engine);

        Spark.get("/gesoc/home/perfil",usuarioController::mostrarPerfil,SparkRouter.engine );
        Spark.post("/gesoc/cambio_contrasenia/", usuarioController::cambiarContrasenia);

        Spark.post("/gesoc/cerrar_sesion", logInController::cerrarSesion);

        Spark.get("/obtener_paises", direccionController:: obtenerPaises);

        //----------------------------------------------------BANDEJA DE MENSAJES
        Spark.get("/gesoc/bandeja_de_mensajes", bandejaDeMensajesController::mostrarBandeja, SparkRouter.engine);
        Spark.get("/gesoc/eliminar_mensaje", bandejaDeMensajesController::eliminarMensaje, SparkRouter.engine);
        Spark.get("/gesoc/marcar_leido", bandejaDeMensajesController::marcarLeido, SparkRouter.engine);
        Spark.get("/gesoc/bandeja_de_mensajes/filtrado", bandejaDeMensajesController::filtrarMensajes, SparkRouter.engine);

        //--------------------------------------------------------------VALIDADOR TRANSPARENCIA
        Spark.get("/gesoc/validacion_transparencia", validadorTransparenciaController::mostrarPaginaValidador, SparkRouter.engine);
        Spark.get("/gesoc/planificacion_validador", validadorTransparenciaController::mostrarPaginaPlanificacionValidador, SparkRouter.engine);
        Spark.post("/gesoc/modificar_planificacion_validador", validadorTransparenciaController::modificarPlanificacionValidador);
        Spark.post("/gesoc/ejecutar_validacion_transparencia", validadorTransparenciaController::ejecucionManualValidador);

        //-----------------------------------------------PROVEEDOR
        Spark.post("/gesoc/nuevo_proveedor",entidadJuridicaController::nuevoProveedor);
        Spark.post("/gesoc/nuevo_proveedor_presupuesto/",presupuestoController::nuevoProveedor);

        //---------------------------------------------CARGA EGRESO---------------------------------------------
        Spark.get("/gesoc/cargar_egreso", egresoController::mostrarCargaEgreso, SparkRouter.engine);
        Spark.post("/gesoc/nuevo_egreso", egresoController::nuevoEgreso);
        Spark.get("/obtener_item", egresoController::obtenerItem);

        //--------------------------------------------CARGA PRESUPUESTO
        Spark.get("/gesoc/cargar_presupuesto", presupuestoController::mostrarCargaPresupuesto, SparkRouter.engine);
        Spark.post("/gesoc/nuevo_presupuesto", presupuestoController::nuevoPresupuesto);
        Spark.get("/gesoc/cargar_presupuesto/:id_egreso", presupuestoController::mostrarCargaPresupuestoConEgreso, SparkRouter.engine);

        //----------------------------------------------CARGA INGRESO
        Spark.get("/gesoc/cargar_ingreso", ingresoController::mostrarCargaIngreso, SparkRouter.engine);
        Spark.post("/gesoc/nuevo_ingreso", ingresoController::nuevoIngreso);

        //-----------------------------------------------VISTA: OPERACIONES DE EGRESO--------------------------------
        Spark.get("/gesoc/listado_egresos", egresoController::mostrarListadoEgresos, SparkRouter.engine);
        Spark.get("/egreso_presupuestos", egresoController::obtenerPresupuestos);
        Spark.get("/egreso_items", egresoController::obtenerItems);
        Spark.get("/egreso_categorias", egresoController::obtenerCategorias);
        Spark.get("/egreso_revisores", egresoController::obtenerRevisores);

        //-----------------------------------------------VISTA: LISTADO CRITERIOS Y CATEGORIAS--------------------------
        Spark.get("/criterio_subcriterios", criteriosController::mostrarSubCriterios);
        Spark.get("/criterio_categorias", categoriasController::mostrarCategorias);

        //-----------------------------------------------VISTA: OPERACIONES DE INGRESO--------------------------------
        Spark.get("/gesoc/listado_ingresos", ingresoController::mostrarListadoIngresos, SparkRouter.engine);
        Spark.get("/ingreso_egresos", ingresoController::mostrarIngresoEgresos);
        Spark.get("/presupuesto_categorias", presupuestoController::obtenerCategorias);

        //-----------------------------VISTA: ASOCIACION PRESUPUESTOS CATEGORIAS Y EGRESOS CATEGORIAS---------------------
        Spark.get("/gesoc/categorizar_presupuesto", edicionPresupuestoController::mostrarCategorizacionPrespuesto, SparkRouter.engine);
        Spark.get("/gesoc/categorizar_egreso", egresoController::mostrarCategorizacionEgreso, SparkRouter.engine);
        Spark.get("/informacion_presupuesto", edicionPresupuestoController::obtenerPresupuestoCategorias);
        Spark.get("/informacion_egreso", egresoController::obtenerEgresoCategorias);
        Spark.get("/obtener_criterios", criteriosController::mostrarCriterios);
        Spark.post("/gesoc/egreso_categorias_nuevas",egresoController::editarCategoriasEgreso);
        Spark.post("/gesoc/presupuesto_categorias_nuevas",edicionPresupuestoController::editarCategoriasPresupuesto);

        //-----------------------------VISTA: ASOCIACION INGRESOS Y EGRESOS---------------------------------
        Spark.post("/gesoc/vinculacion_ingreso_egreso", ingresoController::asociarIngresoEgresos);
        Spark.get("/obtener_egreso", egresoController:: obtenerEgreso);
        Spark.get("/obtener_ingreso", ingresoController:: obtenerIngreso);
        Spark.get("/gesoc/vinculo_operaciones", usuarioController::vincularOperaciones, SparkRouter.engine);
        Spark.post("/gesoc/intentar_vinculo", usuarioController::vinculoRealizado);
        Spark.get("/gesoc/hora_vinculo", usuarioController::modificarHoraVinculo, SparkRouter.engine);
        Spark.post("/gesoc/modificar_criterio", usuarioController::modificarCriterioPermanente);
        Spark.post("/gesoc/fecha_vinculo", usuarioController::modificarFechaVinculo);
        Spark.get("/gesoc/asociar_ingreso_egreso", ingresoController::mostrarAsociacionIngresoEgreso, SparkRouter.engine);

        //---------------------------------------------PRESUPUESTOS
        Spark.get("/gesoc/listado_presupuestos", presupuestoController::mostrarListadoPresupuestos, SparkRouter.engine);
        Spark.get("/gesoc/editar_presupuesto", edicionPresupuestoController::mostrarEdicionPresupuesto, SparkRouter.engine);
        Spark.post("/gesoc/editar_presupuesto", edicionPresupuestoController::editarPresupuesto);
        Spark.get("/presupuesto_items", presupuestoController::obtenerItems);

        //-------------------------------------------ADMIN
        Spark.get("/gesoc/administracion", administradorController::mostrarHome,SparkRouter.engine);
        Spark.get("/gesoc/administracion/admin",administradorController::mostrarPerfil,SparkRouter.engine );
        Spark.post("/gesoc/cambio_contrasenia", usuarioController::cambiarContrasenia);
        Spark.post("/gesoc/cambio_contrasenia/admin", administradorController::cambiarContrasenia);
        Spark.get("/gesoc/alta_usuario", administradorController::mostrarAltaUsuario, SparkRouter.engine);
        Spark.post("/gesoc/crear_usuario", administradorController::crearUsuario);
        Spark.get("/gesoc/baja_usuario", administradorController::mostrarBajaUsuario, SparkRouter.engine);
        Spark.post("/gesoc/eliminar_usuario/:usuario_id", administradorController::eliminarUsuario);
        Spark.get("/gesoc/alta_empresa", administradorController::mostrarAltaEmpresa, SparkRouter.engine);
        Spark.get("/gesoc/alta_organizacion", administradorController::mostrarAltaOrganizacion, SparkRouter.engine);
        Spark.get("/gesoc/alta_osc", administradorController::mostrarAltaOsc, SparkRouter.engine);
        Spark.get("/gesoc/baja_organizacion", administradorController::mostrarBajaOrganizacion, SparkRouter.engine);
        Spark.get("/gesoc/admin_crit_cat", administradorController::mostrarAdministracionCriteriosYCategorias, SparkRouter.engine);
        Spark.post("/gesoc/confirmar_alta_osc", administradorController::darAltaOsc);
        Spark.post("/gesoc/modificar_criterios", administradorController::modificarCriterios);
        Spark.post("/gesoc/modificar_categorias", administradorController::modificarCategorias);

        //-------------------------------------------------------LISTADO CRITERIOS CATEGORIAS
        Spark.get("/gesoc/listado_criterios_categorias", categoriasController::mostrarListado, SparkRouter.engine);
        Spark.get("/gesoc/obtener_categorias", categoriasController::mostrarListadoConCategorias, SparkRouter.engine);

        //-----------------------------------------------------EDICION CRITERIOS CATEGORIAS
        Spark.get("/gesoc/edicion_criterios_categorias", categoriasController::mostrarEdicionCriteriosCategorias, SparkRouter.engine);
        Spark.get("obtener_criterios_edicion", criteriosController::mostrarCriterios);
        Spark.post("/gesoc/nuevo_criterio", categoriasController::nuevoCriterio);
        Spark.post("/gesoc/nueva_categoria", categoriasController::nuevaCategoria);

        //-----------------------------------------------------EGRESOS POR CATEGORIA
        Spark.get("/gesoc/egresos_por_categoria", categoriasController::mostrarEgresosCategoria, SparkRouter.engine);
        Spark.get("/categoria_egresos", categoriasController::obtenerEgresosCategoria);

        //-------------------------------------------------------PRESUPUESTOS POR CATEGORIA
        Spark.get("/gesoc/presupuestos_por_categoria", categoriasController::mostrarPresupuestosCategoria, SparkRouter.engine);
        Spark.get("/categoria_presupuestos", categoriasController::obtenerPresupuestosCategoria);

        //--------------------------------------------------EDITAR EGRESO
        Spark.get("/gesoc/editar_egreso", edicionEgresoController::mostrarEdicionEgreso, SparkRouter.engine);
        Spark.post("/gesoc/editar_egreso", edicionEgresoController::editarEgreso);


        //-------------------------------------------------PRUEBAS
        ControllerPruebaPhp controllerPruebaPhp=new ControllerPruebaPhp();

        Spark.get("/php", controllerPruebaPhp::mostrarPhp, SparkRouter.engine);

        FileUploadServlet fileUploadServlet=new FileUploadServlet();
        //Spark.post("/upload", fileUploadServlet::doPost);


        //------------------------------------------------CARGA ARCHIVOS

        Spark.post("/gesoc/upload", archivoController::guardarArchivo);
        Spark.get("/gesoc/download",archivoController::descargarArchivo);

        Spark.get("/error_logueo", ControllerError::mostrarBloqueoLogin, SparkRouter.engine);
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }


    private static void configure(){
        LogInController logInController= new LogInController();
        HomeController homeController= new HomeController();
        UsuarioController usuarioController=new UsuarioController();
        EgresoController egresoController = new EgresoController();
        EntidadJuridicaController entidadJuridicaController= new EntidadJuridicaController();
        AdministradorController administradorController = new AdministradorController();
        IngresoController ingresoController = new IngresoController();
        PresupuestoController presupuestoController = new PresupuestoController();
        CategoriasController categoriasController = new CategoriasController();
        BandejaDeMensajesController bandejaDeMensajesController = new BandejaDeMensajesController();
        DireccionController direccionController= new DireccionController();
        EdicionEgresoController edicionEgresoController = new EdicionEgresoController();
        ValidadorTransparenciaController validadorTransparenciaController= new ValidadorTransparenciaController();
        CriteriosController criteriosController = new CriteriosController();
        EdicionPresupuestoController edicionPresupuestoController= new EdicionPresupuestoController();
        EdicionCriteriosCategoriasController edicionCriteriosCategoriasController = new EdicionCriteriosCategoriasController();
        GeSocController geSocController=new GeSocController();
        ArchivoController archivoController=new ArchivoController();


        //---------------------------------------------LOGIN - HOME - PERFIL
        Spark.get("/", logInController::inicio, SparkRouter.engine);
        Spark.post("/login", logInController::login);
        Spark.before("/gesoc/*", MiddleWare.instancia()::verificarSesion);
        Spark.get("/gesoc/home", homeController::mostrarHome,SparkRouter.engine);

        Spark.get("/gesoc/home/perfil",usuarioController::mostrarPerfil,SparkRouter.engine );
        Spark.post("/gesoc/cambio_contrasenia/", usuarioController::cambiarContrasenia);

        Spark.post("/gesoc/cerrar_sesion", logInController::cerrarSesion);

        Spark.get("/obtener_paises", direccionController:: obtenerPaises);

        //----------------------------------------------------BANDEJA DE MENSAJES
        Spark.get("/gesoc/bandeja_de_mensajes", bandejaDeMensajesController::mostrarBandeja, SparkRouter.engine);
        Spark.get("/gesoc/eliminar_mensaje", bandejaDeMensajesController::eliminarMensaje, SparkRouter.engine);
        Spark.get("/gesoc/marcar_leido", bandejaDeMensajesController::marcarLeido, SparkRouter.engine);
        Spark.get("/gesoc/bandeja_de_mensajes/filtrado", bandejaDeMensajesController::filtrarMensajes, SparkRouter.engine);

        //--------------------------------------------------------------VALIDADOR TRANSPARENCIA
        Spark.get("/gesoc/validacion_transparencia", validadorTransparenciaController::mostrarPaginaValidador, SparkRouter.engine);
        Spark.get("/gesoc/planificacion_validador", validadorTransparenciaController::mostrarPaginaPlanificacionValidador, SparkRouter.engine);
        Spark.post("/gesoc/modificar_planificacion_validador", validadorTransparenciaController::modificarPlanificacionValidador);
        Spark.post("/gesoc/ejecutar_validacion_transparencia", validadorTransparenciaController::ejecucionManualValidador);

        //-----------------------------------------------PROVEEDOR
        Spark.post("/gesoc/nuevo_proveedor",entidadJuridicaController::nuevoProveedor);
        Spark.post("/gesoc/nuevo_proveedor_presupuesto/",presupuestoController::nuevoProveedor);

        //---------------------------------------------CARGA EGRESO---------------------------------------------
        Spark.get("/gesoc/cargar_egreso", egresoController::mostrarCargaEgreso, SparkRouter.engine);
        Spark.post("/gesoc/nuevo_egreso", egresoController::nuevoEgreso);
        Spark.get("/obtener_item", egresoController::obtenerItem);

        //--------------------------------------------CARGA PRESUPUESTO
        Spark.get("/gesoc/cargar_presupuesto", presupuestoController::mostrarCargaPresupuesto, SparkRouter.engine);
        Spark.post("/gesoc/nuevo_presupuesto", presupuestoController::nuevoPresupuesto);
        Spark.get("/gesoc/cargar_presupuesto/:id_egreso", presupuestoController::mostrarCargaPresupuestoConEgreso, SparkRouter.engine);

        //----------------------------------------------CARGA INGRESO
        Spark.get("/gesoc/cargar_ingreso", ingresoController::mostrarCargaIngreso, SparkRouter.engine);
        Spark.post("/gesoc/nuevo_ingreso", ingresoController::nuevoIngreso);

        //-----------------------------------------------VISTA: OPERACIONES DE EGRESO--------------------------------
        Spark.get("/gesoc/listado_egresos", egresoController::mostrarListadoEgresos, SparkRouter.engine);
        Spark.get("/egreso_presupuestos", egresoController::obtenerPresupuestos);
        Spark.get("/egreso_items", egresoController::obtenerItems);
        Spark.get("/egreso_categorias", egresoController::obtenerCategorias);
        Spark.get("/egreso_revisores", egresoController::obtenerRevisores);

        //-----------------------------------------------VISTA: LISTADO CRITERIOS Y CATEGORIAS--------------------------
        Spark.get("/criterio_subcriterios", criteriosController::mostrarSubCriterios);
        Spark.get("/criterio_categorias", categoriasController::mostrarCategorias);

        //-----------------------------------------------VISTA: OPERACIONES DE INGRESO--------------------------------
        Spark.get("/gesoc/listado_ingresos", ingresoController::mostrarListadoIngresos, SparkRouter.engine);
        Spark.get("/ingreso_egresos", ingresoController::mostrarIngresoEgresos);
        Spark.get("/presupuesto_categorias", presupuestoController::obtenerCategorias);

        //-----------------------------VISTA: ASOCIACION PRESUPUESTOS CATEGORIAS Y EGRESOS CATEGORIAS---------------------
        Spark.get("/gesoc/categorizar_presupuesto", edicionPresupuestoController::mostrarCategorizacionPrespuesto, SparkRouter.engine);
        Spark.get("/gesoc/categorizar_egreso", egresoController::mostrarCategorizacionEgreso, SparkRouter.engine);
        Spark.get("/informacion_presupuesto", edicionPresupuestoController::obtenerPresupuestoCategorias);
        Spark.get("/informacion_egreso", egresoController::obtenerEgresoCategorias);
        Spark.get("/obtener_criterios", criteriosController::mostrarCriterios);
        Spark.post("/gesoc/egreso_categorias_nuevas",egresoController::editarCategoriasEgreso);
        Spark.post("/gesoc/presupuesto_categorias_nuevas",edicionPresupuestoController::editarCategoriasPresupuesto);

        //-----------------------------VISTA: ASOCIACION INGRESOS Y EGRESOS---------------------------------
        Spark.post("/gesoc/vinculacion_ingreso_egreso", ingresoController::asociarIngresoEgresos);
        Spark.get("/obtener_egreso", egresoController:: obtenerEgreso);
        Spark.get("/obtener_ingreso", ingresoController:: obtenerIngreso);
        Spark.get("/gesoc/vinculo_operaciones", usuarioController::vincularOperaciones, SparkRouter.engine);
        Spark.post("/gesoc/intentar_vinculo", usuarioController::vinculoRealizado);
        Spark.get("/gesoc/hora_vinculo", usuarioController::modificarHoraVinculo, SparkRouter.engine);
        Spark.post("/gesoc/modificar_criterio", usuarioController::modificarCriterioPermanente);
        Spark.post("/gesoc/fecha_vinculo", usuarioController::modificarFechaVinculo);
        Spark.get("/gesoc/asociar_ingreso_egreso", ingresoController::mostrarAsociacionIngresoEgreso, SparkRouter.engine);

        //---------------------------------------------PRESUPUESTOS
        Spark.get("/gesoc/listado_presupuestos", presupuestoController::mostrarListadoPresupuestos, SparkRouter.engine);
        Spark.get("/gesoc/editar_presupuesto", edicionPresupuestoController::mostrarEdicionPresupuesto, SparkRouter.engine);
        Spark.post("/gesoc/editar_presupuesto", edicionPresupuestoController::editarPresupuesto);
        Spark.get("/presupuesto_items", presupuestoController::obtenerItems);

        //-------------------------------------------ADMIN
        Spark.get("/gesoc/administracion", administradorController::mostrarHome,SparkRouter.engine);
        Spark.get("/gesoc/administracion/admin",administradorController::mostrarPerfil,SparkRouter.engine );
        Spark.post("/gesoc/cambio_contrasenia", usuarioController::cambiarContrasenia);
        Spark.post("/gesoc/cambio_contrasenia/admin", administradorController::cambiarContrasenia);
        Spark.get("/gesoc/alta_usuario", administradorController::mostrarAltaUsuario, SparkRouter.engine);
        Spark.post("/gesoc/crear_usuario", administradorController::crearUsuario);
        Spark.get("/gesoc/baja_usuario", administradorController::mostrarBajaUsuario, SparkRouter.engine);
        Spark.post("/gesoc/eliminar_usuario/:usuario_id", administradorController::eliminarUsuario);
        Spark.get("/gesoc/alta_empresa", administradorController::mostrarAltaEmpresa, SparkRouter.engine);
        Spark.get("/gesoc/alta_organizacion", administradorController::mostrarAltaOrganizacion, SparkRouter.engine);
        Spark.get("/gesoc/alta_osc", administradorController::mostrarAltaOsc, SparkRouter.engine);
        Spark.get("/gesoc/baja_organizacion", administradorController::mostrarBajaOrganizacion, SparkRouter.engine);
        Spark.get("/gesoc/admin_crit_cat", administradorController::mostrarAdministracionCriteriosYCategorias, SparkRouter.engine);
        Spark.post("/gesoc/confirmar_alta_osc", administradorController::darAltaOsc);
        Spark.post("/gesoc/modificar_criterios", administradorController::modificarCriterios);
        Spark.post("/gesoc/modificar_categorias", administradorController::modificarCategorias);

        //-------------------------------------------------------LISTADO CRITERIOS CATEGORIAS
        Spark.get("/gesoc/listado_criterios_categorias", categoriasController::mostrarListado, SparkRouter.engine);
        Spark.get("/gesoc/obtener_categorias", categoriasController::mostrarListadoConCategorias, SparkRouter.engine);

        //-----------------------------------------------------EDICION CRITERIOS CATEGORIAS
        Spark.get("/gesoc/edicion_criterios_categorias", categoriasController::mostrarEdicionCriteriosCategorias, SparkRouter.engine);
        Spark.get("obtener_criterios_edicion", criteriosController::mostrarCriterios);
        Spark.post("/gesoc/nuevo_criterio", categoriasController::nuevoCriterio);
        Spark.post("/gesoc/nueva_categoria", categoriasController::nuevaCategoria);

        //-----------------------------------------------------EGRESOS POR CATEGORIA
        Spark.get("/gesoc/egresos_por_categoria", categoriasController::mostrarEgresosCategoria, SparkRouter.engine);
        Spark.get("/categoria_egresos", categoriasController::obtenerEgresosCategoria);

        //-------------------------------------------------------PRESUPUESTOS POR CATEGORIA
        Spark.get("/gesoc/presupuestos_por_categoria", categoriasController::mostrarPresupuestosCategoria, SparkRouter.engine);
        Spark.get("/categoria_presupuestos", categoriasController::obtenerPresupuestosCategoria);

        //--------------------------------------------------EDITAR EGRESO
        Spark.get("/gesoc/editar_egreso", edicionEgresoController::mostrarEdicionEgreso, SparkRouter.engine);
        Spark.post("/gesoc/editar_egreso", edicionEgresoController::editarEgreso);


        //-------------------------------------------------PRUEBAS
        ControllerPruebaPhp controllerPruebaPhp=new ControllerPruebaPhp();

        Spark.get("/php", controllerPruebaPhp::mostrarPhp, SparkRouter.engine);

        FileUploadServlet fileUploadServlet=new FileUploadServlet();
        //Spark.post("/upload", fileUploadServlet::doPost);


        //------------------------------------------------CARGA ARCHIVOS

        Spark.post("/gesoc/upload", archivoController::guardarArchivo);
        Spark.get("/gesoc/download",archivoController::descargarArchivo);

        Spark.get("/error_logueo", ControllerError::mostrarBloqueoLogin, SparkRouter.engine);

    }

}

