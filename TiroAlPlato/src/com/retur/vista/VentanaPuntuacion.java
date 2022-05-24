package com.retur.vista;

import com.retur.modelo.juego.objetos.Jugador;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Clase que representa una ventana que mostrará la puntuación al acabar el juego.
 * @author Sergio
 *
 */
public class VentanaPuntuacion extends VentanaMenu {

	private static final String CLASE_RAIZ = "raiz_puntuacion" ;
	
	
	/**
	 * Crea una instancia de VentanaPuntuación
	 * @param ancho Ancho para el tamaño de la escena
	 * @param alto Alto para el tamaño de la escena
	 */
	public VentanaPuntuacion(double ancho, double alto) {
		
		super(ancho, alto);
		RAIZ.getStyleClass().add(CLASE_RAIZ);
		crearTitulo();
		
	}
	
	@Override
	protected void crearTitulo() {
		
		Label titulo = new Label("PUNTUACION");
		
		titulo.setPrefSize(ANCHO_VENTANA/3, 50);
		titulo.setAlignment(Pos.CENTER);
		titulo.getStyleClass().add("titulo_pnt");
		titulo.setFont(Font.font("Elephant", FontWeight.BOLD, DIMENSION_FUENTE_TITULO));
		
		AnchorPane.setTopAnchor(titulo, (ALTO_VENTANA/5) - (titulo.getPrefHeight()/2));
		AnchorPane.setLeftAnchor(titulo, (ANCHO_VENTANA/2) - (titulo.getPrefWidth()/2));
		
		RAIZ.getChildren().add(titulo);
		
	}

	@Override
	protected Button crearBoton() {
		
		
		Button boton = new Button();
		
		boton.setMinWidth(ANCHO_VENTANA/3);
		boton.getStyleClass().add("boton_pnt");
		boton.setText("Regresar al menu");
		boton.setFont(Font.font("Elephant", FontWeight.BOLD, 30));
		
		
		AnchorPane.setBottomAnchor(boton, ALTO_VENTANA/4);
		AnchorPane.setLeftAnchor(boton, (ANCHO_VENTANA/2) - (boton.getMinWidth()/2));

		return boton;
		
	}


	/**
	 * Crea un Label donde se muestra la puntuación que obtuvo el jugador en el juego
	 * y lo añade a la raiz para mostrarlo
	 * @param jugador Recibe el jugador para obtener la puntuación obtenida.
	 */
	public void mostrarPuntuacion(Jugador jugador) {

		Label puntuacion = new Label(jugador.getPuntos() + "");
		
		puntuacion.setPrefSize(ANCHO_VENTANA/3, 50);
		puntuacion.setAlignment(Pos.CENTER);
		puntuacion.getStyleClass().add("titulo_pnt");
		puntuacion.setFont(Font.font("Arial", FontWeight.BOLD, DIMENSION_FUENTE_TITULO));
		
		AnchorPane.setTopAnchor(puntuacion, (ALTO_VENTANA/3) - (puntuacion.getPrefHeight()/2));
		AnchorPane.setLeftAnchor(puntuacion, (ANCHO_VENTANA/2) - (puntuacion.getPrefWidth()/2));
		
		RAIZ.getChildren().add(puntuacion);
		
	}


	
	
	
}
