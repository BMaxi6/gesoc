package DTOs;

import domain.Categorizacion.Categoria;
import domain.Categorizacion.Criterio;
import java.util.ArrayList;
import java.util.List;

public class CriterioDTO {
    private int id;
    private String nombre;
    private List<Categoria> categorias =new ArrayList<Categoria>();

    public CriterioDTO(Criterio criterio){
        this.id = criterio.getId();
        this.nombre = criterio.getNombre();
        this.categorias = criterio.getCategorias();
    }

    public CriterioDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
