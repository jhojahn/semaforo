package pack;

public class Coche extends Thread {
	private String nombre;
	private boolean vip;
	private Estacionamiento estacionamiento;

	public Coche(String nombre, boolean vip, Estacionamiento estacionamiento) {
		this.nombre = nombre;
		this.vip = vip;
		this.estacionamiento = estacionamiento;
	}

	boolean esVip() {
		if (this.vip == true)
			return true;
		return false;
	}

	public void run() {
		try {
			estacionamiento.entrar(this);
			Thread.sleep((int) (Math.random() * (6000 - 2000)) + 2000);
			estacionamiento.salir(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return nombre + (vip ? " (VIP)" : "");
	}
}
