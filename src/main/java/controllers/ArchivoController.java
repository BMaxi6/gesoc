package controllers;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Formatter;

import domain.Personas.Usuario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import repositorios.CriteriaQueryFactory;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;
import spark.Request;
import spark.Response;

public class ArchivoController {
   public String guardarArchivo(Request req, Response res) throws IOException, ServletException {
       int idUsuario= req.session().attribute("usuario_id");
       Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
       int idOrg= usuario.getOrganizacion().getId();
       req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("tmp/"));
       Part filePart = req.raw().getPart("archivo");

        System.out.println("hola1");
       try (InputStream inputStream = filePart.getInputStream()) {

           File directorio= new File("uploads/"+idOrg);
           if(!directorio.exists()){
               directorio.mkdirs();
           }

           OutputStream outputStream = new FileOutputStream("uploads/"  + idOrg + "/"+ filePart.getSubmittedFileName());
           System.out.println("hola2");
           IOUtils.copy(inputStream, outputStream);
           System.out.println("hola2.5");
           outputStream.close();
       }
       System.out.println("hola3");
        return "Archivo guardado";

   }
   public Response descargarArchivo(Request request, Response response) throws IOException {
       int idUsuario= request.session().attribute("usuario_id");
       Usuario usuario= FactoryRepositorio.instancia().obtenerRepositorio(Usuario.class).buscar(idUsuario);
       int idOrg= usuario.getOrganizacion().getId();
       String nombreArchivo= request.queryParams("nombre_archivo");
       response.header("Content-disposition", "attachment; filename="+ ArchivoController.sacarBarras(nombreArchivo) + ";");

       File file = new File("uploads/" + idOrg+ "/" + nombreArchivo);
       OutputStream outputStream = response.raw().getOutputStream();
       outputStream.write(Files.readAllBytes(file.toPath()));
       outputStream.flush();
       return response;
   }

   public static String obtenerNombreArchivo(String path){
        if(path==null)
            return null;
        String arr[]= path.split("\\\\");
        return arr[arr.length-1];
   }

   public static String sacarBarras(String path){
       if(path==null)
           return null;
       String arr[]= path.split("/");
       return arr[arr.length-1];
   }
   public static String asociarArchivoAEgreso(int idEgreso,int idOrg ,String nombreArchivo) throws IOException {
       /*File directorio= new File("uploads/"+ idOrg +"/"+"op_egreso_"+ idEgreso);
       if(!directorio.exists()){
           directorio.mkdirs();
       }
       String pathFuente= "uploads/" + idOrg+ "/" + nombreArchivo;
       String pathDestino="uploads/" + idOrg + "/op_egreso_" + idEgreso + "/" + nombreArchivo;

       copiarArchivo(pathFuente, pathDestino);
       return "op_egreso_" + idEgreso + "/" + nombreArchivo;*/
       return  asociarArchivoAEntidad(idEgreso, idOrg, nombreArchivo, "op_egreso_");

   }

    public static String asociarArchivoAPresupuesto(int idPresupuesto,int idOrg ,String nombreArchivo) throws IOException {

        return  asociarArchivoAEntidad(idPresupuesto, idOrg, nombreArchivo, "presupuesto_");
    }

    public static String asociarArchivoAEntidad(int idEntidad, int idOrg, String nombreArchivo, String nombreCarpeta) throws IOException {
        File directorio= new File("uploads/"+ idOrg +"/"+nombreCarpeta+ idEntidad);
        if(!directorio.exists()){
            directorio.mkdirs();
        }
        String pathFuente= "uploads/" + idOrg+ "/" + nombreArchivo;
        String pathDestino="uploads/" + idOrg +"/" + nombreCarpeta + idEntidad + "/" + nombreArchivo;

        copiarArchivo(pathFuente, pathDestino);
        return nombreCarpeta + idEntidad + "/" + nombreArchivo;
    }

   public static void copiarArchivo(String pathFuente, String pathDestino) throws IOException {
       File archivoOriginal= new File(pathFuente);
       File archivoNuevo= new File(pathDestino);


        try{
            FileUtils.copyFile(archivoOriginal,archivoNuevo);
            //Files.copy(archivoOriginal.toPath(), archivoNuevo.toPath());
        }catch(Exception e){

        }


   }

}
