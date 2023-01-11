package com.retur.modelo.juego.objetos;

import java.io.File;

import com.retur.modelo.juego.interfaces.Pintable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;


/**
 * Es la Mira con la que el jugador se moverá por el juego.
 * @author Sergio
 *
 */
public class Mirilla implements Pintable {

	private static final int DIMENSION_IMAGEN = 30;
	private static final int TIEMPO_RECARGA_MILIS = 100;
	private static final double MITAD_DIMENSION_MIRILLA = DIMENSION_IMAGEN / 2.0;
	private static final double CUARTAPARTE_DIMENSION_MIRILLA = DIMENSION_IMAGEN / 4.0;
	private static final Image CURSOR_JUGADOR = new Image(new File("./src/resources/mirilla.png").toURI().toString(),
			DIMENSION_IMAGEN,
			DIMENSION_IMAGEN,
			false,
			true);
	private static final AudioClip AUDIO_DISPARO = new AudioClip(new File("./src/resources/sounds/disparo.mp3").toURI().toString());
	
	private final double ANCHO_VENTANA;
	private final double ALTO_VENTANA;
	
	
	private Bala bala;
	private boolean disparoDisponible;
	private long ultimoDisparo;
	private double x;
	private double y;
	
	
	/**
	 * Crea una instancia de la Mirilla
	 * @param anchoVentana Recibe el ancho de la ventana.
	 * @param altoVentana Recibe el alto de la ventana.
	 */
	public Mirilla(double anchoVentana, double altoVentana) {
		
		ANCHO_VENTANA = anchoVentana;
		ALTO_VENTANA = altoVentana;
		ultimoDisparo = System.currentTimeMillis();
		
	}
	
	/**
	 * Método encargado mover la posición de la mirilla. Para que el centro de la mirilla coincida
	 * exactamente con la posición del cursor, se le restará la mitad del ancho de la mirilla a X
	 * y el alto a Y. 
	 * @param x posición en eje x
	 * @param y posición en eje y
	 */
	public void mover(double x, double y) {
		
		//Comprueba que la posición de la mirilla no se encuentre en los limites de la ventana.
		if((x - MITAD_DIMENSION_MIRILLA) >= 0 && (x + MITAD_DIMENSION_MIRILLA) <= ANCHO_VENTANA) {
			
			this.x = x - MITAD_DIMENSION_MIRILLA;
			
		}
		
		if((y - MITAD_DIMENSION_MIRILLA) >= 0 && (y + MITAD_DIMENSION_MIRILLA) <= ALTO_VENTANA) {
			
			this.y = y - MITAD_DIMENSION_MIRILLA;
			
		}
		
	}
	
	/**
	 * Recarga el disparo permitiendo poder volver a disparar despues del tiempo de recarga.
	 */
	public void recargaDisparo() {
		
		long tiempoActual = System.currentTimeMillis();
		
		if(tiempoActual - ultimoDisparo >= TIEMPO_RECARGA_MILIS) {
			disparoDisponible = true;
		}
		
	}
	
	public void disparar() {
		
	
		if(disparoDisponible) {
			
			/*
			 * Para que la bala vaya justo al centro se ajusta la posición de X sumandole una cuarta
			 * parte de las dimensiones de la mirilla, y lo mismo con la Y.
			 * Se le da a la bala la mitad de la dimensiones de la mirilla para reducir el tamaño
			 * de esta.
			 */
			AUDIO_DISPARO.play();
			bala = new Bala( x + CUARTAPARTE_DIMENSION_MIRILLA,
							 y + CUARTAPARTE_DIMENSION_MIRILLA,
							 MITAD_DIMENSION_MIRILLA,
							 MITAD_DIMENSION_MIRILLA);
			disparoDisponible = false;
			ultimoDisparo = System.currentTimeMillis();
		}
		
	}
	
	/**
	 * Pinta la mirilla en la posición en la que se encuentre.
	 * @param gc 
	 */
	@Override
	public void pintar(GraphicsContext gc) {
		
		//Pinta el rango de colisión de la bala.
//		gc.setStroke(Color.DARKRED);
//		gc.strokeRect((x + CUARTAPARTE_DIMENSION_MIRILLA), (y + CUARTAPARTE_DIMENSION_MIRILLA), 
//				MITAD_DIMENSION_MIRILLA, MITAD_DIMENSION_MIRILLA);
		gc.drawImage(CURSOR_JUGADOR, x, y);
		
	}
	

	public Bala getBala() {
		
		return bala;
		
	}

	
}
