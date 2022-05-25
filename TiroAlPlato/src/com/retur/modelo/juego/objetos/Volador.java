package com.retur.modelo.juego.objetos;


import java.io.File;

import com.retur.modelo.juego.Juego;
import com.retur.modelo.juego.interfaces.Disparable;
import com.retur.modelo.juego.interfaces.Pintable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Clase que padre de todos los elementos voladores.
 * @author Sergio
 *
 */
public abstract class Volador extends Thread implements Disparable, Pintable{

	private static final int VELOCIDAD_DEFECTO = 3;
	private static final int DIMENSION_IMAGEN_DISPARADO = 70;

	
	private final Image IMAGEN_DISPARADO;
	private final Image IMAGEN_VOLADOR;
	private final int DESPAWN;
	private final int DIMENSION_IMAGEN;
	private final int MAX_SPAWN_Y;
	private final double DANYO;
	private final int PUNTOS;
	private final Juego JUEGO;
	
	private double x;
	private double y;
	private int velocidad;
	private boolean recorridoFinalizado;
	private boolean disparado;
	private Rectangle contornoColision;
	
	
	/**
	 * Crea una instancia de Volador
	 * @param dimensionImagen La dimensión de la imagen del volador (El mismo ancho que alto)
	 * @param imgVolador Ruta de la imagen del volador.
	 * @param danyo El daño que le hará al jugador.
	 * @param puntos Los puntos que dará al jugador
	 * @param jugador El jugador para la gestión de puntos y vidas.
	 * @param vj La ventana de juego para obtener las medidas y acceder al Canvas.
	 */
	public Volador(int dimensionImagen, File imgVolador, double danyo, int puntos, Juego juego) {
		
		this.DIMENSION_IMAGEN = dimensionImagen;
		this.IMAGEN_DISPARADO = new Image(new File("./src/resources/disparado.png").toURI().toString(),
								DIMENSION_IMAGEN_DISPARADO,
								DIMENSION_IMAGEN_DISPARADO,
								true,
								false);
		
		this.IMAGEN_VOLADOR = new Image(imgVolador.toURI().toString(),
								DIMENSION_IMAGEN,
								DIMENSION_IMAGEN,
								true,
								false);
		
		this.JUEGO = juego;
		this.DESPAWN = -DIMENSION_IMAGEN;
		this.MAX_SPAWN_Y = (int) (JUEGO.VJ.ALTO_VENTANA / 2.5);
		this.DANYO = danyo;
		this.PUNTOS = puntos;
		this.contornoColision = new Rectangle(x,y,DIMENSION_IMAGEN,DIMENSION_IMAGEN);
		this.x = JUEGO.VJ.ANCHO_VENTANA + DIMENSION_IMAGEN;
		this.y = generadorSpawnY();
		this.velocidad = VELOCIDAD_DEFECTO;
	
	}
	
	/**
	 * Mueve la posición del obejto volador.
	 */
	private void mover(){
		
		if(x <= DESPAWN) {
			
			salvado();
			
		}else {
			
			if(!disparado) {
				
				x -= velocidad;
				
			}else {
				
				
				disparado();
				
			}
			
			contornoColision.relocate(x, y);
			
		}
		
		
	}
	
	/**
	 * Se encarga de pintar el objeto en el canvas y dependiendo del valor de 'disparado'
	 * pintara una imagen u otra.
	 * @param gc 
	 */
	@Override
	public void pintar(GraphicsContext gc) {
		
		if(!recorridoFinalizado) {
			
			if(disparado) {
				
				gc.drawImage(IMAGEN_DISPARADO, x, y);
				
			}else {
				
				//Pintar el rango de colisión de los objetos.
				//gc.setStroke(Color.RED);
				//gc.strokeRect(x,y,DIMENSION_IMAGEN,DIMENSION_IMAGEN);
				gc.drawImage(IMAGEN_VOLADOR, x, y);
				
			}
			
		}
		
	}
	
	/**
	 * Genera la cordenada de 'y' donde hará spawn el objeto.
	 * @return
	 */
	private int generadorSpawnY() {

		return (int) (Math.random() * MAX_SPAWN_Y);
		
	}


	@Override
	public void run() {

		
		double nsPorSegundo = 1000000000 / Juego.APS;
		long ultimaActualizacion = System.nanoTime();
		double delta = 0;
		long tiempoActualBucle;

		
		/*
		 * Se inicia el bucle que realizaras las acciones del volador hasta que llegue a su destino
		 * o sea disparado por el camino.
		 */
		while(!recorridoFinalizado) {
			
			tiempoActualBucle = System.nanoTime();
			
			/*
			 * Almacenamos la cantidad de Actualizaciones que corresponden dependiendo
			 * del tiempo transcurrido entre vueltas de bucle para obtener las cantidades límites
			 * establecidas.
			 */
			delta += (tiempoActualBucle - ultimaActualizacion) / nsPorSegundo;
			ultimaActualizacion = tiempoActualBucle;
			
			/*
			 * Cuando el valor de delta corresponda a una actualización o más se ejecutará el búcle
			 * actualizando los elementos la cantidad de veces almacenadas.
			 */
			while(delta >= 1) {
			
				mover();
				comprobarAlcanzado();
				delta--;
				
			}
			
			//Se duerme el hilo para aumentar la eficiencia y reducir el consumo de recursos.
			try {
			
				sleep(0,(int) ((delta)*100000));
			
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			
			}
			
			
		}
	
		
	}
	
	@Override
	public void disparado(){
		/*
		 *Se comprobará que tipo de volador es y dependiendo de cual se realizará la acción
		 *oportuna. Luego se pausará el hilo para que se imprima la imagen al ser disparado
		 *el tiempo que está dormido y luego se le dará el estado de finalizado. 
		 */
		try {
			
			if(this instanceof Pajaro) {
				
				JUEGO.JUGADOR.reducirVidas(DANYO);
				
			}else {
				
				JUEGO.JUGADOR.aumentarPuntos(PUNTOS);
				
			}
			
			sleep(500);
			
			recorridoFinalizado = true;
			
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	@Override
	public void comprobarAlcanzado() {
		
		
		if(JUEGO.JUGADOR.MIRILLA.getBala() != null) {
			
			if(JUEGO.JUGADOR.MIRILLA.getBala().getRangoColision() != null) {
				
				Shape colision = Shape.intersect(JUEGO.JUGADOR.MIRILLA.getBala().getRangoColision(),contornoColision);
				
				if(colision.getLayoutBounds().getWidth() != -1 || colision.getLayoutBounds().getHeight() != -1) {
					
					disparado = true;
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void salvado(){
		
		recorridoFinalizado = true;
		
		if(this instanceof Plato) {
			
			JUEGO.JUGADOR.reducirVidas(DANYO);
			
		}else {
			
			JUEGO.JUGADOR.aumentarPuntos(PUNTOS);
			
		}
		
	}

	public boolean isRecorridoFinalizado() {
		return recorridoFinalizado;
	}

	public Rectangle getContornoColision() {
		return contornoColision;
	}

	
}
