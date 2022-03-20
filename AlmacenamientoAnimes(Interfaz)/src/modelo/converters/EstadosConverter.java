package modelo.converters;

import javafx.util.StringConverter;
import modelo.enums.Estados;


/**
 * Convertidor para convertir los Estaods de String a {@link Estados} y viceversa.
 * @author Sergio
 */
public class EstadosConverter extends StringConverter<Estados> {

		
		@Override
		public String toString(Estados estado) {
			
			String nombreEstados = null;
			
			//Comprueba que el estado recibido no sea nulo.
			if(estado != null) {
				//Se obtiene el nombre del estado transformado en String con la primera letra en mayusculas.
				nombreEstados = estado.toString().charAt(0) + estado.toString().substring(1).toLowerCase();
			}
			
			
			
			return nombreEstados;
		}
		
		
		//Método no utilizado.
		@Override
		public Estados fromString(String estado) {
			return null;
		}
		
}
