package com.retur.vista;


import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


/**
 * Ventana donde poder visualizar el juego.
 * @author Sergio
 *
 */
public class VentanaJuego extends Ventana {
	//Dimension de la fuente de la puntuación.
	private static final int DIMENSION_FUENTE_P = 20;
	//Dimension de la fuente de las vidas.
	private static final int DIMENSION_FUENTE_V = 30;
	//Separación horizontal para la impresión de marcadores durante el juego.
	private static final int SEPARACION_HORIZONTAL = 10;
	//Separación vertical para la impresión de marcadores durante el juego.
	private static final int SEPARACION_VERTICAL = 25;
	
	
	public final Canvas CANVAS;
	
	
	/**
	 * Crea una instandia de la ventana de juego.
	 * @param ancho Ancho para el tamaño de la escena
	 * @param alto Alto para el tamaño de la escena
	 */
	public VentanaJuego(double ancho, double alto) {
		
		super(ancho, alto);
		
		
		CANVAS = new Canvas(ANCHO_VENTANA, ALTO_VENTANA);
		//Se oculta el cursor.
		RAIZ.setCursor(Cursor.NONE);
		//Se añade el Canvas a la raiz.
		RAIZ.getChildren().add(CANVAS);

	}
	
	
	/**
	 * Pinta las vidas del jugador.
	 * @param vidas Vidas del jugador.
	 */
	public void pintarPanelVidas(double vidas) {
		
		GraphicsContext gc = CANVAS.getGraphicsContext2D();
		
		gc.setFont(Font.font("Arial", FontWeight.BOLD, DIMENSION_FUENTE_V));
		gc.setFill(Color.DARKRED);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.fillText("VIDAS: " + vidas, ANCHO_VENTANA - SEPARACION_HORIZONTAL, ALTO_VENTANA - SEPARACION_VERTICAL);
		
		
	}
	
	/**
	 * Pinta la puntuación del jugador.
	 * @param puntuacion Puntuación del jugador
	 */
	public void pintarPanelPuntuacion(String puntuacion) {
		
		GraphicsContext gc = CANVAS.getGraphicsContext2D();
		
		gc.setFont(Font.font("Arial", FontWeight.BOLD, DIMENSION_FUENTE_P));
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.fillText(puntuacion, ANCHO_VENTANA - SEPARACION_HORIZONTAL, SEPARACION_VERTICAL);
		
		
	}

}
