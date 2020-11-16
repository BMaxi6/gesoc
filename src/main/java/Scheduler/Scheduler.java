package Scheduler;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

public class Scheduler {
    Timer timer;
    TimerTask tarea;
    Date fecha;
    private int hora;
    private int minuto;


    public void planificar(Periodo periodo,  List<Dia> diasDeEjecucion,Planificable planificable, Date fecha){
        timer= new Timer();
        tarea= new TimerTask(){
          @Override
          public void run(){

              int hora=fecha.getHours();
              int minuto=fecha.getMinutes();

              Date actual= new Date();

              if(diasDeEjecucion.contains(Dia.convertirADia(LocalDateTime.now().getDayOfWeek())) && actual.getHours()==hora &&actual.getMinutes()==minuto){
                  planificable.ejecutarAccionPeriodica();
              }
          }


        };

        System.out.println(tarea);
        System.out.println(fecha);
        System.out.println(periodo);

        timer.schedule(tarea,fecha, periodo.intervalo());


    }

    public void replanificar(Periodo periodo,  List<Dia> diasDeEjecucion,Planificable planificable, Date fecha){
        if(this.tarea!=null)
            this.tarea.cancel();
        this.planificar(periodo,diasDeEjecucion,planificable, fecha);

    }

    public Scheduler(Periodo periodo,  List<Dia> diasDeEjecucion,Planificable planificable, Date fecha){
        this.planificar(periodo,  diasDeEjecucion,planificable,fecha);
    }

    public Scheduler(){

    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
}

