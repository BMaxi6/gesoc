package repositorios;
import config.config;
import bandejaDeMensajes.Mensaje;

import java.util.HashMap;

public class FactoryRepositorio {
    private static FactoryRepositorio instancia= null;
    private static HashMap<String, Repositorio> repos;
    public static <T> Repositorio<T> obtenerRepositorio(Class<T> clase){
        Repositorio repositorio;
        if(repos.containsKey(clase.getName())){
            repositorio=repos.get(clase.getName());
        }else{
            if(config.database_on){
                repositorio= new Repositorio<T>(new DaoHibernate<T>(clase));
            }else{
                repositorio= new Repositorio<T>(new DaoMemoria<>());
            }
            repos.put(clase.toString(), repositorio);
        }
        return repositorio;
    }
    public static FactoryRepositorio instancia(){
        if(instancia==null){
            instancia= new FactoryRepositorio();
            repos= new HashMap<String, Repositorio>();
        }

        return instancia;
    }

}
