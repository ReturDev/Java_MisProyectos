package com.retur.main.modelo.excepciones;

/**
 * Excepción que se lanzará si se encuentra una obra ya almacenada con el mismo título
 * que el título de la obra a registrar.
 * @author Sergio
 *
 */
public class ObraYaRegistradaException extends Exception{

	private static final long serialVersionUID = 1L;

	public ObraYaRegistradaException() {}
	
	public ObraYaRegistradaException(String msg) {
		
		super(msg);
		
	}
	
	
}
