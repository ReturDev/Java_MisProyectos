package com.retur.modelo.juego;


import com.retur.controlador.ControladorPuntuacion;
import com.retur.modelo.juego.objetos.Fondo;
import com.retur.modelo.juego.objetos.Jugador;
import com.retur.modelo.juego.objetos.Pajaro;
import com.retur.modelo.juego.objetos.Plato;
import com.retur.modelo.juego.objetos.Volador;
import com.retur.vista.VentanaJuego;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


/**
 * Núcleo del juego, donde se ejecutarán todas las acciones que tengan que ver con este, como
 * el dibujo en el Canvas, el búcle principal y la creación de elementos disparables.
 * @author Sergio
 *
 */
public class Juego extends Thread{

	//APS(Actualizaciones Por Segundo)
	public static final int APS = 60;
	//FPS(Frames Por Segundo)
	public static final int FPS = 60;
	private static final int CAPACIDAD_VOLADORES = 15;
	private static final int MILIS_ESPERA_VOLADORES = 750;
	
	private final GraphicsContext GC;
	public final VentanaJuego VJ;
	private final Fondo FONDO;
	public final Jugador JUGADOR;
	private final Volador[] VOLADORES;
	
	//TODO pintarFPS por pantalla;
	private int contadorFPS;
	//private int contadorAPS;
	private int contadorVoladores;
	private boolean iniciado;
	
	public Juego(VentanaJuego vj) {
		
		this.FONDO = new Fondo(vj.ANCHO_VENTANA, vj.ALTO_VENTANA);
		this.JUGADOR = new Jugador(vj.ANCHO_VENTANA, vj.ALTO_VENTANA);
		this.VOLADORES = new Volador[CAPACIDAD_VOLADORES];
		this.VJ = vj;
		this.GC = vj.CANVAS.getGraphicsContext2D();
		
	}
	
	@Override
	public void run() {
		iniciado = true;
		gameLoop();
	}
	
	/**
	 * Bucle principal del juego.
	 */
	private void gameLoop() {
		
		double nsPorSegundoFPS = 1000000000 / FPS;
		double nsPorSegundoAPS = 1000000000 / APS;
		
		long tiempoActual;
		long tiempoTranscurrido = System.nanoTime();
		long tiempoMilis = System.currentTimeMillis();
		long ultimoVolador = System.currentTimeMillis();
		
		//Almacena el tiempo transcurrido para las APS.
		double delta = 0;
		//Almacena el tiempo transcurrido para las FPS.
		double renderizado = 0;
		
		while(iniciado) {
			
			//Almacena el tiempo de la vuelta del bucle.
			tiempoActual = System.nanoTime();
			/*
			 * Almacenamos la cantidad de Frames y de Actualizaciones que corresponden dependiendo
			 * del tiempo transcurrido entre vueltas de bucle para obtener las cantidades límites
			 * establecidas.
			 */
			delta += (tiempoActual - tiempoTranscurrido) / nsPorSegundoAPS;
			renderizado += ((tiempoActual - tiempoTranscurrido) / nsPorSegundoFPS);
			tiempoTranscurrido = tiempoActual;
			
			/*
			 * Comprueba que desde que se generó el último objeto volador hasta el momento actual
			 * hayan transcurrido los milisegunos establecidos.
			 */
			if(System.currentTimeMillis() - ultimoVolador >= MILIS_ESPERA_VOLADORES) {
				
				eliminarVoladoresMuertos();
				if(contadorVoladores != CAPACIDAD_VOLADORES) {

					generarVolador();
					
				}
				ultimoVolador = System.currentTimeMillis();
				
			}
			
			/*
			 * Cuando el valor de delta corresponda a una actualización o más se ejecutará el búcle
			 * actualizando los elementos la cantidad de veces almacenadas.
			 */
			while(delta >= 1) {
				
				actualizarElementos();
				pintarTodo();
				delta--;
				
			}
			
			/*
			 * Cuando el valor de delta corresponda a una impresión o más se ejecutará el búcle
			 * dibujando los elementos la cantidad de veces almacenadas.
			 */
			while(renderizado >= 1) {
				
				pintarTodo();
				renderizado--;
				
			}
			
			//Se duerme el hilo para aumentar la eficiencia y reducir el consumo de recursos.
			
			try {
				sleep(0,(int) ((delta)*100000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			//TODO Mostrar FPS y APS en la ventada de juego.
			if(System.currentTimeMillis() - tiempoMilis >= 1000) {
				
				//System.out.println("APS" + contadorAPS);
				System.out.println("FPS" + contadorFPS);
				tiempoMilis = System.currentTimeMillis();
				contadorFPS = 0;
				//contadorAPS = 0;
				
			}
			
		}
		
	}
	

	/**
	 * Actualiza los elementos del juego.
	 */
	private void actualizarElementos() {
		
		JUGADOR.MIRILLA.recargaDisparo();
		comprobacionFinalJuego();
		//contadorAPS++;
		
	}
	
	/**
	 * Comprueba si el jugador se ha quedado sin vidas para finalizar el juego.
	 */
	private void comprobacionFinalJuego() {
		
		if (JUGADOR.getVidas() == 0) {
			
			finalizarJuego();
			
		}
		
	}
	
	
	/**
	 * Finaliza el juego.
	 */
	private void finalizarJuego() {
		
		iniciado = false;
		/*
		 * Crea un subproceso del hilo encargado de ejecutar todo lo relacionado con la interfaz
		 * para poder hacer cambios en esta.
		 */
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				Stage stage = (Stage) GC.getCanvas().getScene().getWindow();
				
				//Muestra la escena con la puntuación.
				new ControladorPuntuacion(stage).mostrarPuntuacion(JUGADOR, stage);
				
			}
		});
		
	}
 
	/**
	 * Método encargado de pintar en el Canvas.
	 */
	private void pintarTodo() {
		
		FONDO.pintar(GC);
		
		for(int i = 0; i < VOLADORES.length; i++) {
			
			if(VOLADORES[i] != null) {
				
				VOLADORES[i].pintar(GC);
				
			}
			
		}
		
		VJ.pintarPanelPuntuacion(JUGADOR.getPuntos() + "");
		VJ.pintarPanelVidas(JUGADOR.getVidas());
		JUGADOR.MIRILLA.pintar(GC);
		contadorFPS++;
		
	}
	
	/**
	 * Elimina los elementos que han muerto, ya sea porque han llegado al final de su
	 * recorrido o hayan sido disparados.
	 */
	private void eliminarVoladoresMuertos() {
		
		for(int i = 0; i < CAPACIDAD_VOLADORES; i++) {
			
			Volador volador = VOLADORES[i];
			
			if(volador != null) {
				
				if(volador.isRecorridoFinalizado()) {
					
					VOLADORES[i] = null;
					contadorVoladores--;
					
				}
				
			}
			
		}
		
		
	}
	
	
	/**
	 * Génera un objeto volador y comienza su recorrido.
	 */
	private void generarVolador() {
		
		boolean generado = false;
		
		
		for(int i = 0; i < CAPACIDAD_VOLADORES && !generado; i++) {
			
			if(VOLADORES[i] == null) {
				
				Volador volador = voladorAleatorio();
				volador.setDaemon(true);
				volador.start();
				
				VOLADORES[i] = volador;
				
				contadorVoladores++;
				generado = true;
				
			}
			
		}
		
	}

	/**
	 * Genera un volador de manera aleatoria.
	 * @return
	 */
	private Volador voladorAleatorio() {
		double num = Math.random();
		
		Volador volador = num < 0.9 ? new Plato(this) : new Pajaro(this);
		
		return volador;
	}

	
	public Jugador getJugador() {
		return JUGADOR;
	}

	
}
