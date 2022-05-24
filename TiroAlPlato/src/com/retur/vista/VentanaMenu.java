package com.retur.vista;

import java.io.File;

import javafx.scene.control.Button;

/**
 * Clase base de las ventanas que actuen como algún tipo de menú que cuente con un botón.
 * @author Sergio
 *
 */
public abstract class VentanaMenu extends Ventana{
	
	
	protected static final int DIMENSION_FUENTE_BOTON = 30;
	protected static final int DIMENSION_FUENTE_TITULO = 50;
	
	public final Button BOTON;

	/**
	 * Crea una instancia de la ventana de menu.
	 * @param anchoVentana
	 * @param altoVentana
	 */
	public VentanaMenu(double anchoVentana, double altoVentana) {
		super(anchoVentana, altoVentana);
		
		ESCENA.getStylesheets().add(new File("src/resources/application.css").toURI().toString());
		BOTON = crearBoton();
		RAIZ.getChildren().add(BOTON);
		
	}

	/**
	 * Crea un botón personalizado.
	 * @return
	 */
	protected abstract Button crearBoton();
	/**
	 * Crea un título personalizado y lo añade al padre.
	 */
	protected abstract void crearTitulo();
}
