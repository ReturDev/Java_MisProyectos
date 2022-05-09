package com.retur.main.modelo.funciones;

import com.retur.main.controlador.ControladorModificacion;
import com.retur.main.modelo.elementos.PiezaAudiovisual;
import com.retur.main.modelo.elementos.Temporada;
import com.retur.main.modelo.enums.Estados;
import com.retur.main.modelo.envio.datos.EnvioDatos;
import com.retur.main.modelo.excepciones.CampoInvalidoException;


import javafx.scene.control.*;

/**
 * Clase funcional para apoyar al {@link ControladorModificacion} con todo lo relacionado con consultas.
 * @author Sergio
 *
 */
public class Modificaciones {

	/**
	 * Dependiendo del valor del parámetro estado recibido, activará o desactivará campos dependiendo.
	 * @param estado
	 * @param tempV
	 * @param tempT
	 * @param tablaTemporadas
	 * @param capsV
	 */
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
		
		/*
		 * Obtiene el texto de las Temporadas Totales y crea las temporadas oportunas. La excepción
		 * nunca llegará a lanzarse.
		 */
		
		try {
			
			FuncionesApoyoControladores.introduccionTempT(tempT, tempV, tablaTemporadas, estado);
			
		} catch (CampoInvalidoException e) {
			
			e.printStackTrace();
			
		}
		FuncionesApoyoControladores.rellenarCapV(tablaTemporadas,tempV, estado);
		
	}
	
	
	/**
	 * Actualiza la lista de elementos almacenada en la clase Consultas y manda a través de la
	 * clase {@link EnvioDatos} la pieza cambiada para que se seleccione automáticamente una vez se cierre
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
