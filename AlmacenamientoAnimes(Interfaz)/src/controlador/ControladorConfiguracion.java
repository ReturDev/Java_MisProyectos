package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import modelo.funcionesXML.OpcionesDirectorioXML;

public class ControladorConfiguracion implements Initializable {

	
	@FXML private Button botonExaminar;
	@FXML private TextField direccion;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		direccion.setText(OpcionesDirectorioXML.getRutaArchivo().toString());
		
	}
	
	@FXML
	private void examinar() {
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Elige la ubicación donde almacenar los datos.");
		chooser.setInitialDirectory(OpcionesDirectorioXML.getRutaArchivo().getParent().toFile());
		File nuevaDireccion = chooser.showDialog(botonExaminar.getScene().getWindow());
		if(nuevaDireccion != null) {
			
			direccion.setText(nuevaDireccion.toString());
			
		}
		
	}
	
	@FXML
	private void guardarConfig() {
		
		Path direccionSeleccionada = Paths.get(direccion.getText());
		
		if(!OpcionesDirectorioXML.getRutaArchivo().equals(direccionSeleccionada)) {
			
			try {
				OpcionesDirectorioXML.modificarConfig(direccionSeleccionada.toString());
			} catch (IOException e) {
		
				e.printStackTrace();
			}
			OpcionesDirectorioXML.creacionArchivos();
			
		}
		
		Stage stage = (Stage) botonExaminar.getScene().getWindow();
		
		stage.close();
	}

}
