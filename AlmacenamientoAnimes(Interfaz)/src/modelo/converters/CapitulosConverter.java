package modelo.converters;

import javafx.util.StringConverter;

/**
 * Convertidor para convertir los Capitulos de String a Integer y viceversa.
 * @author Sergio
 */
public class CapitulosConverter extends StringConverter<Integer>{

	@Override
	public Integer fromString(String num) {
		Integer numero = null;
		
		//Comprueba que el String recibido almacene números.
		if(num.matches("\\d*")) {
			numero = Integer.parseInt(num);
		}
		
		return numero;
	}

	@Override
	public String toString(Integer num) {
		return String.valueOf(num);
	}

}
