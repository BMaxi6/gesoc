package repositorios;

import Database.EntityManagerHelper;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DaoHibernate<T> implements Dao<T> {
    private Class<T> type;

    public DaoHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {

        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> critera = builder.createQuery(this.type);
        critera.from(type);
        List<T> entities = EntityManagerHelper.getEntityManager().createQuery(critera).getResultList();

        //EntityManagerHelper.closeEntityManager();
        return entities;
    }

    @Override
    public T buscar(int id) {

        T t= (T) EntityManagerHelper.getEntityManager().find(type, id);
        //EntityManagerHelper.closeEntityManager();
        return t;
    }

    @Override
    public T buscar(CriteriaQuery<T> criteria) {


        try{
            T t=(T) (EntityManagerHelper.getEntityManager()
                    .createQuery(criteria)
                    .getSingleResult());
            //EntityManagerHelper.closeEntityManager();
            return t;
        } catch(NoResultException e){
            return null;
        }

    }

    public List<T> buscarGrupo(CriteriaQuery<T> criteria){
        try{
            List<T> t=(EntityManagerHelper.getEntityManager()
                    .createQuery(criteria)).getResultList();
            //EntityManagerHelper.closeEntityManager();
            return t;
        } catch(NoResultException e){
            return null;
        }
    }

    @Override
    public void agregar(Object unObjeto) {


        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();

        //EntityManagerHelper.closeEntityManager();
    }

    @Override
    public void modificar(Object unObjeto) {


        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();

        //EntityManagerHelper.closeEntityManager();
    }

    @Override
    public void eliminar(Object unObjeto) {


        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();

        //EntityManagerHelper.closeEntityManager();
    }
}
