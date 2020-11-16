package repositorios;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoMemoria<T> implements Dao<T>{
    private ArrayList<Persistente> list = new ArrayList<>();

    @Override
    public T buscar(CriteriaQuery<T> criteria) {
        return null;
    }

    @Override
    public List<T> buscarGrupo(CriteriaQuery<T> criteria) {
        return null;
    }

    @Override
    public void agregar(Object p){
        list.add((Persistente)p);
    }

    @Override
    public T buscar(int id) {
       return (T) this.list.stream().filter(e->e.getId()==id).findFirst().get();
    }


    @Override
    public void eliminar(Object o) {
        this.list.remove(o);
    }

    @Override
    public void modificar(Object o) {
        Persistente objetoSimilar= (Persistente) list.stream().filter(obj->obj.getId()==((Persistente)o).getId()).findFirst().get();
        this.list.set(this.list.indexOf(objetoSimilar),(Persistente)o);
    }

    @Override
    public List<T> buscarTodos() {
        return (List<T>)this.list;
    }

    public DaoMemoria(ArrayList<Persistente> list) {
        this.list = list;
    }

    public DaoMemoria(){

    }
}
