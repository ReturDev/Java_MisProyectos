package com.retur.modelo.juego.objetos;

import java.io.File;

import com.retur.vista.VentanaJuego;

/**
 * Clase que representa a un Pájaro
 * @author Sergio
 *
 */
public class Pajaro extends Volador {

	private static final String URL_DISCO = "./src/resources/pajaro.png";
	private static final int DIMENSION_IMAGEN = 30;
	private static final double DANYO = 1;
	private static final int PUNTOS = 25;
	
	/**
	 * Instancia un Pajaro.
	 * @param jugador Recibe el jugador para gestionar los puntos y su vida.
	 * @param vj Recibe la Ventana de Jugador para acceder a sus medidas y al Canvas.
	 */
	public Pajaro(Jugador jugador, VentanaJuego vj) {
		
		super(DIMENSION_IMAGEN, new File(URL_DISCO), DANYO, PUNTOS, jugador, vj);
	
	}
	
}
