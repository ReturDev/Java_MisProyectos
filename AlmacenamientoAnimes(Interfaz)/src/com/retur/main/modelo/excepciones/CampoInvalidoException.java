package com.retur.main.modelo.excepciones;

public class CampoInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CampoInvalidoException() {}

	public CampoInvalidoException(String msg) {
		
		super(msg);
		
	}
	
}
