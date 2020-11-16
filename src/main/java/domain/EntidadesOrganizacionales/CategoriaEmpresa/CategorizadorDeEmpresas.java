package domain.EntidadesOrganizacionales.CategoriaEmpresa;

import domain.EntidadesOrganizacionales.Empresa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategorizadorDeEmpresas {

    private List<CategoriaEmpresa> categorias = new ArrayList<CategoriaEmpresa>();

    public CategorizadorDeEmpresas(CategoriaEmpresa ... categorias){
        this.categorias = new ArrayList<CategoriaEmpresa>();
        this.categorias.addAll(Arrays.asList(categorias));
    }

    public CategorizadorDeEmpresas(){ }

    public void agregarCategoria(CategoriaEmpresa unaCategoria){
        this.categorias.add(unaCategoria);
    }

    public CategoriaEmpresa obtenerCategoria(Empresa empresa) {
        return categorias.stream().filter(cat -> cat.pertenezcoALaCategoria(empresa)).collect(Collectors.toList()).get(0);
    }

    public List<CategoriaEmpresa> getCategorias(){
        return categorias;
    }
}