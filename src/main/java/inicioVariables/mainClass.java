package inicioVariables;

import AsociadorEgresoIngreso.ActionProvider;
import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import Database.EntityManagerHelper;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.CategoriaEmpresa;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.CategorizadorDeEmpresas;
import domain.Personas.Usuario;
import org.hibernate.Criteria;
import spark.Spark;
import spark.SparkServer;
import validadorContrasenias.ValidadorDeContrasenias;
import repositorios.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class mainClass {



    public static ValidadorDeContrasenias validadorContrasenias;
    public static Repositorio<String> repoUsuarios;
    public static Repositorio<String> repoAdmin;
    public static Repositorio<Integer> repoEgresos;
    public static Repositorio<Integer> repoProveedores;
    public static Repositorio<String> repoMediosDePago;
    public static Repositorio<String> repoEntidadesJuridicas;
    public static CategorizadorDeEmpresas categorizadorDeEmpresas;
    public static CategoriaEmpresa microConstruccion;
    public static CategoriaEmpresa pequeniaConstruccion;
    public static CategoriaEmpresa microServicios;
    public static List<CategoriaEmpresa> categorias;

    public static void main(String[] args) throws IOException {

//        ServicioMl servicioMl= ServicioMl.instancia();
//
//        List<Moneda> domain.monedas=servicioMl.obtenerMonedas();
//
//        System.out.println("------------------Monedas");
//        for(Moneda m: domain.monedas){
//            System.out.println("Id: " + m.getId());
//            System.out.println("Descripcion: " + m.getId());
//            System.out.println("Símbolo: " + m.getId());
//            System.out.println("Lugares decimales: " + m.getLugaresDecimales());
//        }
//
//        List<Pais> lista= servicioMl.listaPaises();
//        for(Pais pais: lista){
//            System.out.println("Nombre: " + pais.getNombre() + "   " + "Id: "+ pais.getId());
//        }
//
//
//        Pais paisPrueba=lista.get(0);
//        System.out.println("Provincias de " + paisPrueba.getNombre());
//
//
//        for(Provincia p: paisPrueba.getProvincias()){
//            System.out.println( "Nombre " + p.getNombre() + "          Id " + p.getId());
//        }
//
//        Provincia provinciaPrueba=paisPrueba.getProvincias().get(0);
//        System.out.println("Ciudades de " + provinciaPrueba.getNombre());
//
//        for(Ciudad c: provinciaPrueba.getCiudades()){
//            System.out.println(c.getNombre());
//        }
        /*

        HashMap<Integer, String> criterios = ActionProvider.obtenerCriteriosDeAsociacion().invocar();

        List<Ingreso> ingresos = new ArrayList<Ingreso>();
        ingresos.add(new Ingreso(20.0, null, "compra", LocalDate.now()));
        ActionProvider.obtenerGuardarIngresos().invocar(ingresos);

        List<OperacionDeEgreso> egresos = new ArrayList<OperacionDeEgreso>();
        egresos.add(new OperacionDeEgreso(1, 20.0, null, null, 2));
        ActionProvider.obtenerGuardarEgresos().invocar(egresos);

        System.out.print("Todo cargado");
         */
    }
}