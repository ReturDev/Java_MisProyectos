package modulo.alertas;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alertas {

	public static void alertaInformativa(String titulo, String mensaje) {
		
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setHeaderText(null);
		alerta.setTitle(titulo);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
		
	}

	public static void alertaError(String titulo, String mensaje) {
		
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.setTitle(titulo);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.showAndWait();
		
	}
	
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
