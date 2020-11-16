package DTOs;

import domain.Egresos.OperacionDeEgreso;
import domain.Personas.Usuario;

import java.time.LocalDate;

public class RevisorDto {
    String nombre;
    String apellido;

    public RevisorDto(Usuario usuario){
        this.nombre = usuario.getNombreReal();
        this.apellido = usuario.getApellido();
    }

    public RevisorDto(){}

    public static RevisorDto toRevisorDto(Usuario usuario){
        return new RevisorDto(usuario);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
