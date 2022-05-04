package com.retur.main.modelo.excepciones;


/**
 * Excepción que se lanzará al quedar un campo vacío o mal rellenado.
 * @author sergi
 *
 */
public class CampoInvalidoException extends Exception {


	private static final long serialVersionUID = 1L;
	
	public CampoInvalidoException() {}

	public CampoInvalidoException(String msg) {
		
		super(msg);
		
	}
	
}
