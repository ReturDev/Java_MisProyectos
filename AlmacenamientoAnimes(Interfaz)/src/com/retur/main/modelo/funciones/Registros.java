package com.retur.main.modelo.funciones;

import com.retur.main.controlador.ControladorPrincipal;
import com.retur.main.modelo.elementos.Temporada;
import com.retur.main.modelo.enums.Estados;
import com.retur.main.modelo.enums.TiposPiezasAudiovisuales;
import com.retur.main.modelo.excepciones.CampoInvalidoException;

import javafx.collections.FXCollections;
import javafx.scene.control.*;

/**
 * Clase funcional para apoyar al {@link ControladorPrincipal} con todo lo relacionado con registros.
 * @author Sergio
 *
 */
public class Registros {
	
	/**
	 * Resetea los valores de los campos al cambio de tipo, habilita los campos en común para todas
	 * las obras y deshabilita todo lo relacionado con objetos serializables.
	 * @param estado
	 * @param tempT
	 * @param tempV
	 * @param tablaTemp
	 * @param titulo
	 * @param sinopsis
	 * @param botonRegistro 
	 */
	public static void cambioTipo(ComboBox<TiposPiezasAudiovisuales> tipo,ComboBox<Estados> estado, TextField tempT, TextField tempV, TableView<Temporada> tablaTemp,
			TextField titulo, TextArea sinopsis, Button botonRegistro) {
		
		//Se comprueba si el tipo elegido no es serializable.
		if(!ComprobacionesCampos.tipoSerializable(tipo.getSelectionModel().getSelectedItem())) {
			//Si no es serializable, se elimina la posibilidad de elegir el estado Siguiendo.
			estado.getItems().remove(Estados.SIGUIENDO);
			
		}else {
			/*
			 * Si el tipo es serializable, se comprueba si en los elementos del combo Estados se encuentra el estado Siguiendo.
			 * Si no se encuentra, se añade en la posición en la que se debería encontrar.
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
		
		botonRegistro.setDisable(true);
		
	}
	
	
	/**
	 * Desbloquea los campos pertenecientes a obras Serializables, dependiendo del Estado elegido
	 * se desactivarán todos o algunos se quedarán desactivados.
	 * @param serializable
	 * @param tempT
	 * @param tempV
	 * @param tablaTemporadas
	 * @param estado
	 * @param capsV
	 * @param botonRegistro 
	 */
	public static void prepararCamposTSerializable(boolean serializable,TextField tempT, TextField tempV,
			TableView<Temporada> tablaTemporadas,Estados estado,TableColumn<Temporada, Integer> capsV,
			Button botonRegistro) {
		
		//Comprueba si es serializable.
		
		if(serializable) {
			
			//Comprueba si el estado es Visto o Pendiente.
			if(estado.equals(Estados.VISTO) || estado.equals(Estados.PENDIENTE) ) {
				
				//Desactiva el campo de Temporadas Vistas.
				tempV.setDisable(true);
				//Desactiva la edici�n de la columna de Capitulos Vistos.
				capsV.setEditable(false);
				//Si el estado es Pendiente, fija el valor a 0.
				if (estado.equals(Estados.PENDIENTE)) {
					
					tempV.setText("0");
					
				}
				
			}else {
				
				//Activa el campo de Temporadas Vistas.
				tempV.setDisable(false);
				//Activa la edici�n de la columna de Capitulos Vistos.
				capsV.setEditable(true);
				
			}
			
			//Activa el valor 
			tempT.setDisable(false);
			
			//Le da una Temporada Total temporada m�nima obligatoria.
			if(tempT.getText().isEmpty() || tempT.getText().equals("0")) {
				
				tempT.setText("1");
				
			}
			
			//Activa la tabla para editar las temporadas.
			tablaTemporadas.setDisable(false);
			
			/*
			 * Obtiene el texto de las Temporadas Totales y crea las temporadas y oportunas. La excepcion en este
			 * caso nunca llegara a lanzarse.
			 */
			try {
				
				FuncionesApoyoControladores.introduccionTempT(tempT, tempV, tablaTemporadas, estado);
				
			} catch (CampoInvalidoException e) {
				
				e.printStackTrace();
				
			}
			
			FuncionesApoyoControladores.rellenarCapV(tablaTemporadas,tempV, estado);
			
		}
		
		botonRegistro.setDisable(false);
		
	}

}
