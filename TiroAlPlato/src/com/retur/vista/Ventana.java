package com.retur.vista;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Clase base de todas las ventanas del juego.
 * @author Sergio
 *
 */
public abstract class Ventana {

	public final double ANCHO_VENTANA;
	public final double ALTO_VENTANA;
	protected final AnchorPane RAIZ;
	public final Scene ESCENA;
	
	/**
	 * Crea una instancia de Ventana
	 * @param anchoVentana 
	 * @param altoVentana
	 */
	public Ventana(double anchoVentana, double altoVentana) {
		
		ANCHO_VENTANA = anchoVentana;
		ALTO_VENTANA = altoVentana;
		RAIZ = new AnchorPane();
		ESCENA = new Scene(RAIZ, anchoVentana, altoVentana);
		
	}
	
}
