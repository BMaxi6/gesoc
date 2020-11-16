package repositorios;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface Dao<T> {
    T buscar(CriteriaQuery<T> criteria);
    public List<T> buscarGrupo(CriteriaQuery<T> criteria);
    public void agregar(Object o);
    public T buscar(int id);
    public void eliminar(Object o);
    //public boolean existe(T t);
    public void modificar(Object o);//t del que quiero modificar y el p final
    public List<T> buscarTodos();
}
