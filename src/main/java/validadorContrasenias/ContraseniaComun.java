package validadorContrasenias;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ContraseniaComun implements TipoValidacion {
    ArrayList<String> malasContrasenias;
    public void cargarContraseniasComunes() {
        try {
            File archivoContrasenias = new File("contraseniasComunes.txt");
            Scanner sc = new Scanner(archivoContrasenias);
            ArrayList<String> arrayStrings = new ArrayList<String>();
            for (int i = 0; sc.hasNext(); i++) {
                String s = sc.next();
                arrayStrings.add(s);
            }
            this.setMalasContrasenias(arrayStrings);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ContraseniaComun(){
        this.cargarContraseniasComunes();
    }
    @Override
    public Boolean validar(String contrasenia) {
        return !malasContrasenias.contains(contrasenia);
    }

    public void setMalasContrasenias(ArrayList<String> malasContrasenias) {
        this.malasContrasenias = malasContrasenias;
    }
}
