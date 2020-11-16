package validadorContrasenias;

public class MasDe2CaracteresConsecutivos implements TipoValidacion{

    @Override
    public Boolean validar(String contrasenia) {
        int consecutivosEncontrados=0;
        char charAnterior = ' ';
        char charActual;

        for(int i=0;i<contrasenia.length()&&consecutivosEncontrados<2;i++){
            charActual=contrasenia.charAt(i);
            if(i>0 && (charActual==charAnterior+1)){
                consecutivosEncontrados++;
            } else {
                consecutivosEncontrados = 0;
            }
            charAnterior=charActual;
        }

        return !(consecutivosEncontrados>1);
    }
}
