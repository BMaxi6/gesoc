package validadorContrasenias;

public class CantidadCaracteresCorrectos implements TipoValidacion{
    @Override
    public Boolean validar(String contrasenia) {
        return contrasenia.length() >= 8 && contrasenia.length() <= 64;
    }
}
