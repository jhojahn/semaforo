package pack;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		int i;
		Estacionamiento estacionamiento = new Estacionamiento();
		ArrayList<Coche> lista = new ArrayList<Coche>();
		for (i = 1; i <= 15; i++) {
			if (i % 3 == 0)
				lista.add(new Coche("Coche-" + i, true, estacionamiento));
			else
				lista.add(new Coche("Coche-" + i, false, estacionamiento));
		}

		Thread estado = new Thread(() -> {
			try {
				while (true) {
					estacionamiento.Estado();
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {

			}
		});
		estado.start();

		for (Coche c : lista) {
			c.start();
		}
		for (Coche c : lista) {
			c.join();
		}
//a
		estado.interrupt();

		System.out.println("los coches han intentado aparcar");
		System.out.println("Coches que aparcaron: " + estacionamiento.getCochesAparcadosTotal());
		System.out.println("Coches que no aparcaron: " + (lista.size() - estacionamiento.getCochesAparcadosTotal()));

	}

}
