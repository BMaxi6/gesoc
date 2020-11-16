package AsociadorEgresoIngreso;

import AsociadorEgresoIngreso.Conversiones.EgresosAsociados;
import Scheduler.Planificable;
import domain.Egresos.Ingreso;
import domain.Egresos.OperacionDeEgreso;
import repositorios.DaoHibernate;
import repositorios.FactoryRepositorio;
import repositorios.Repositorio;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Asociador implements Planificable {

    int idCriterio = 1;
    Repositorio<OperacionDeEgreso> repoEgresos = FactoryRepositorio.instancia().obtenerRepositorio(OperacionDeEgreso.class);
    Repositorio<Ingreso> repoIngreso = FactoryRepositorio.instancia().obtenerRepositorio(Ingreso.class);
    int idOrganizacion;

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public void setIdCriterio(int id){
        this.idCriterio = id;
    }

    public boolean ejecucionManual(int id){
        try {
            System.out.println("ENTRE A EJECUTAR MANUAL VINCULADOR");
            List<OperacionDeEgreso> egresos;
            List<OperacionDeEgreso> egresosFiltados = new ArrayList<OperacionDeEgreso>();
            egresos = repoEgresos.buscarTodos();
            egresos.forEach(egreso -> {
                if(egreso.getOrganizacion().getId() == idOrganizacion && egreso.getIngreso() == null){

                    egresosFiltados.add(egreso);
                    System.out.println("----------agregue egreso" + egreso.getNumeroOp());
                }
            });
            ActionProvider.obtenerGuardarEgresos().invocar(egresosFiltados);

            List<Ingreso> ingresos;
            List<Ingreso> ingresosFiltrados = new ArrayList<Ingreso>();
            ingresos = repoIngreso.buscarTodos();
            ingresos.forEach(ingreso -> {
                if (ingreso.getOrganizacion().getId() == idOrganizacion && ingreso.disponibleParaAsociacion() && ingreso.getFechaDeAceptabilidadEgreso().isBefore(LocalDate.now())) {
                    ingresosFiltrados.add(ingreso);
                    System.out.println("----------agregue ingreso" + ingreso.getId());
                }
            });
            ActionProvider.obtenerGuardarIngresos().invocar(ingresosFiltrados);

            List<EgresosAsociados> egresosAsociados = ActionProvider.asociarEgresosAIngresos().invocar(id);
            System.out.println("-------");
            if(egresosAsociados.size() > 0) {
                egresosAsociados.forEach(egresosAsociados1 -> {
                    System.out.println("Egreso: " + egresosAsociados1.getIdEgreso());
                    System.out.println("Ingreso: " + egresosAsociados1.getIdIngreso());
                    if(egresosAsociados1.getIdIngreso() > 0 && egresosAsociados1.getIdEgreso() > 0){
                        OperacionDeEgreso egreso = repoEgresos.buscar(egresosAsociados1.getIdEgreso());
                        Ingreso ingreso = repoIngreso.buscar(egresosAsociados1.getIdIngreso());
                        egreso.setIngreso(ingreso);
                        ingreso.agregarEgreso(egreso);
                        repoIngreso.modificar(ingreso);
                        repoEgresos.modificar(egreso);
                    }
                });
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void ejecutar(){
        ejecucionManual(idCriterio);
    }

    @Override
    public void ejecutarAccionPeriodica() {
        this.ejecutar();
    }
}
