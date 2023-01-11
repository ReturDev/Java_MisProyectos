package com.retur.modelo.juego.objetos;

import java.io.File;

import com.retur.modelo.juego.interfaces.Pintable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Clase que crea el fondo del juego.
 * @author Sergio
 *
 */
public class Fondo implements Pintable{

	private final String DIR_IMAGEN;
	private final Image IMAGEN;
	
	
	/**
	 * Crea una instancia de Fondo
	 * @param anchoVentana Recibe el ancho de la ventana.
	 * @param altoVentana Recibe el alto de la ventana.
	 */
	public Fondo(double anchoVentana, double altoVentana) {
		
		this.DIR_IMAGEN = "./src/resources/cielo.jpg";
		this.IMAGEN = new Image(new File(DIR_IMAGEN).toURI().toString(),
				anchoVentana,
				altoVentana,
				false,
				false);
		
	}
	
	@Override
	public void pintar(GraphicsContext gc) {
		
		gc.drawImage(IMAGEN, 0, 0);
		
	}


	
}
