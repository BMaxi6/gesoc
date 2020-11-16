package AsociadorEgresoIngreso.Conversiones;

public class EgresoApi {
    public Integer numeroOp;
    public int id;
    public Double valor;
    public String fecha;

    public EgresoApi(Integer numeroOp, int id, Double valor, String fecha) {
        this.id = id;
        this.numeroOp = numeroOp;
        this.valor = valor;
        this.fecha = fecha;
    }

    public Integer getNumeroOp(){
        return numeroOp;
    }
}
