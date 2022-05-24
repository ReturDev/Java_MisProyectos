package com.retur.modelo.juego.objetos;

import java.io.File;

import com.retur.vista.VentanaJuego;

public class Plato extends Volador {
	
	private static final String URL_DISCO = "./src/resources/plato.png";
	private static final int DIMENSION_IMAGEN = 30;
	private static final double DANYO = 0.25;
	private static final int PUNTOS = 50;

	
	/**
	 * Instancia un Plato.
	 * @param jugador Recibe el jugador para gestionar los puntos y su vida.
	 * @param vj Recibe la Ventana de Jugador para acceder a sus medidas y al Canvas.
	 */
	public Plato(Jugador jugador, VentanaJuego vj) {
		
		super(DIMENSION_IMAGEN, new File(URL_DISCO), DANYO, PUNTOS, jugador, vj);
		
	}

	
}
