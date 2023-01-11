package com.retur.controlador;



import com.retur.modelo.juego.objetos.Jugador;
import com.retur.vista.VentanaPuntuacion;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;


/**
 * Controla el cambio de escena al acabar el juego y muestra la puntuación obtenida.
 * @author Sergio
 */
public class ControladorPuntuacion {
	
	private final VentanaPuntuacion VPNT;
	

	/**
	 * Constructor que recibe una ventana.
	 * @param stage Ventana de la aplicación
	 */
	public ControladorPuntuacion(Stage stage) {
		
		VPNT = new VentanaPuntuacion(stage.getScene().getWidth(), stage.getScene().getHeight());
		eventoBotonPuntuacion();
		
	}
	
	/**
	 * Muestra la escena con la puntuación obtenida.
	 * @param jugador Jugador que ha obtenido la puntuación.
	 * @param stage Ventana donde se cambiará la escena.
	 */
	public void mostrarPuntuacion(Jugador jugador, Stage stage) {
		
		VPNT.mostrarPuntuacion(jugador);
		stage.setScene(VPNT.ESCENA);
		
	}
	
	private void eventoBotonPuntuacion() {
		
		VPNT.BOTON.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				Node nodo = (Node) e.getSource();
				
				Stage stage = (Stage) nodo.getScene().getWindow();
				
				new ControladorPrincipal(stage).mostrarVentanaPrincipal(stage);
				
			}
			
			
			
		});
		
		
	}

	
}
