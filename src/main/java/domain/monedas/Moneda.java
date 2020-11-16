package domain.monedas;

import repositorios.Persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="moneda")
public class Moneda extends Persistente {

    @Column(name="id_moneda")
    private String idMoneda;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="simbolo")
    private String simbolo;

    @Column(name="lugares_decimales")
    private int lugaresDecimales;

    public Moneda(String id, String descripcion, String simbolo, int lugaresDecimales) {
        this.idMoneda = id;
        this.descripcion = descripcion;
        this.simbolo = simbolo;
        this.lugaresDecimales = lugaresDecimales;
    }

    public Moneda(){}

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getLugaresDecimales() {
        return lugaresDecimales;
    }

    public void setLugaresDecimales(int lugaresDecimales) {
        this.lugaresDecimales = lugaresDecimales;
    }
}
