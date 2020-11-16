package domain.Egresos;

import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.monedas.Moneda;
import repositorios.Persistente;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ingreso")
public class Ingreso extends Persistente {

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="valor_total")
    private Double valorTotal;

    @Column(name="valor_restante")
    private Double valorRestante;

    @Column(name="fecha")
    //@Convert(converter = LocalDateConverter.class)
    private LocalDate fecha;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_entidad_juridica", referencedColumnName = "id")
    private EntidadJuridica organizacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_moneda", referencedColumnName = "id")
    private Moneda moneda;

    @OneToMany(mappedBy = "ingreso", cascade = {CascadeType.ALL})
    private List<OperacionDeEgreso> egresos = new ArrayList<>();

    @Column(name="fecha_aceptabilidad")
    private LocalDate fechaDeAceptabilidadEgreso;

    public Ingreso(Double unValor, EntidadJuridica unaOrganizacion, String unaDescripcion, LocalDate fechaDeAceptabilidad, LocalDate fecha, Moneda moneda){
        if(fechaDeAceptabilidad != null){
            this.fechaDeAceptabilidadEgreso = fechaDeAceptabilidad;
        } else {
            this.fechaDeAceptabilidadEgreso = LocalDate.now();
        }
        this.descripcion = unaDescripcion;
        this.valorTotal = unValor;
        this.valorRestante = unValor;
        this.organizacion = unaOrganizacion;
        this.fecha = fecha;
        this.moneda = moneda;
    }

    public Ingreso(Double unValor, EntidadJuridica unaOrganizacion, String unaDescripcion, LocalDate fechaDeAceptabilidad){
        if(fechaDeAceptabilidad != null){
            this.fechaDeAceptabilidadEgreso = fechaDeAceptabilidad;
        } else {
            this.fechaDeAceptabilidadEgreso = LocalDate.now();
        }
        this.descripcion = unaDescripcion;
        this.valorTotal = unValor;
        this.valorRestante = unValor;
        this.organizacion = unaOrganizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
        this.valorRestante = valorTotal;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EntidadJuridica getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(EntidadJuridica organizacion) {
        this.organizacion = organizacion;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public List<OperacionDeEgreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<OperacionDeEgreso> egresos) {
        this.egresos = egresos;
    }

    public void setFechaDeAceptabilidadEgreso(LocalDate fechaDeAceptabilidadEgreso) {
        this.fechaDeAceptabilidadEgreso = fechaDeAceptabilidadEgreso;
    }

    public Double getValor(){
        return this.valorTotal;
    }

    public LocalDate getFecha(){
        return this.fecha;
    }

    public LocalDate getFechaDeAceptabilidadEgreso(){
        return this.fechaDeAceptabilidadEgreso;
    }

    public Ingreso(){

    }
    public boolean puedoAsociarmeAEgresos(List<OperacionDeEgreso> egresos){
        return (this.valorTotal-this.totalEgresos())>= (egresos.stream().mapToDouble(e->e.getValorTotal()).sum())
                && egresos.stream().allMatch(e->e.noPoseeIngresoAsociado() && this.egresoFechaAceptable(e));
    }
    public Double totalEgresos(){
        return this.egresos.stream().mapToDouble(e->e.getValorTotal()).sum();
    }
    public boolean puedoAsociarmeAEgreso(OperacionDeEgreso egreso){
        return (this.valorTotal-this.totalEgresos())>= egreso.getValorTotal() && egreso.noPoseeIngresoAsociado() && this.egresoFechaAceptable(egreso);
    }
    public void asociarEgreso(OperacionDeEgreso egreso) throws Exception{
        if(!puedoAsociarmeAEgreso(egreso))
            throw new Exception("No se puede realizar la vinculación ingreso - egreso");
        else{
            if(valorRestante==null){
                valorRestante=valorTotal;
            }
            valorRestante-=egreso.getValorTotal();
            this.egresos.add(egreso);
            egreso.setIngreso(this);
        }

    }

    public boolean egresoFechaAceptable(OperacionDeEgreso egreso){
        return this.getFecha().isBefore(egreso.getFecha()) && this.getFechaDeAceptabilidadEgreso().isAfter(egreso.getFecha());
    }

    public void agregarEgreso(OperacionDeEgreso egreso){
        this.egresos.add(egreso);
    }

    public void asociarEgresos(List<OperacionDeEgreso> egresos) throws Exception {
        if(!puedoAsociarmeAEgresos(egresos))
            throw  new Exception("No se puede realizar la vinculación ingreso - egresos");
        else
            egresos.stream().forEach(e-> {
                try {
                    this.asociarEgreso(e);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
    }

    public boolean disponibleParaAsociacion(){
        return valorRestante>0;
    }
    public Double getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(Double valorRestante) {
        this.valorRestante = valorRestante;
    }
}