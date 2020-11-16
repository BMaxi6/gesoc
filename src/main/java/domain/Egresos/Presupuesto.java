package domain.Egresos;

import domain.Categorizacion.*;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.ItemPresupuesto;
import domain.Personas.Proveedor;
import repositorios.Persistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="presupuesto")
public class Presupuesto extends Persistente {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_operacion_egreso", referencedColumnName = "id")
    private OperacionDeEgreso operacion;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="id_presupuesto")
    private List<ItemPresupuesto> items = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_proveedor", referencedColumnName = "id")
    private Proveedor proveedorP;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_entidad_juridica", referencedColumnName = "id")
    private EntidadJuridica organizacion;

    @Column(name="elegido")
    private Boolean elegido;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Categoria> categorias =new ArrayList<Categoria>();

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_documento_comercial", referencedColumnName = "id")
    private DocumentoComercial documento;

    @Column(name="fecha")
    private LocalDate fecha;

    public EntidadJuridica getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(EntidadJuridica organizacion) {
        this.organizacion = organizacion;
    }

    public Double valorTotal(){
        return this.items.stream().mapToDouble(p->p.valorTotal()).sum();
    }

    public void setElegido(Boolean eleccion){
        this.elegido = eleccion;
    }

    public Boolean getElegido() {
        return elegido;
    }

    public void agregarCategoria(Categoria cat){
        this.categorias.add(cat);
    }

    public void agregarItem(ItemPresupuesto item){
        items.add(item);
    }

    public Presupuesto presupuesto(){
        return new Presupuesto();
    }

    public OperacionDeEgreso getOperacion() {
        return operacion;
    }

    public void setOperacion(OperacionDeEgreso operacion) {
        this.operacion = operacion;
    }

    public List<ItemPresupuesto> getItems() {
        return items;
    }

    public void setItems(List<ItemPresupuesto> items) {
        this.items = items;
    }

    public Proveedor getProveedorP() {
        return proveedorP;
    }

    public void setProveedorP(Proveedor proveedorP) {
        this.proveedorP = proveedorP;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public DocumentoComercial getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoComercial documento) {
        this.documento = documento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Presupuesto(){

    }

}
