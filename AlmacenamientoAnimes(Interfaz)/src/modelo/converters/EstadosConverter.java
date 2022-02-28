package modelo.converters;

import javafx.util.StringConverter;
import modelo.enums.Estados;

public class EstadosConverter extends StringConverter<Estados> {

		
		@Override
		public String toString(Estados arg0) {
			
			String nombreEstados = null;
			
			if(arg0 != null) {
				nombreEstados = arg0.toString().charAt(0) + arg0.toString().substring(1).toLowerCase();
			}
			
			
			
			return nombreEstados;
		}
		
		@Override
		public Estados fromString(String arg0) {
			return null;
		}
		
}
