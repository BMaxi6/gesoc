package validadorContrasenias;

import java.util.ArrayList;
import java.util.Scanner;

public class ValidadorDeContrasenias {
    private static ValidadorDeContrasenias instancia=null;

    ArrayList<TipoValidacion> validaciones = new ArrayList<TipoValidacion>();

    // -------------------------- Configurar Validaciones -------------------------- //

    public static ValidadorDeContrasenias instancia(){
        if(instancia==null)
            instancia=new ValidadorDeContrasenias();
        return instancia;
    }

    public ValidadorDeContrasenias() {
        ArrayList<TipoValidacion> validaciones = new ArrayList<TipoValidacion>();
        validaciones.add(new ContraseniaComun());
        validaciones.add(new MismoCaracter());
        validaciones.add(new MasDe2CaracteresConsecutivos());
        validaciones.add(new CantidadCaracteresCorrectos());
        this.validaciones = validaciones;
    }

    public String validacionesContrasenia(){
        String validaciones = "La contrasenia debe: no repetir 3 caracteres seguidos, ni presentarlos en escalera(123). Tener una longitud entre 8 y 64 caracteres.";
        return validaciones;
    }

    public void agregarValidacion(TipoValidacion validacion){ validaciones.add(validacion); }

    public void eliminarValidacion(TipoValidacion validacion){
        validaciones.remove(validacion);
    }

    //-------------------------- Ingresar contrasenias --------------------------//

    public static class DebilException extends Exception {
        public DebilException(String msg) {
            super(msg);
        }
    }

    public boolean validarContrasenia(String contrasenia){
        if (!this.esContraseniaSegura(contrasenia)) {
            DebilException debilException = new DebilException("Su contrasenia no cumple con las condiciones.\n");
            try {
                throw debilException;
            }catch(DebilException exception){
                System.out.println("Su contraseña no cumple las condiciones");
            }
            this.validarContrasenia(contrasenia);
        } else {
            System.out.println("contraseña aceptada.");
            return true;
        }
        return false;
    }


    public boolean esContraseniaSegura(String contrasenia){
        boolean x;
        for(TipoValidacion validacion: validaciones){
            if(!validacion.validar(contrasenia)) return false;
        }
        eliminarEspacios(contrasenia);
        return true;
    }

    // -------------------------- Eliminar Espacios -------------------------- //

    String eliminarEspacios(String contrasenia){
       StringBuffer nuevaContrasenia=new StringBuffer();
       nuevaContrasenia.ensureCapacity(64);
       int espaciosEvitados=0;
       char charAnterior='x';
       char charActual;
       boolean seBorraronEspacios=false;
       for(int i=0;i<contrasenia.length();i++){
           charActual=contrasenia.charAt(i);
           if(!(charActual==' ' && charAnterior==' ')){
               nuevaContrasenia.insert(i-espaciosEvitados,charActual);
           }else{
               espaciosEvitados++;
               seBorraronEspacios=true;
           }
           charAnterior=charActual;
       }
       // Este if podría resumirse a una sola sentencia.
       if(seBorraronEspacios)
           System.out.println("Se han reemplazado los espacios multiples por espacios simples");
       return nuevaContrasenia.toString();
    }
}
