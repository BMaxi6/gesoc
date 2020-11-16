package repositorios;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Repositorio<T> {//busco por T
    private Dao<T> dao;

    public Repositorio(Dao<T> dao) {
        this.dao = dao;
    }
    public void agregar(Object o){
        dao.agregar(o);
    }
    public T buscar(int id){
        return dao.buscar(id);
    }
    public T buscar(CriteriaQuery<T> criteria){
        return this.dao.buscar(criteria);
    }
    public List<T> buscarGrupo(CriteriaQuery<T> criteria){
        return this.dao.buscarGrupo(criteria);
    }
    public void cambiarDao(Dao<T> daoNuevo){
        dao=daoNuevo;
    }
    public void eliminar(Object o){
        dao.eliminar(o);
    }
    public void modificar(Object o){
        dao.modificar(o);
    }
    public List<T> buscarTodos (){return this.dao.buscarTodos();}
}
