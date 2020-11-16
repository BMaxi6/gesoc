package domain.EntidadesOrganizacionales.CategoriaEmpresa;

import domain.EntidadesOrganizacionales.Empresa;
import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="categoria_empresa")
public class CategoriaEmpresa extends Persistente {

    @Column(name="nombre")
    private String nombre;

    @Column(name="limite_ventas")
    private double limiteVentas;

    @Column(name="limite_personal")
    private int limitePersonal;

    @Column(name="sector")
    @Enumerated(value = EnumType.STRING)
    private Sector sector;

    public CategoriaEmpresa(String nombre, int limiteVentas, int limitePersonal, Sector sector){
        this.nombre = nombre;
        this.limiteVentas = limiteVentas;
        this.limitePersonal = limitePersonal;
        this.sector = sector;
    }

    public CategoriaEmpresa(){}

    public boolean pertenezcoALaCategoria(Empresa empresa){
        return empresa.getSector() == this.sector && empresa.getPromedioVentas() >= this.limiteVentas && empresa.getCantidadPersonal() >= this.limitePersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLimiteVentas() {
        return limiteVentas;
    }

    public void setLimiteVentas(int limiteVentas) {
        this.limiteVentas = limiteVentas;
    }

    public int getLimitePersonal() {
        return limitePersonal;
    }

    public void setLimitePersonal(int limitePersonal) {
        this.limitePersonal = limitePersonal;
    }
}
