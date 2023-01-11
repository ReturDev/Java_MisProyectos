package com.retur.modelo.juego.interfaces;

/**
 * Interfaz para los elementos que pueden ser disparados.
 * @author Sergio
 *
 */
public interface Disparable{

	/**
	 * Realiza las acciones correspondientes al ser disparado.
	 */
	void disparado();
	
	/**
	 * Realiza las acciones correspondientes al llegar a su destino si ser alcanzado.
	 */
	void salvado();

	/*
	 * Comprueba si ha sido alcanzado por algun disparo.
	 */
	void comprobarAlcanzado();
	
}
