package Entity.Minimercado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Supermercado {
  public static void main(String[] args) {
    int numCajas = 3;
    int numClientes = 10;
    CajaRegistradora[] cajas = new CajaRegistradora[numCajas];

    for (int i = 0; i < numCajas; i++) {
      cajas[i] = new CajaRegistradora(i + 1);
    }

    List<Thread> threads = new ArrayList<>();
    Random random = new Random();

    for (int i = 0; i < numClientes; i++) {
      CajaRegistradora caja = cajas[random.nextInt(numCajas)];
      Cliente cliente = new Cliente(i + 1, caja);
      Thread hiloCliente = new Thread(cliente);
      threads.add(hiloCliente);
      hiloCliente.start();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    int totalVentasSupermercado = 0;
    for (CajaRegistradora caja : cajas) {
      System.out.println("Caja " + caja.getId() + " atendió a " + caja.getClientesAtendidos() + " clientes con ventas de $" + caja.getTotalVentas());
      totalVentasSupermercado += caja.getTotalVentas();
    }
    System.out.println("Total de ventas en el supermercado: $" + totalVentasSupermercado);
  }
}

