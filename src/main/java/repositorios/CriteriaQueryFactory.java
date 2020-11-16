package repositorios;

import Database.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CriteriaQueryFactory<T,K> { //T es el tipo de dato que busco y K el tipo del atributo
    public static CriteriaBuilder cb=EntityManagerHelper.getEntityManager().getCriteriaBuilder();

    public CriteriaQuery<T> busquedaPorNombre(Class<T> clase, String nombre){
        return this.busquedaPorAtributo(clase, "nombre", (K) nombre);
    }
    public CriteriaQuery busquedaPorAtributo(Class<T> clase,String nombreAtributo ,K atributo){//nombreAtributo es textual el nombre del atributo en el dominio de objetos
        CriteriaQuery<T> cq= cb.createQuery(clase);
        Root<T> root= cq.from(clase);
        cq.select(root).where(cb.equal(root.get(nombreAtributo),atributo));
        return cq;
    }
}
