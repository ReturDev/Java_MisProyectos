package com.retur.modelo.juego.objetos;



import javafx.scene.shape.Rectangle;


/**
 * Representa la bala disparada.
 * @author Sergio
 *
 */
public class Bala extends Thread{
	
	
	private Rectangle rangoColision;
	
	private final int TIEMPO_VIDA_MILIS = 20;

	/**
	 * Instancia una bala colocando la colisión e iniciando el hilo
	 *  que se encargará de mantener la bala viva 20 milisegundos.
	 * @param x Posición x donde estará la bala
	 * @param y Posición y donde estará la bala
	 * @param ancho Ancho de la bala
	 * @param alto Alto de la bala
	 */
	public Bala(double x, double y, double ancho, double alto) {
		
		this.rangoColision = new Rectangle(x, y, ancho, alto);
		this.start();
		
	}

	
	
	@Override
	public void run() {
		/*
		 * Mantiene el rango de colisión de la bala los milisegundos establecidos y luego establece
		 * el rango de colisión a nulo para que desaparezca el disparo.
		 */
		try {
			sleep(TIEMPO_VIDA_MILIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		rangoColision = null;
		
	}
	

	public Rectangle getRangoColision() {
		return rangoColision;
	}
	

	
}
