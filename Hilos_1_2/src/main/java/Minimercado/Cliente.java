package Entity.Minimercado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

  class Cliente implements Runnable {
    private int id;
    private List<String> cesta;
    private int tiempoProcesamiento;
    private CajaRegistradora caja;

    public Cliente(int id, CajaRegistradora caja) {
      this.id = id;
      this.caja = caja;
      this.cesta = generarCestaAleatoria();
      this.tiempoProcesamiento = new Random().nextInt(2000) + 1000; // Entre 1 y 3 segundos
    }

    private List<String> generarCestaAleatoria() {
      String[] articulos = {"Pan", "Leche", "Huevos", "Carne", "Pasta", "Frutas", "Verduras"};
      List<String> cesta = new ArrayList<>();
      Random random = new Random();
      int numArticulos = random.nextInt(5) + 1; // Entre 1 y 5 artículos

      for (int i = 0; i < numArticulos; i++) {
        cesta.add(articulos[random.nextInt(articulos.length)]);
      }
      return cesta;
    }

    @Override
    public void run() {
      try {
        System.out.println("Minimercado.Cliente " + id + " atendido en caja " + caja.getId() + " con artículos: " + cesta);
        Thread.sleep(tiempoProcesamiento);
        caja.procesarVenta(cesta.size() * 10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  class CajaRegistradora {
    private int id;
    private int clientesAtendidos;
    private int totalVentas;

    public CajaRegistradora(int id) {
      this.id = id;
      this.clientesAtendidos = 0;
      this.totalVentas = 0;
    }

    public int getId() {
      return id;
    }

    public synchronized void procesarVenta(int monto) {
      clientesAtendidos++;
      totalVentas += monto;
    }

    public int getClientesAtendidos() {
      return clientesAtendidos;
    }

    public int getTotalVentas() {
      return totalVentas;
    }
  }

