package validadorContrasenias;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncriptadorDeContrasenias {

    private static EncriptadorDeContrasenias instancia = null;

    public static EncriptadorDeContrasenias instancia(){
        if(instancia==null){
            instancia=new EncriptadorDeContrasenias();
        }
        return instancia;
    }

    public String encriptarContrasenia (String contrasenia){
        return BCrypt.hashpw(contrasenia, BCrypt.gensalt(10));
    }

    public Boolean contraseniaCoincide(String contrasenia, String contraseniaEncriptada){
        return BCrypt.checkpw(contrasenia, contraseniaEncriptada);
    }
}
