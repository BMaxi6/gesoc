package AsociadorEgresoIngreso.Conversiones;

public class EgresosAsociados {
    int idIngreso;
    Integer idEgresos;


    public EgresosAsociados(Integer idEgreso, int idIngreso) {
        this.idEgresos = idEgreso;
        this.idIngreso = idIngreso;
    }

    public Integer getIdEgreso() {
        return idEgresos;
    }

    public void setIdEgreso(Integer idEgreso) {
        this.idEgresos = idEgreso;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }
}

