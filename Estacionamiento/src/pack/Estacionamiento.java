package pack;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Estacionamiento {
private final int capacidadMaxima=5;
private final Semaphore semaforo =new Semaphore(5);
private final ArrayList<Coche> cochesAparcados= new ArrayList<Coche>();
private int cochesAparcadosTotal = 0;


boolean entrar(Coche coche) throws InterruptedException {

	 boolean aparcado = false;  
	    if (cochesAparcados.size() >= capacidadMaxima) {
	        if (!coche.esVip()) {
	            if (semaforo.tryAcquire(5, TimeUnit.SECONDS)) {
	                cochesAparcados.add(coche);
	                aparcado = true;
	            }
	        } else {
	            semaforo.acquire();
	            desalojarCocheNormal(coche);
	            aparcado = true;
	        }
	    } else {
	        semaforo.acquire();
	        cochesAparcados.add(coche);
	        aparcado = true;
	    }	   
	    if (aparcado)
	        cochesAparcadosTotal++;
	    return aparcado;
	}

synchronized void salir(Coche coche) {
    cochesAparcados.remove(coche);
    semaforo.release(); 
}

void desalojarCocheNormal(Coche cocheVip) {
	for (int i = 0; i < cochesAparcados.size(); i++) {
        if (!cochesAparcados.get(i).esVip()) {                
            cochesAparcados.remove(i);   
            cochesAparcados.add(i, cocheVip); 
            return;                  
        }
    }
}

int getCochesAparcadosTotal(){
	return cochesAparcadosTotal;
}

public synchronized void Estado() {
    System.out.println("Estacionamiento actual (" + cochesAparcados.size() + "/" + capacidadMaxima + "):");
    for (Coche c : cochesAparcados) {
      System.out.println(c);
    }
    System.out.println("---------------------------");
}
}
