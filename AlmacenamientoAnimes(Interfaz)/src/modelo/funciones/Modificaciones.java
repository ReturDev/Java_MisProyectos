package modelo.funciones;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import modelo.clases.PiezaAudiovisual;
import modelo.clases.Temporada;
import modelo.enums.Estados;
import modelo.envioDatos.EnvioDatos;

public class Modificaciones {

	
	public static void comprobarEstado(Estados estado, TextField tempV, TextField tempT, TableView<Temporada> tablaTemporadas,
			TableColumn<Temporada, Integer> capsV) {
		
		if(estado.equals(Estados.VISTO) || estado.equals(Estados.PENDIENTE) ) {
			tempV.setDisable(true);
			capsV.setEditable(false);
			if (estado.equals(Estados.PENDIENTE)) {
				tempV.setText("0");
			}
		}else {
			tempV.setDisable(false);
			capsV.setEditable(true);
		}
		
		//Obtiene el texto de las Temporadas Totales y crea las temporadas y oportunas
		FuncionesApoyoControladores.introduccionTempT(tempT, tempV, tablaTemporadas, estado);
		FuncionesApoyoControladores.rellenarCapV(tablaTemporadas,tempV, estado);
		
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
	 */
	public static void prepararCamposTSerializable(boolean serializable,TextField tempT, TextField tempV, TableView<Temporada> tablaTemporadas,
			Estados estado,TableColumn<Temporada, Integer> capsV) {
		
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
			tempT.setText("1");
			//Activa la tabla para editar las temporadas.
			tablaTemporadas.setDisable(false);
			
			
		
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
		
	}
	
	/**
	 * Actualiza la lista de elementos almacenada en la clase Consultas y manda a través de la
	 * clase EnvioDatos, la pieza cambiada para que se seleccione automáticamente una vez se cierre
	 * la ventana de modificado.
	 * @param pieza
	 */
	public static void actualizarConsultasModificado(PiezaAudiovisual pieza) {
		
		
		for(PiezaAudiovisual elemento : Consultas.getListaElementosBase()) {
			
			if(elemento.getId() == pieza.getId()) {
				
				Consultas.getListaElementosBase().remove(elemento);
				Consultas.getListaElementosBase().add(pieza);
				EnvioDatos.getInstance().setDatosTransferencia(pieza);
				break;
			}
			
		}
		
	}
	
}
