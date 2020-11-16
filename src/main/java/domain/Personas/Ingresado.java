package domain.Personas;

import repositorios.Persistente;
import repositorios.Repositorio;
import validadorContrasenias.EncriptadorDeContrasenias;
import validadorContrasenias.GeneradorDeContrasenias;
import validadorContrasenias.ValidadorDeContrasenias;

import javax.persistence.*;

@Entity
@Table(name="ingresado")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_ingresado")
public abstract class Ingresado extends Persistente {

    @Column(name="nombre_real")
    protected String nombreReal;
    @Column(name="apellido")
    protected String apellido;

    @Column(name="nombre")
    protected String nombre;

    @Column(name="contrasenia")
    protected String contrasenia;

    @Column(name="activo")
    protected boolean activo=true;

    public Ingresado(String nombre) {

        this.nombre = nombre;
        GeneradorDeContrasenias generador = GeneradorDeContrasenias.instancia();
        this.contrasenia = EncriptadorDeContrasenias.instancia().encriptarContrasenia(generador.generarContraseniaAleatoria());
    }

    public Ingresado(){
        GeneradorDeContrasenias generador = GeneradorDeContrasenias.instancia();
        this.contrasenia = EncriptadorDeContrasenias.instancia().encriptarContrasenia(generador.generarContraseniaAleatoria());
    }


    public int registrarContrasenia(String contrasenia, Repositorio<String> repositorio){
        if(ValidadorDeContrasenias.instancia().validarContrasenia(contrasenia)){
            this.contrasenia=EncriptadorDeContrasenias.instancia().encriptarContrasenia(contrasenia);;
            repositorio.modificar(this);
            return 0;
        }else{
            return -1;
        }
    }


    public boolean contraseniaCorrecta(String contrasenia){
        return EncriptadorDeContrasenias.instancia().contraseniaCoincide(contrasenia,this.contrasenia);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = EncriptadorDeContrasenias.instancia().encriptarContrasenia(contrasenia);
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

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
