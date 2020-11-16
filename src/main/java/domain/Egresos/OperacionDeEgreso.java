package domain.Egresos;

import domain.Categorizacion.*;
import Database.Converters.CriterioDeSeleccionConverter;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Items.Item;
import domain.Pagos.Pago;
import domain.Personas.Proveedor;
import domain.Personas.Usuario;
import bandejaDeMensajes.Mensaje;
import domain.monedas.Moneda;
import inicioVariables.mainClass;
import repositorios.Persistente;
import domain.validadorDeTransparencia.CriterioDeSeleccionPresupuesto;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="operacion_de_egreso")
public class OperacionDeEgreso extends Persistente {

    @Column(name="id_operacion_egreso")
    private Integer numeroOp;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id")
    private Proveedor proveedor;

    @Column(name="fecha")
   // @Convert(converter = LocalDateConverter.class)
    private LocalDate fecha;

    @Column(name="valor_total")
    private Double ValorTotal=0.0;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_pago", referencedColumnName = "id")
    private Pago pago;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_documento_comercial", referencedColumnName = "id")
    private DocumentoComercial docComercial;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_entidad_juridica", referencedColumnName = "id")
    private EntidadJuridica organizacion;

    @OneToMany(mappedBy = "operacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();

    @Column(name="cantidad_presupuestos_requeridos")
    private int cantidadPresupuestosRequeridos;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name= "id_operacion_egreso")
    private List<Item> items = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Usuario> revisores = new ArrayList<Usuario>();

    @Column(name="id_criterio_seleccion_presupuesto")
    @Convert(converter= CriterioDeSeleccionConverter.class)
    private CriterioDeSeleccionPresupuesto criterioSelecc;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_ingreso", referencedColumnName = "id")
    private Ingreso ingreso;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_moneda", referencedColumnName = "id")
    private Moneda moneda;

    @ManyToMany(cascade ={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Categoria> categorias =new ArrayList<Categoria>();

    @Column(name="validada")
    private boolean validada=false;

    public void enviarMensajeARevisores(Mensaje mensaje){
        this.getRevisores().forEach(x->x.getBandejaDeMensajes().recibirMensaje(new Mensaje(mensaje.getMensaje(), mensaje.getFechaCreacion())));
    }
    public void registrarOperacion(EntidadJuridica entidadJuridica){
        mainClass.repoEgresos.agregar(this);
    }

    public void agregarRevisor(Usuario usuario){
        this.revisores.add(usuario);
    }

    public void eliminarRevisor(Usuario usuario){
        this.revisores.remove(usuario);
    }

    public void agregarPresupuesto(Presupuesto presupuesto){
        presupuestos.add(presupuesto);
    }

    public void agregarCategoria(Categoria cat){
        this.categorias.add(cat);
    }



    protected void registrarPago(Pago pago){
        this.pago = pago;
    }

   public void asociarIngreso(Ingreso unIngreso){
        this.ingreso = unIngreso;
    }

    public void asociarIngresos() throws IOException {
       // Integer idCriterioSeleccionado = ActionProvider.obtenerCriteriosDeAsociacion().invocar();

    }



    public Ingreso getIngreso(){
        return this.ingreso;
    }

    public Double valorTotal(){
        return this.items.stream().mapToDouble(p->p.valorTotal()).sum();
    }

    //------------------------------------------ CONSTRUCTOR, GETTERS, SETTERS

    public OperacionDeEgreso(){

    }
    public OperacionDeEgreso(Integer numeroOp, Double valorTotal, EntidadJuridica organizacion, List<Item> items, int cantidadPresupuestosRequeridos, Moneda moneda) {
        this.numeroOp = numeroOp;
        this.fecha = LocalDate.now();
        this.ValorTotal = valorTotal;
        this.organizacion = organizacion;
        this.items = items;
        this.cantidadPresupuestosRequeridos = cantidadPresupuestosRequeridos;
        this.moneda=moneda;
    }

    public OperacionDeEgreso(Integer numeroOp, Double valorTotal, EntidadJuridica organizacion, List<Item> items, int cantidadPresupuestosRequeridos) {
        this.numeroOp = numeroOp;
        this.fecha = LocalDate.now();
        ValorTotal = valorTotal;
        this.organizacion = organizacion;
        this.items = items;
        this.cantidadPresupuestosRequeridos = cantidadPresupuestosRequeridos;
    }

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public int getCantidadPresupuestosRequeridos() {
        return cantidadPresupuestosRequeridos;
    }

    public List<Usuario> getRevisores() {
        return revisores.stream().filter(revisor->revisor.isActivo()).collect(Collectors.toList());
    }

    public CriterioDeSeleccionPresupuesto getCriterioSelecc() {
        return criterioSelecc;
    }

    public Integer getNumeroOp() {
        return numeroOp;
    }

    public void setNumeroOp(Integer numeroOp) {
        this.numeroOp = numeroOp;
    }



    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        ValorTotal = valorTotal;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public DocumentoComercial getDocComercial() {
        return docComercial;
    }

    public void setDocComercial(DocumentoComercial docComercial) {
        this.docComercial = docComercial;
    }

    public EntidadJuridica getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(EntidadJuridica organizacion) {
        this.organizacion = organizacion;
    }

    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public void setCantidadPresupuestosRequeridos(int cantidadPresupuestosRequeridos) {
        this.cantidadPresupuestosRequeridos = cantidadPresupuestosRequeridos;
    }

    public void setRevisores(List<Usuario> revisores) {
        this.revisores = revisores;
    }

    public void setCriterioSelecc(CriterioDeSeleccionPresupuesto criterioSelecc) {
        this.criterioSelecc = criterioSelecc;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.ValorTotal= items.stream().mapToDouble(i->i.valorTotal()).sum();
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public boolean noPoseeIngresoAsociado(){
        return this.ingreso==null;
    }

    public boolean isValidada() {
        return validada;
    }

    public void setValidada(boolean validada) {
        this.validada = validada;
    }

    public void agregarItem(Item item){
        items.add(item);
        this.ValorTotal+=item.valorTotal();
    }
}
