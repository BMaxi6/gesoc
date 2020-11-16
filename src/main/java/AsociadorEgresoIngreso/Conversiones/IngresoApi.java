package AsociadorEgresoIngreso.Conversiones;

public class IngresoApi {
    public int id;
    public Double valor;
    public String fecha;
    public String fechaDeAceptacion;
    public Double valorRestante;

    public IngresoApi(int id, Double valor, String fecha, String fechaAceptacion, Double valorRestante) {
        this.id = id;
        this.valor = valor;
        this.fecha = fecha;
        this.fechaDeAceptacion = fechaAceptacion;
        this.valorRestante = valorRestante;
    }
}