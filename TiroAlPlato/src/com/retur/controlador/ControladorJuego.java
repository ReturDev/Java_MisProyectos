package com.retur.controlador;


import com.retur.modelo.juego.Juego;
import com.retur.modelo.juego.objetos.Jugador;
import com.retur.vista.VentanaJuego;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controla el cambio de la escena actual por la escena donde se reproducirá
 * el juego.
 * @author Sergio
 */
public class ControladorJuego{

	private final VentanaJuego VJ;
	private final Juego JUEGO;
	
	
	/**
	 * Constructor que recibe una ventana.
	 * @param stage Ventana de la aplicación
	 */
	public ControladorJuego(Stage stage) {
		
		VJ = new VentanaJuego(stage.getScene().getWidth(), stage.getScene().getHeight());
		JUEGO = new Juego(VJ);
		
		//Se cambia la escena de la ventana por la escena perteneciente al juego.
		stage.setScene(VJ.ESCENA);
		crearEventos();
		
	}
	
	
	/**
	 * Método encargado de crear los eventos que se usarán en el juego.
	 */
	private void crearEventos() {
	
		/*
		 * Se asigna un evento que captura el clic del raton para el disparo a los
		 * elementos.
		 */
		VJ.CANVAS.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				
				Jugador jugador = JUEGO.getJugador();
				jugador.MIRILLA.disparar();
				
			}
			
			
			
		});;
		
		
		
		//Se asigna un evento que captura al mover el raton para el movimiento de la mirilla.
		VJ.CANVAS.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				
				JUEGO.getJugador().MIRILLA.mover(e.getX(), e.getY());
				
			}
		});
		
	}
	
	
	
	/**
	 * Inicia el juego.
	 */
	public void iniciarJuego() {
		
		JUEGO.setDaemon(true);
		JUEGO.start();
		
	}

	
}
