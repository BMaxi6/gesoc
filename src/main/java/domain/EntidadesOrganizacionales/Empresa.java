package domain.EntidadesOrganizacionales;

import domain.EntidadesOrganizacionales.CategoriaEmpresa.CategoriaEmpresa;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.CategorizadorDeEmpresas;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.Rubro;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.Sector;
import domain.Personas.Usuario;
import domain.ubicaciones.Direccion;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("empresa")
public class Empresa extends EntidadJuridica {

    @Transient
    private String descriptor = "Empresa";

    @Column(name = "promedio_ventas")
    private Double promedioVentas;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private CategoriaEmpresa categoria;

    @Column(name = "sector")
    @Enumerated(value = EnumType.STRING)
    private Sector sector;

    public Empresa(int cantPersonal, Double ingresosAnuales, Sector sector, CategorizadorDeEmpresas categorizador) {
        this.setCantidadPersonal(cantPersonal);
        this.sector = sector;
        this.promedioVentas = ingresosAnuales;
        this.recategorizar(categorizador);
    }

    public Empresa() {
    }

    public void recategorizar(CategorizadorDeEmpresas categorizador) {
        this.categoria = categorizador.obtenerCategoria(this);
    }

    // --------------------------- Getter y Setter --------------------------- //

    @Override
    public String getDescriptor() {
        return descriptor;
    }

    public CategoriaEmpresa getCategoria() {
        return categoria;
    }

    public Double getPromedioVentas() {
        return promedioVentas;
    }

    public Sector getSector() {
        return sector;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public void setPromedioVentas(Double promedioVentas) {
        this.promedioVentas = promedioVentas;
    }

    public void setCategoria(CategoriaEmpresa categoria) {
        this.categoria = categoria;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Empresa(String descriptor, Double promedioVentas, CategoriaEmpresa categoria, Sector sector) {
        this.descriptor = descriptor;
        this.promedioVentas = promedioVentas;
        this.categoria = categoria;
        this.sector = sector;
    }

    public Empresa(String razonSocial, String nombreFicticio, Direccion direccion, String codigoInscripcionIGJ, ArrayList<Usuario> usuarios, Integer cantidadPers, Rubro actividad, String descriptor, Double promedioVentas, CategoriaEmpresa categoria, Sector sector) {
        super(razonSocial, nombreFicticio, direccion, codigoInscripcionIGJ, usuarios, cantidadPers, actividad);
        this.descriptor = descriptor;
        this.promedioVentas = promedioVentas;
        this.categoria = categoria;
        this.sector = sector;
    }


}
