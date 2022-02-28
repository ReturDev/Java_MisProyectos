package modelo.funciones;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import modelo.clases.Temporada;
import modelo.enums.Estados;
import modelo.enums.TiposPiezasAudiovisuales;


public class Registros {
	
	/**
	 * Método encargado de resetear los valores de los campos al cambio de tipo, habilitar los campos generales y
	 * deshabilitar todo lo relacionado con objetos serializables.
	 * @param estado
	 * @param tempT
	 * @param tempV
	 * @param tablaTemp
	 * @param titulo
	 * @param sinopsis
	 * @param botonRegistro 
	 */
	public static void CambioTipo(ComboBox<TiposPiezasAudiovisuales> tipo,ComboBox<Estados> estado, TextField tempT, TextField tempV, TableView<Temporada> tablaTemp,
			TextField titulo, TextArea sinopsis, Button botonRegistro) {
		
		//Se comprueba si el tipo elegido no es serializable.
		if(!ComprobacionesCampos.tipoSerializable(tipo.getSelectionModel().getSelectedItem())) {
			//Si no es serializable, se elimina la posibilidad de elegir el estado Siguiendo.
			estado.getItems().remove(Estados.SIGUIENDO);
		}else {
			/*
			 * Si el tipo es serializable, se comprueba si en los elementos del combo Estados se encuentra el estado Siguiendo.
			 * Si no se encuentra, se le añade en la posición que debería estar.
			 */
			if(!estado.getItems().contains(Estados.SIGUIENDO)) {
				estado.getItems().add(1, Estados.SIGUIENDO);
			}
		}
		
		//Se activa los campos de Titulo, Sinopsis y Estado.
		titulo.setDisable(false);
		sinopsis.setDisable(false);
		estado.setDisable(false);
		
		//Se resetea el valor del campo estado.
		estado.valueProperty().set(null);
		
		//Deshabilita el campo de temporadas totales y lo resetea.
		tempT.setDisable(true);
		tempT.setText("");
		
		//Deshabilita el campo de temporadas vistas y lo resetea.
		tempV.setDisable(true);
		tempV.setText("");
		
		//Deshabilita la tabla de temporadas y la deja vacía.
		tablaTemp.setDisable(true);
		tablaTemp.setItems(FXCollections.observableArrayList());
		
	}
	
	
	/**
	 * Método que desbloquea o bloquea los campos dependiendo si es serializable el tipo elegido y
	 * resetea o da valores dependiendo de la situación.
	 * @param serializable
	 * @param tempT
	 * @param tempV
	 * @param tablaTemporadas
	 * @param estado
	 * @param capsV
	 * @param botonRegistro 
	 */
	public static void prepararCamposTSerializable(boolean serializable,TextField tempT, TextField tempV, TableView<Temporada> tablaTemporadas,
			Estados estado,TableColumn<Temporada, Integer> capsV, Button botonRegistro) {
		
		//Comprueba si es serializable.
		if(serializable) {
			//Comprueba si el estado es Visto o Pendiente.
			if(estado.equals(Estados.VISTO) || estado.equals(Estados.PENDIENTE) ) {
				//Desactiva el campo de Temporadas Vistas.
				tempV.setDisable(true);
				//Desactiva la edición de la columna de Capitulos Vistos.
				capsV.setEditable(false);
				//Si el estado es Pendiente, fija el valor a 0.
				if (estado.equals(Estados.PENDIENTE)) {
					tempV.setText("0");
				}
			}else {
				//Activa el campo de Temporadas Vistas.
				tempV.setDisable(false);
				//Activa la edición de la columna de Capitulos Vistos.
				capsV.setEditable(true);
			}
			
			//Activa el valor 
			tempT.setDisable(false);
			//Le da una Temporada Total temporada mínima obligatoria.
			if(tempT.getText().isEmpty() || tempT.getText().equals("0")) {
				tempT.setText("1");
			}
			//Activa la tabla para editar las temporadas.
			tablaTemporadas.setDisable(false);
			//Obtiene el texto de las Temporadas Totales y crea las temporadas y oportunas
			FuncionesApoyoControladores.introduccionTempT(tempT, tempV, tablaTemporadas, estado);
			FuncionesApoyoControladores.rellenarCapV(tablaTemporadas,tempV, estado);
			
		
		//Si por algún motivo el tipo se cambiara, desactivará los campos y los reseteara como al inicio excepto estado y titulo.
		}else {
			
			//Deshabilita el campo de Temporadas Totales y le quita el valor.
			tempT.setDisable(true);
			tempT.setText("");
			
			//Deshabilita el campo de Temporadas Totales y le quita el valor.
			tempV.setDisable(true);
			tempV.setText("");
			
			//Deshabilita la Tabla de Temporadas
			tablaTemporadas.setDisable(true);
			//Se le sobreescribe su lista de elementos por una vacía(No se establece nula para ahorrar futuros errores)
			tablaTemporadas.setItems(FXCollections.observableArrayList());
			
		}
		
		botonRegistro.setDisable(false);
		
	}

	
	

	
	
}
