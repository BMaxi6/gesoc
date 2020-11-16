package domain.ubicaciones;

import repositorios.Persistente;

import javax.persistence.*;

@Entity
@Table(name="direccion")
public class Direccion extends Persistente {

    @Column(name="codigo_postal")
    private int codigoPostal;

    @Column(name="calle")
    private String calle;

    @Column(name="altura")
    private int altura;

    @Column(name="piso")
    private int piso;

    @Column(name="nombre_ciudad")
    private String nombreCiudad;

    @Column(name="nombre_provincia")
    private String nombreProvincia;

    @Column(name="nombre_pais")
    private String nombrePais;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_ciudad", referencedColumnName = "id")
    private Ciudad ciudad;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_provincia", referencedColumnName = "id")
    private Provincia provincia;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_pais", referencedColumnName = "id")
    private Pais pais;

    public Direccion(){ }

    public Direccion(int codigoPostal, String calle, int altura, int piso, Ciudad ciudad, Provincia provincia, Pais pais){
        this.codigoPostal=codigoPostal;
        this.calle=calle;
        this.altura=altura;
        this.piso=piso;
        this.ciudad=ciudad;
        this.provincia=provincia;
        this.pais=pais;
        this.nombreCiudad=ciudad.getNombre();
        this.nombreProvincia= provincia.getNombre();
        this.nombrePais= pais.getNombre();
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        this.nombreCiudad=ciudad.getNombre();
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
        this.nombreProvincia=provincia.getNombre();
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
        this.nombrePais=pais.getNombre();
    }
}
