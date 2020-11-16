package domain.EntidadesOrganizacionales;

//import com.sun.tools.jdeprscan.scan.Scan;

import domain.Categorizacion.Criterio;
import domain.EntidadesOrganizacionales.CategoriaEmpresa.Rubro;
import domain.Personas.Proveedor;
import domain.Personas.Usuario;
//import jdk.javadoc.internal.doclets.toolkit.taglets.DocRootTaglet;
import repositorios.Persistente;
import repositorios.Repositorio;
import domain.ubicaciones.Direccion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="entidad_juridica")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_entidad_juridica")
public class EntidadJuridica extends Persistente {

    @Column(name="razon_social")
    private String razonSocial;

    @Column(name="nombre_ficticio")
    private String nombreFicticio;

    @Column(name="activo")
    protected boolean activo=true;

    @Column(name="codigo_inscripcion_igj")
    private String codigoInscripcionIGJ;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_direccion", referencedColumnName = "id")
    private Direccion direccion;

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<Usuario>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="id_entidad_juridica")
    private List<Criterio> criterios = new ArrayList<Criterio>();

    @Column(name="cantidad_personal")
    private Integer cantidadPersonal;

    @Column(name="actividad")
    @Enumerated(value = EnumType.STRING)
    private Rubro actividad;

    @Column(name="cuit")
    private String cuit;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_entidad_juridica")
    private List<Proveedor> proveedores= new ArrayList<Proveedor>();

    // ------------------------ Entidades y Usuarios ------------------------ //

    public void agregarUsuario(Usuario usuario){
        usuarios.add(usuario);

    }

    public void eliminarUsuario(Usuario usuario, Repositorio<String> repo){
        usuarios.remove(usuario);
        repo.modificar(this);
    }



    public void agregarCriterio(Criterio criterio){
        this.criterios.add(criterio);
    }

    public void agregarProveedor(Proveedor proveedor){
        proveedores.add(proveedor);
    }
    // ------------------------ Getter y Setter ------------------------ //


    public EntidadJuridica(){}

    public EntidadJuridica(String razonSocial, String nombreFicticio, Direccion direccion, String codigoInscripcionIGJ, ArrayList<Usuario> usuarios, Integer cantidadPers, Rubro actividad) {
        this.razonSocial = razonSocial;
        this.nombreFicticio = nombreFicticio;
        this.direccion = direccion;
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
        this.usuarios = usuarios;
        this.actividad=actividad;
        this.cantidadPersonal=cantidadPers;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccionPostal(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getCodigoInscripcionIGJ() {
        return codigoInscripcionIGJ;
    }

    public void setCodigoInscripcionIGJ(String codigoInscripcionIGJ) {
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }



    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getCantidadPersonal() {
        return cantidadPersonal;
    }

    public void setCantidadPersonal(Integer cantidadPersonal) {
        this.cantidadPersonal = cantidadPersonal;
    }

    public Rubro getActividad() {
        return actividad;
    }

    public void setActividad(Rubro actividad) {
        this.actividad = actividad;
    }
    public String getDescriptor() {
        return "";
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }



    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<Criterio> criterios) {
        this.criterios = criterios;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void darBajaLogica(){
        this.setActivo(false);
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
}
