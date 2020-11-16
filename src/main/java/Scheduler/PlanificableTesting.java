package Scheduler;

public class PlanificableTesting implements Planificable {

    @Override
    public void ejecutarAccionPeriodica() {
        System.out.println("----------------EJECUTO------------------");
    }

    public PlanificableTesting() {
    }
}
