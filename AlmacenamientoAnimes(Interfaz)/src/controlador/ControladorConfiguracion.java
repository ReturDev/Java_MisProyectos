package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
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
		chooser.setInitialDirectory(OpcionesDirectorioXML.getRutaArchivo().toFile());
	
		
	}

}
