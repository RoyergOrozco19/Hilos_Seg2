package Hospital;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Medico extends Thread {
  private String nombre;
  private AtomicInteger pacientesAtendidos;
  private Random random;

  public Medico(String nombre) {
    this.nombre = nombre;
    this.pacientesAtendidos = new AtomicInteger(0);
    this.random = new Random();
  }

  @Override
  public void run() {
    while (true) {  // Simulando que los médicos atienden de forma continua
      try {

        int tiempoAtencion = random.nextInt(5) + 1;
        System.out.println(nombre + " está atendiendo a un paciente durante " + tiempoAtencion + " segundos.");
        Thread.sleep(tiempoAtencion * 1000);

        pacientesAtendidos.incrementAndGet();
        System.out.println(nombre + " ha terminado de atender a un paciente.");
      } catch (InterruptedException e) {
        System.out.println(nombre + " ha sido interrumpido.");
        break;
      }
    }
  }

  public int getPacientesAtendidos() {
    return pacientesAtendidos.get();
  }
}

class Hospital {
  public static void main(String[] args) {

    Medico[] medicos = {
        new Medico("Médico Andrés"),
        new Medico("Dra. Fernandez "),
        new Medico("Especialista Steven 3")
    };

    // Iniciar los hilos de los médicos
    for (Medico medico : medicos) {
      medico.start();
    }

    // Ejecutar la simulación por un tiempo determinado (e.g., 20 segundos)
    try {
      Thread.sleep(20000);  // Simular 20 segundos de operación
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


    for (Medico medico : medicos) {
      medico.interrupt();
    }

    for (Medico medico : medicos) {
      try {
        medico.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.println("\nCantidad de pacientes atendidos por cada médico:");
    for (Medico medico : medicos) {
      System.out.println(medico.getName() + ": " + medico.getPacientesAtendidos() + " pacientes");
    }
  }
}


