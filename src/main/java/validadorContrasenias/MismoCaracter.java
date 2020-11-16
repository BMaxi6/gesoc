package validadorContrasenias;

public class MismoCaracter implements TipoValidacion {

    @Override
    public Boolean validar(String contrasenia) {
        int consecutivosEncontrados=0;
        char caracterGuia = contrasenia.charAt(0);
        int i;
        for (i = 1; i < contrasenia.length() &&
                (consecutivosEncontrados < 2 ) ; i++) {
            if (caracterGuia == contrasenia.charAt(i)) {
                consecutivosEncontrados++;
            }
            caracterGuia = contrasenia.charAt(i);
        }
        return (i == contrasenia.length());//no encontro mismoCaracter
    }
}

