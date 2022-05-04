package com.retur.main.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import com.retur.main.modelo.alertas.Alertas;
import com.retur.main.modelo.alertas.texto.MensajesAlertas;
import com.retur.main.modelo.funciones.xml.OpcionesDirectorioXML;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


/**
 * Controla los valores de los campos y los eventos de la Vista de Configuración.
 * @author Sergio
 *
 */
public class ControladorConfiguracion implements Initializable {

	
	@FXML private Button botonExaminar;
	@FXML private TextField direccion;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/*
		 * Le asignamos el texto de la dirección del directorio en la que se encuentra 
		 * ahora mismo el documento.
		 */
		direccion.setText(OpcionesDirectorioXML.getRutaArchivo().getParent().toString());
		
	}
	
	
	/**
	 * Método que se ejecuta al pulsar el botón para examinar los directorios donde almacenar
	 * el documento AlmacenamientoDatos.xml.
	 */
	@FXML
	private void examinar() {
		
		//Se crea el seleccionador de directorios.
		DirectoryChooser chooser = new DirectoryChooser();
		//Se le asigna un título a la ventana que se abrirá.
		chooser.setTitle(MensajesAlertas.TITULO_EXAMINADOR);
		//Se obtiene la ruta que está almacenada en este momento en el archivo de configuración.
		Path direccionInicial = OpcionesDirectorioXML.getRutaArchivo().getParent();
		
		//Se comprueba si la dirección almacenada no existe
		if(!Files.isDirectory(direccionInicial)) {
			
			//Se sustituye la dirección almacenada en la configuración por la dirección donde se almacena por defecto.
			direccionInicial = OpcionesDirectorioXML.UBICACION_DATOS_DEFECTO;
			
		}
		
		//Establece el directorio en el que estará situado el examinador de directorios al iniciarse.
		chooser.setInitialDirectory(direccionInicial.toFile());
		
		//Muestra el examinador de directorios se almacenará en la variable.
		File nuevaDireccion = chooser.showDialog(botonExaminar.getScene().getWindow());
		
		//Comprueba que se haya seleccionado algún directorio en el examinador.
		if(nuevaDireccion != null) {
			
			//Se almacena la nueva dirección como texto en el TextField.
			direccion.setText(nuevaDireccion.toString());
			
		}
		
	}
	
	/**
	 * Se ejecuta al pulsar el botón guardar.
	 */
	@FXML
	private void guardarConfig() {
		
		//Se guarda la dirección del TextField como una ruta.
		Path direccionSeleccionada = Paths.get(direccion.getText());
		
		//Comprueba que la ruta almacenada en el documento config y la obtenida del Textfield no sea la misma.
		if(!OpcionesDirectorioXML.getRutaArchivo().getParent().equals(direccionSeleccionada)) {
			
			try {
				/*
				 * Se llamará al método para modificar la dirección almacenada en el archivo de configuración y 
				 * al método de creación de archivos para que cree un XML si este no existe en la localización.
				 */
				OpcionesDirectorioXML.modificarConfig(direccionSeleccionada.toString());
				OpcionesDirectorioXML.creacionArchivos();
				
			} catch (IOException e) {
		
				Alertas.alertaError(MensajesAlertas.T_ERROR_GENERICO, e.getMessage());
				
			}
			
		}
		
		//Se obtiene la ventana y se cierra.
		Stage stage = (Stage) botonExaminar.getScene().getWindow();
		
		stage.close();
		
	}

}
