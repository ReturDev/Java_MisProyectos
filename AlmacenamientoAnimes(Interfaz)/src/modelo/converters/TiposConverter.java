package modelo.converters;

import javafx.util.StringConverter;
import modelo.enums.TiposPiezasAudiovisuales;

public class TiposConverter extends StringConverter<TiposPiezasAudiovisuales> {

	@Override
	public TiposPiezasAudiovisuales fromString(String arg0) {
		
		return null;
	}

	@Override
	public String toString(TiposPiezasAudiovisuales tipo) {
		
		String texto = null;
		
		if(tipo != null) {
			
			texto = (tipo.toString().charAt(0) + tipo.toString().substring(1).toLowerCase());
			texto = texto.replace('_', ' ');
		}
		
		return texto;
	}
	
}
