package com.retur.vista;



import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Clase que representa una ventana al inicio de la aplicación.
 * @author Sergio
 *
 */
public class VentanaPrincipal extends VentanaMenu {

	protected static final int DIMENSION_FUENTE_TITULO = 50;
	
	/**
	 * Crea una instancia de VentanaPrincipal
	 * @param ancho Ancho para el tamaño de la escena
	 * @param alto Alto para el tamaño de la escena
	 */
	public VentanaPrincipal(double ancho, double alto) {
		
		super(ancho, alto);
		
		RAIZ.getStyleClass().add("raiz_vp");
		crearTitulo();
		
	}
	
	@Override
	protected Button crearBoton() {
		
		Button boton = new Button();
		boton.setPrefSize(150, 75);
		AnchorPane.setBottomAnchor(boton, ALTO_VENTANA/4);
		AnchorPane.setLeftAnchor(boton, (ANCHO_VENTANA/2) - (boton.getPrefWidth()/2));
		boton.getStyleClass().add("boton_start");
		boton.setText("Jugar");
		boton.setFont(Font.font("Elephant", FontWeight.BOLD, DIMENSION_FUENTE_BOTON));
		
		return boton;
		
	}
	
	@Override
	protected void crearTitulo() {
		
		Label titulo = new Label("TIRO AL PLATO MAL");
		titulo.setPrefSize(750, 50);
		titulo.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(titulo, (ALTO_VENTANA/4) - (titulo.getPrefHeight()/2));
		AnchorPane.setLeftAnchor(titulo, (ANCHO_VENTANA/2) - (titulo.getPrefWidth()/2));
		titulo.getStyleClass().add("titulo_principal");
		titulo.setFont(Font.font("Elephant", FontWeight.BOLD, DIMENSION_FUENTE_TITULO));
		
		RAIZ.getChildren().add(titulo);
		
	}
	
	
}
