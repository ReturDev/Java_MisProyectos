package com.retur.main.modelo.funciones;


import com.retur.main.modelo.elementos.Temporada;
import com.retur.main.modelo.enums.TiposPiezasAudiovisuales;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 * Clase funcional de apoyo para ayudar a las comprobaciones de los campos.
 * @author Sergio
 *
 */
public class ComprobacionesCampos {
	

	/**
	 * Se encarga de añadir un listener a campos TextField encargados de las temporadas para comprobar
	 * si los valores introducidos son dígitos y que sean maximo 2.
	 * @param campo TextField al que añadir el listener.
	 */
	public static void listenerTextoTemporadasTextField(TextField campo){
		
		//Se le añade a la propiedad de texto del campo un listener y se sobreescribe la interfaz como parámetro.
		campo.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	//Se comprueba si el nuevo valor es distinto a nulo.
		    	if(newValue != null) {
		    		//Si cada nuevo valor introducido es distinto a un dígito lo borra.
		    		if (!newValue.matches("\\d")) {
			            campo.setText(newValue.replaceAll("[^\\d]", ""));
			        }
		    		//Si la longitud del nuevo valor supera los dos caracteres, el tercer caracter no se mostrará.
			        if(newValue.length() >= 3) {
			        	
			        	campo.setText(newValue.substring(0,2));
			        	
			        }
			        
			        
		    	}
		    }
		});
	}
	
	/**
	 * Cuando el campo tempVistas pierde el foco, comprueba que no se haya quedado el campo
	 * vacío. Si este se ha quedado vacío le dará un valor por defecto al campo de 0.
	 * @param tempVistas
	 */
	public static void listenerCambioFocoTempVistas(TextField tempVistas) {
		
		tempVistas.focusedProperty().addListener(new ChangeListener<Boolean>() {
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
				//Comprobamos si antes tenia el foco y ahora lo ha perdido.
				if(oldProperty && !newProperty) {
					//Si el campo una vez pierda el foco está vacío, se le dara un valor por defecto de 0.
					if(tempVistas.getText().isEmpty()) {
						
						tempVistas.setText("0");
												
					}
					
				}
				
			}
			
		});
		
	}
	
	/**
	 * Cuando el campo tempTotales pierde el foco, comprueba que no se haya quedado el campo
	 * vacío. Si este se ha quedado vacío le dará un valor por defecto al campo de 1.
	 * @param tempTotales
	 */
	public static void listenerCambioFocoTempTotales(TextField tempTotales) {
		
		tempTotales.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
				//Comprobamos si antes tenia el foco y ahora lo ha perdido.
				if(oldProperty && !newProperty) {
					//Si el campo una vez pierda el foco está vacío, se le dara un valor por defecto de 0.
					if(tempTotales.getText().isEmpty()) {
						tempTotales.setText("1");
					}
				}
				
			}
		});
		
	}
	
	/**
	 * Cuando el campo titulo pierde el foco, comprueba que no se haya quedado el campo
	 * vacío. Si este se ha quedado vacío le devolverá el titulo que tenia anteriormente. 
	 * @param titulo
	 * @param pieza
	 */
	public static void listenerCambioFocoTitulo(TextField titulo, String tituloAnterior) {
		
		titulo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
				
				//Comprueba que antes tuviera el foco y ahora lo ha perdido.
				if(oldProperty && !newProperty) {
					
					//Comprueba si el título se ha dejado vacío, y si es así se le asigna el título que tenía antes de modificarse.
					if (titulo.getText().isEmpty()) {
						
						titulo.setText(tituloAnterior);
						
					}
					
				}
				
			}
		
		});
		
	}
	
	
	/**
	 * Método para crear o borrar temporadas dependiendo de las temporadas totales
	 * introducidas por el usuario sobre la lista obtenida de la tabla de temporadas.
	 * @param temporadasBase Lista de las temporadas para modificar.
	 * @param temporadasTotales Cantidad de las temporadas totales introducidas por el usuario.
	 */
	public static void crearBorrarTemporadas(ObservableList<Temporada> temporadasBase, int temporadasTotales){
		
		//Almacenamos la cantidad total de elementos en la lista.
		int cantidadElementos = temporadasBase.size();
		
		//Comprobamos si el usuario quiere menos o más temporadas de las ya incluidas en la lista.
		if( temporadasTotales < cantidadElementos) {
			
			/*
			 * Eliminamos a partir de la Última temporada contenida en la lista
			 * hasta la cantidad que el usuario a introducido.
			 */
			for(int i = (cantidadElementos-1); i >= temporadasTotales; i--) {
				temporadasBase.remove(i);
			}
			
		}else if(temporadasTotales > cantidadElementos) {
			
			/*
			 * Creamos la cantidad de temporadas de diferencia que hay entre las que 
			 * existían en la lista con las introducidas por el usuario.
			 */
			for(int i = (cantidadElementos+1); i <= temporadasTotales; i++) {
				
				temporadasBase.add(new Temporada(i, 0, 0));
				
			}
			
		}
		
	}
	
	/**
	 * Comprueba si el elemento es serializable a partir del TipoPiezaAudivisual pasada por
	 * parámetros.
	 * @param tipo
	 * @return
	 */
	public static boolean tipoSerializable(TiposPiezasAudiovisuales tipo) {
		
		boolean serializable = false;
		
		if(tipo.equals(TiposPiezasAudiovisuales.ANIMES) || tipo.equals(TiposPiezasAudiovisuales.SERIES)) {
			
			serializable = true;
		}
		
		return serializable;
	}
	
	
}
