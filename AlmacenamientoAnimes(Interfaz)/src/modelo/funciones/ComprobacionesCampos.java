package modelo.funciones;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import modelo.clases.Temporada;
import modelo.enums.TiposPiezasAudiovisuales;

public class ComprobacionesCampos {
	

	/**
	 * Se encarga de a�adir un listener a campos TextField encargados de las temporadas para comprobar
	 *  si los valores introducidos son digitos y la m�xima cantidad de valores permitida sean 2 d�gitos.
	 * @param campo
	 */
	public static void temporadasTextField(TextField campo){
		//Se le a�ade a la propiedad de texto del campo un listener y se sobreescribe la interfaz como parametro.
		campo.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	//Se comprueba si el nuevo valor es distinto a nulo.
		    	if(newValue != null) {
		    		//Si cada nuevo valor introducido es distinto a un d�gito lo borra.
		    		if (!newValue.matches("\\d")) {
			            campo.setText(newValue.replaceAll("[^\\d]", ""));
			        }
		    		//Si la longitud del nuevo valor supera los dos caracteres, el tercer caracter no se mostrar�.
			        if(newValue.length() >= 3) {
			        	campo.setText(newValue.substring(0,2));
			        }
		    	}
		    }
		});
	}
	
	
	/**
	 * M�todo para crear o borrar temporadas dependiendo de las temporadas totales
	 * introducidas por el usuario sobre la lista obtenida de la tabla de temporadas.
	 * @param temporadasBase Lista de las temporadas para modificar.
	 * @param temporadasTotales Cantidad de las temporadas totales introducidas por el usuario.
	 */
	public static void crearBorrarTemporadas(ObservableList<Temporada> temporadasBase, int temporadasTotales){
		
		//Almacenamos la cantidad total de elementos en la lista.
		int cantidadElementos = temporadasBase.size();
		
		//Comprobamos si el usuario quiere menos o m�s temporadas de las ya incluidas en la lista.
		if( temporadasTotales < cantidadElementos) {
			
			/*
			 * Eliminamos a partir de la �ltima temporada contenida en la lista
			 * hasta la cantidad que el usuario a introducido.
			 */
			for(int i = (cantidadElementos-1); i >= temporadasTotales; i--) {
				temporadasBase.remove(i);
			}
			
		}else if(temporadasTotales > cantidadElementos) {
			
			/*
			 * Creamos la cantidad de temporadas de diferencia que hay entre las que 
			 * existian en la lista con las introducidas por el usuario.
			 */
			for(int i = (cantidadElementos+1); i <= temporadasTotales; i++) {
				
				temporadasBase.add(new Temporada(i, 0, 0));
				
			}
			
		}
		
	}
	
	/**
	 * Comprueba si el elemento es serializable a partir del TipoPiezaAudivisual pasada por
	 * par�metros.
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
