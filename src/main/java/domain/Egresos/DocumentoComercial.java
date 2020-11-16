package domain.Egresos;

import domain.EntidadesOrganizacionales.CategoriaEmpresa.Sector;
import domain.EntidadesOrganizacionales.EntidadJuridica;
import domain.Personas.Proveedor;
import repositorios.Persistente;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="documento_comercial")
public class DocumentoComercial extends Persistente {

    @Column(name="numero_documento")
    private String numeroDocumento;

    @Column(name="tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoDocumentoComercial tipo;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_operacion_egreso", referencedColumnName = "id")
    private OperacionDeEgreso operacion;

    @Column(name="fecha")
    //@Convert(converter = LocalDate.class)
    private LocalDate fecha;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_proveedor", referencedColumnName = "id")
    private Proveedor emisor;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_entidad_juridica_receptora", referencedColumnName = "id")
    private EntidadJuridica receptor;

    @Column(name="path")
    private String path;

    @Column(name="ubicacion")
    private String ubicacion;

    public DocumentoComercial(String numeroDocumento, TipoDocumentoComercial tipo, OperacionDeEgreso operacion, String path) {
        this.numeroDocumento = numeroDocumento;
        this.tipo = tipo;
        this.operacion = operacion;
        this.fecha = operacion.getFecha();
        this.emisor = operacion.getProveedor();
        this.receptor = operacion.getOrganizacion();
        this.path = path;
    }

    public DocumentoComercial(){

    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public TipoDocumentoComercial getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumentoComercial tipo) {
        this.tipo = tipo;
    }

    public OperacionDeEgreso getOperacion() {
        return operacion;
    }

    public void setOperacion(OperacionDeEgreso operacion) {
        this.operacion = operacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Proveedor getEmisor() {
        return emisor;
    }

    public void setEmisor(Proveedor emisor) {
        this.emisor = emisor;
    }

    public EntidadJuridica getReceptor() {
        return receptor;
    }

    public void setReceptor(EntidadJuridica receptor) {
        this.receptor = receptor;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}