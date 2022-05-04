package com.retur.main.modelo.convertidores;

import com.retur.main.modelo.enums.TiposPiezasAudiovisuales;

import javafx.util.StringConverter;


/**
 * Convertidor para pasar los tipos de piezas audiovisuales de {@link TiposPiezasAudiovisuales} a String y viceversa.
 * @author Sergio
 */
public class TiposConverter extends StringConverter<TiposPiezasAudiovisuales> {

	
	//Método no utilizado.
	@Override
	public TiposPiezasAudiovisuales fromString(String arg0) {
		
		return null;
	}

	

	@Override
	public String toString(TiposPiezasAudiovisuales tipo) {
		
		String texto = null;
		
		//Comprueba que el valor recibido no sea nulo.
		if(tipo != null) {
			
			//Almacena el nombre del tipo con la primera letra en mayúscula y remplazando los guiones bajos por espacios.
			texto = (tipo.toString().charAt(0) + tipo.toString().substring(1).toLowerCase());
			texto = texto.replace('_', ' ');
			
		}
		
		return texto;
		
	}
	
}
