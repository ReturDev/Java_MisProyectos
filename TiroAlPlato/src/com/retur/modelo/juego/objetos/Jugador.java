package com.retur.modelo.juego.objetos;



/**
 * Clase que representa al jugador.
 * @author Sergio
 *
 */
public class Jugador{

	private static final int VIDAS_MAXIMAS = 5;
	
	public final Mirilla MIRILLA;

	private double vidas;
	private int puntos;
	
	/**
	 * Crea una instancia de Jugador almacenando valores en sus atributos.
	 * @param anchoVentana Recibe el ancho de la ventana.
	 * @param altoVentana Recibe el alto de la ventana.
	 */
	public Jugador(double anchoVentana, double altoVentana) {
		
		MIRILLA = new Mirilla(anchoVentana, altoVentana);
		vidas = VIDAS_MAXIMAS;
	
	}
	
	/**
	 * Aumenta las vidas del jugador.
	 * @param vidasObtenidas Número de vidas obtenidas
	 */
	public void aumentarVidas(double vidasObtenidas) {
		
		if(vidas < VIDAS_MAXIMAS ) {
			
			vidas = (vidas + vidasObtenidas) > VIDAS_MAXIMAS ? VIDAS_MAXIMAS : vidas + vidasObtenidas;
			
		}
		
	}
	
	
	/**
	 * Reduce las vidas del jugador.
	 * @param vidasPerdidas Número de vidas perdidas.
	 */
	public void reducirVidas(double vidasPerdidas) {
		
		if(vidas > 0) {
			
			vidas = (vidas - vidasPerdidas) <= 0 ? 0 : vidas - vidasPerdidas;
			
		}
		
	}
	
	/**
	 * Aumenta los puntos del jugador.
	 * @param puntos Cantidad de puntos que aumentar.
	 */
	public void aumentarPuntos(int puntos) {
		
		this.puntos += puntos;
		
	}
	

	public double getVidas() {
		return vidas;
	}
	
	
	public void setVidas(double vidas) {
		this.vidas = vidas;
	}
	
	
	public int getPuntos() {
		return puntos;
	}
	
	
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	
	
}
