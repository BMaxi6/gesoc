package domain.EntidadesOrganizacionales;

import domain.EntidadesOrganizacionales.CategoriaEmpresa.Rubro;
import domain.Personas.Usuario;
import domain.ubicaciones.Direccion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("osc")
public class Osc extends EntidadJuridica {

    @Transient
    private String descriptor = "OSC";

    @ElementCollection
    private List<String> voluntarios = new ArrayList<String>();

    @ElementCollection
    private List<String> financiadores = new ArrayList<String>();

    public Osc(String razonSocial, String nombreFicticio, Direccion direccion, String codigoInscripcionIGJ, ArrayList<Usuario> usuarios, Integer cantidadPers, Rubro actividad, List<String> voluntarios, List<String> financiadores) {
        super(razonSocial, nombreFicticio, direccion, codigoInscripcionIGJ, usuarios, cantidadPers, actividad);
        this.voluntarios = voluntarios;
        this.financiadores = financiadores;
    }

    public Osc(List<String> personal, List<String> financiadores){
        this.voluntarios = personal;
        this.financiadores = financiadores;
    }

    public Osc(){

    }
    @Override
    public String getDescriptor() {
        return descriptor;
    }

    public List<String> getVoluntarios() {
        return voluntarios;
    }

    public List<String> getFinanciadores() {
        return financiadores;
    }
}
