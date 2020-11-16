package domain.Categorizacion;

import repositorios.Persistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="criterio")
public class Criterio extends Persistente {

    @Column(name="nombre")
    private String nombre;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name= "id_criterio")
    private List<Categoria> categorias=new ArrayList<Categoria>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name= "id_criterio_padre")
    private List<Criterio> criteriosHijos = new ArrayList<Criterio>();

   public  void agregarCategoria(Categoria categoria){
        categorias.add(categoria);
    }
    public void agregarSubCriterio(Criterio criterio){
        criteriosHijos.add(criterio);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    public void setCategorias(Categoria ... categorias){
        Arrays.stream(categorias).forEach(cat->this.categorias.add(cat));
    }

    public List<Criterio> getCriteriosHijos() {
        return criteriosHijos;
    }

    public void setCriteriosHijos(List<Criterio> criteriosHijos) {
        this.criteriosHijos = criteriosHijos;
    }

    public void setCriterios(Criterio ... criterios) {
        Arrays.stream(criterios).forEach(criterio->this.criteriosHijos.add(criterio));
    }

    public Criterio(){

    }

    public Boolean contieneCategoria(List<Categoria> categorias){
       for(int i=0; i<categorias.size(); i++){
           if(this.getCategorias().contains(categorias.get(i))){
               return true;
           }
       }
       return false;
    }



}