package controllers;
import DTOs.PaisJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Categorizacion.Criterio;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Proveedor;
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

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class EntidadJuridicaController {
    /*public Response nuevoProveedor(Request request, Response response){

        String nombreProveedor=request.queryParams("nombre_proveedor");
        long cuit =Long.parseLong(request.queryParams("cuit_proveedor"));
        int codigoPostal= Integer.parseInt(request.queryParams("codigo_postal_proveedor"));
        String calle= request.queryParams("calle_proveedor");
        int altura=Integer.parseInt(request.queryParams("altura_proveedor"));
        int piso= Integer.parseInt(request.queryParams("piso_proveedor"));
        int idPais=Integer.parseInt(request.queryParams("pais_id"));
        int idProvincia= Integer.parseInt(request.queryParams("provincia_id"));
        int idCiudad=Integer.parseInt(request.queryParams("ciudad_id"));


        Repositorio<Ciudad> repoCiudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class);
        Repositorio<Provincia> repoProvincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class);
        Repositorio<Pais> repoPaises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class);
        Ciudad ciudad=repoCiudades.buscar(idCiudad);
        Provincia provincia=repoProvincias.buscar(idProvincia);
        Pais pais=repoPaises.buscar(idPais);
        Direccion direccion = new Direccion(codigoPostal,calle, altura, piso,ciudad,provincia,pais );
        Proveedor nuevoProveedor= new Proveedor(nombreProveedor, cuit,direccion);
        //Repositorio<Proveedor> repoProveedores= FactoryRepositorio.instancia().obtenerRepositorio(Proveedor.class);
        //repoProveedores.agregar(nuevoProveedor);

        int idUsuario= Integer.parseInt(request.params("usuario_id"));
        System.out.println("-------------------------------------");
        System.out.println(idUsuario);
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica organizacion= usuario.getOrganizacion();

        Repositorio<EntidadJuridica> entidadJuridicaRepositorio= FactoryRepositorio.instancia().obtenerRepositorio(EntidadJuridica.class);

        organizacion.agregarProveedor(nuevoProveedor);
        entidadJuridicaRepositorio.modificar(organizacion);
        response.redirect(new Formatter().format("/cargar_egreso/%d", idUsuario).toString());
        return response;
    }*/
    public Response nuevoProveedor(Request request, Response response){
        int idUsuario= request.session().attribute("usuario_id");
        String path="/gesoc/cargar_egreso";

        return this.crearNuevoProveedor(request, response,path,path);
    }
    public Response crearNuevoProveedor(Request request, Response response, String pathExito, String pathError){
        String nombreProveedor, calle;
        long cuit;
        int codigoPostal, altura, piso, idPais, idProvincia, idCiudad;
        try{
            nombreProveedor=request.queryParams("nombre_proveedor");
            cuit =Long.parseLong(request.queryParams("cuit_proveedor"));
            codigoPostal= Integer.parseInt(request.queryParams("codigo_postal_proveedor"));
            calle= request.queryParams("calle_proveedor");
            altura=Integer.parseInt(request.queryParams("altura_proveedor"));
            piso= Integer.parseInt(request.queryParams("piso_proveedor"));
            idPais=Integer.parseInt(request.queryParams("pais_id"));
            idProvincia= Integer.parseInt(request.queryParams("provincia_id"));
            idCiudad=Integer.parseInt(request.queryParams("ciudad_id"));

        }catch(Exception e){
            response.redirect(pathError);
            return response;
        }

        Repositorio<Ciudad> repoCiudades= FactoryRepositorio.instancia().obtenerRepositorio(Ciudad.class);
        Repositorio<Provincia> repoProvincias= FactoryRepositorio.instancia().obtenerRepositorio(Provincia.class);
        Repositorio<Pais> repoPaises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class);
        Ciudad ciudad=repoCiudades.buscar(idCiudad);
        Provincia provincia=repoProvincias.buscar(idProvincia);
        Pais pais=repoPaises.buscar(idPais);
        Direccion direccion = new Direccion(codigoPostal,calle, altura, piso,ciudad,provincia,pais );
        Proveedor nuevoProveedor= new Proveedor(nombreProveedor, cuit,direccion);
        //Repositorio<Proveedor> repoProveedores= FactoryRepositorio.instancia().obtenerRepositorio(Proveedor.class);
        //repoProveedores.agregar(nuevoProveedor);

        int idUsuario= request.session().attribute("usuario_id");
        System.out.println("-------------------------------------");
        System.out.println(idUsuario);
        Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
        EntidadJuridica organizacion= usuario.getOrganizacion();

        Repositorio<EntidadJuridica> entidadJuridicaRepositorio= FactoryRepositorio.instancia().obtenerRepositorio(EntidadJuridica.class);

        organizacion.agregarProveedor(nuevoProveedor);
        entidadJuridicaRepositorio.modificar(organizacion);
        response.redirect(pathExito);
        return response;


    }

    /*public String obtenerCriterios(Request request, Response response){
        int idUsuario=Integer.parseInt(request.queryParams("id_usuario"));
        List<Criterio> criterios= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario).getOrganizacion().getCriterios();

        Type listType = new TypeToken<ArrayList<Criterio>>(){}.getType();

        Gson gson = new Gson();
        String jsonCriterios = gson.toJson(criterios, listType);

        response.type("application/json");
        return jsonCriterios;
    }*/
}
