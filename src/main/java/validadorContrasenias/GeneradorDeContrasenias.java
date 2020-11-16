package validadorContrasenias;

import java.util.Random;

public class GeneradorDeContrasenias {
    private static GeneradorDeContrasenias instancia=null;
    public String generarContraseniaAleatoria(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static GeneradorDeContrasenias instancia(){
        if(instancia==null)
            instancia=new GeneradorDeContrasenias();
        return instancia;
    }
}
