package com.retur.main.modelo.alertas;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Clase funcional que a través de sus métodos se invocan alertas que se les mostrarán al usuario.
 * @author Sergio
 *
 */
public class Alertas {

	
	/**
	 * Muestra una ventana emergente informativa al usuario
	 * @param titulo Recibe el título que mostrar.
	 * @param mensaje Recibe el mensaje que mostrar.
	 */
	public static void alertaInformativa(String titulo, String mensaje) {
		
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setHeaderText(null);
		alerta.setTitle(titulo);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
		
	}

	/**
	 * Muestra una ventana emergente de un error al usuario
	 * @param titulo Recibe el título que mostrar.
	 * @param mensaje Recibe el mensaje que mostrar.
	 */
	public static void alertaError(String titulo, String mensaje) {
		
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.setTitle(titulo);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.showAndWait();
		
	}
	
	
	/**
	 * Muestra una ventana emergente para que el usuario acepte o rechace una opción.
	 * @param titulo Recibe el título que mostrar.
	 * @param mensaje Recibe el mensaje que mostrar.
	 */
	public static boolean alertaEleccion(String titulo, String mensaje) {
		boolean eleccion = false;
		
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setHeaderText(null);
		alerta.setTitle(titulo);
		alerta.setContentText(mensaje);
		Optional<ButtonType> botonPulsado = alerta.showAndWait();
		
		if(botonPulsado.get().equals(ButtonType.OK)) {
			
			eleccion = true;
			
		}
		
		return eleccion;
		
	}
	
}
