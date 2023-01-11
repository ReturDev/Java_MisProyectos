package com.retur.controlador;


import com.retur.vista.VentanaPrincipal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Se encarga de cambiar la escena actual por la escena encargada de mostar la escena de
 * la Ventana Principal.
 * @author Sergio
 */
public class ControladorPrincipal{

	
	private final VentanaPrincipal VP;

	
	/**
	 * Constructor que recibe una ventana.
	 * @param stage Ventana de la aplicación
	 */
	public ControladorPrincipal(Stage stage) {
		
		VP = new VentanaPrincipal(stage.getScene().getWidth(), stage.getScene().getHeight());
		eventoInicioJuego();
		
	}
	
	/**
	 * Cambia la escena de la ventana por una escena de la Ventana Principal.
	 * @param stage 
	 */
	public void mostrarVentanaPrincipal(Stage stage) {
		
		stage.setScene(VP.ESCENA);
		
	}
	
	
	/**
	 * Crea un evento para el botón de la Ventana Principal que ejecuta el juego al ser pulsado.
	 */
	private void eventoInicioJuego() {
		
		VP.BOTON.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				//Se obtiene el stage del elemento que ha activado el evento.
				Node nodo = (Node) e.getSource();
				Stage stage = (Stage) nodo.getScene().getWindow();
				
				//Se inicia el juego.
				new ControladorJuego(stage).iniciarJuego();
				
			}
		});
		
	}
	
}
