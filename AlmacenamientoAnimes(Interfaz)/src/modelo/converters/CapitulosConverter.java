package modelo.converters;

import javafx.util.StringConverter;

public class CapitulosConverter extends StringConverter<Integer>{

	@Override
	public Integer fromString(String num) {
		Integer numero = null;
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
