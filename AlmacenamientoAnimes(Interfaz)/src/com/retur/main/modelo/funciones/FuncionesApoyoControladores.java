package com.retur.main.modelo.funciones;

import java.util.ArrayList;
import java.util.HashSet;

import com.retur.main.modelo.alertas.Alertas;
import com.retur.main.modelo.alertas.texto.MensajesAlertas;
import com.retur.main.modelo.elementos.*;
import com.retur.main.modelo.enums.*;
import com.retur.main.modelo.excepciones.CampoInvalidoException;
import com.retur.main.modelo.excepciones.ObraYaRegistradaException;
import com.retur.main.modelo.funciones.xml.ObtencionDatosXML;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.TableColumn.CellEditEvent;

public class FuncionesApoyoControladores {

	//TODO Por aqui.
	
	/**
	 * M�todo encargado de las acciones con la introducción de un valor en el campo de Temporadas Totales.
	 * @param tempTotales
	 * @param tempVistas
	 * @param tablaTemporadas
	 * @param estado
	 */
	public static void introduccionTempT(TextField tempTotales, TextField tempVistas, TableView<Temporada> tablaTemporadas,
			Estados estado) {

		// Comprobamos que el valor del campo es distinto a 0.
		if(!tempTotales.getText().isEmpty()) {
			
			if (Integer.parseInt(tempTotales.getText()) != 0) {

				// Se comprueba la diferencia de temporadas con el valor introducido y se crean
				// o borran las necesarias.
				ComprobacionesCampos.crearBorrarTemporadas(tablaTemporadas.getItems(),
						Integer.parseInt(tempTotales.getText()));
				// Comprobamos que si el estado es visto, se le da el mismo valor a las
				// temporadas vistas que las totales.
				if (estado.equals(Estados.VISTO)) {
					tempVistas.setText(tempTotales.getText());
				}else {
					tempVistas.setText("0");
				}

			} else {
				
				//Se muestra un mensaje de error.
				Alertas.alertaError(MensajesAlertas.T_INTRO_TT, MensajesAlertas.M_INTRO_TT);
				
				tempTotales.setText("1");

			}
			
		}else {
			
			tempTotales.setText("1");
			
		}

	}
	
	/**
	 * M�todo encargado de las acciones con la introducci�n de un valor en el campo de Temporadas Vistas.
	 * @param tempVistas
	 * @param tempTotales
	 * @return
	 */
	public static void introduccionTempV(TextField tempVistas, TextField tempTotales, TableView<Temporada> tablaTemporadas,
			ComboBox<Estados> estado) {
		
		if(!tempVistas.getText().isEmpty()) {
			
			//Se comprueba si el n�mero introducido es mayor que la cantidad de Temporada Totales.
			if (Integer.parseInt(tempVistas.getText()) > Integer.parseInt(tempTotales.getText())) {
				
				Alertas.alertaError(MensajesAlertas.T_INTRO_TV, MensajesAlertas.M_INTRO_TV);
				
				//Se le da el valor m�ximo posible a las temporadas vistas.
				tempVistas.setText("0");
			
			//Se comprueba si el valor de temporadas vistas es el mismo que totales.
			}else if(Integer.parseInt(tempVistas.getText()) == Integer.parseInt(tempTotales.getText())) {
				//Si los valores son iguales, se establece el estado en visto.
				estado.getSelectionModel().select(Estados.VISTO);
				
			}
			
		}else {
			
			tempVistas.setText("0");
			
		}
		
		autoRellenarCapV(tempVistas, tablaTemporadas);
		
	}
	
	/**
	 * M�todo encargado de las acciones al modificar celdas de los Capitulos Totales.
	 * @param e
	 * @param tablaTemporadas
	 * @param estado
	 */
	public static boolean modificarCapT (CellEditEvent<Temporada, Integer> e, TableView<Temporada> tablaTemporadas,
			TextField tempV, Estados estado) {
		
		boolean valorValido = false;
		
		//Se obtiene el nuevo valor de la celda.
		Integer nuevoValor = e.getNewValue();
		//Se comprueba que el valor no sea nulo, es decir, que la variable no est� vac�a.
		if (nuevoValor != null) {
			//Se obtiene el valor de toda la fila, es decir, la temporada de esas celdas.
			Temporada temporada = e.getRowValue();
			//Se le asigna el nuevo valor de las temporadas totales a la temporada obtenida antes.
			temporada.setCapitulosTotales(nuevoValor);
		
			//Se obtiene el indice que ocupa la temporada obtenida en la lista de temporadas.
			int indiceTempListaTabla = indiceTempTabla(tablaTemporadas, temporada);
			
			//Se actualiza la temporada modificada en la lista de temporadas de la tabla.
			tablaTemporadas.getItems().set(indiceTempListaTabla, temporada);
			//Se acualizan los capitulos vistos al m�ximo.
			rellenarCapV(tablaTemporadas,tempV, estado);

			valorValido = true;
		} else {
			//Se muestra un mensaje de alerta.
			Alertas.alertaError(MensajesAlertas.T_VALOR_INV_CAPS, MensajesAlertas.M_VALOR_INV_CAPS);
			//Se refresca los valores de la tabla.
			tablaTemporadas.refresh();
		}
		
		return valorValido;
		
	}
	
	/**
	 * M�todo encargado de las acciones al modificar celdas de los Capitulos Vistos.
	 * @param e
	 * @param tablaTemporadas
	 */
	public static boolean modificarCapV(CellEditEvent<Temporada, Integer> e, TableView<Temporada> tablaTemporadas, TextField tempV,
			ComboBox<Estados> estado) {
		
		boolean valorValido = false;
		
		//Se obtiene el nuevo valor de la celda.
		Integer nuevoValor = e.getNewValue();

		//Se comprueba que el valor no sea nulo, es decir, que la variable no est� vac�a.
		if (nuevoValor != null) {
			
			//Se obtiene el valor de toda la fila, es decir, la temporada de esas celdas.
			Temporada temporada = e.getRowValue();
			
			//Se comprueba que el valor sea menor o igual que los capitulos totales.
			if (temporada.getCapitulosTotales() >= nuevoValor) {
				//Se le asigna a la temporada obtenida el nuevo valor de los capitulos vistos.
				temporada.setCapitulosVistos(nuevoValor);
				
				//Se obtiene el indice que ocupa la temporada obtenida en la lista de temporadas.
				int indiceTempListaTabla = indiceTempTabla(tablaTemporadas, temporada);
				
				//Se actualiza la temporada modificada en la lista de temporadas de la tabla.
				tablaTemporadas.getItems().set(indiceTempListaTabla, temporada);
				
				sumarTempVConCapV(tablaTemporadas, tempV, estado);
				valorValido = true;
			} else {
				//Se muestra un mensaje de alerta.
				Alertas.alertaError(MensajesAlertas.T_INTRO_CAPV,MensajesAlertas.M_INTRO_CAPV);
				//Se refresca los valores de la tabla.
				tablaTemporadas.refresh();
			}
		} else {
			//Se muestra un mensaje de alerta.
			Alertas.alertaError(MensajesAlertas.T_VALOR_INV_CAPS, MensajesAlertas.M_VALOR_INV_CAPS);
			//Se refresca los valores de la tabla.
			tablaTemporadas.refresh();
		}
		
		return valorValido;
		
	}
	
	
	/**
	 * M�todo encargado de rellenar los Capitulos Vistos en las columnas que la cantidad de Capitulos Totales
	 * esten definidos, dependiendo de la cantidad de Temporadas Vistas recibida.
	 * @param tempVistas Campo Temporadas Vistas.
	 * @param tablaTemporadas Tabla de Temporadas.
	 */
	private static void autoRellenarCapV(TextField tempVistas, TableView<Temporada> tablaTemporadas) {
		//Se almacena las Temporadas pertenecientes a la tabla.
		ObservableList<Temporada> temporadas = tablaTemporadas.getItems();
		
		//Recorremos de la lista de temporadas la misma cantidad de temporadas que el valor del campo Temporadas Vistas.
		for(int i = 0; i < Integer.parseInt(tempVistas.getText()); i++) {
			//Se obtiene la temporada
			Temporada temporada = temporadas.get(i);
			/*
			 * Si el valor de los Capitulos Totales est� definido(es distinto a 0), se iguala el valor de los Capitulos Vistos
			 * a los totales.
			 */
			
			if(temporada.getCapitulosTotales() != 0) {
				
				
				temporada.setCapitulosVistos(temporada.getCapitulosTotales());
				//Se modifica la temporada de la lista.
				temporadas.set(i, temporada);
			}
			
		}
		//Se modifica los elementos de la tabla, almacenando la lista con los cambios.
		tablaTemporadas.setItems(temporadas);
	}
	
	/**
	 * M�todo encargado de buscar el �ndice en la lista de elementos de la tabla de temporadas 
	 * que corresponda con la temporada a buscar.
	 * @param tablaTemporadas
	 * @param temporada
	 * @return
	 */
	private static int indiceTempTabla(TableView<Temporada> tablaTemporadas, Temporada temporada) {
		
		int indiceTempListaTabla = -1;
		
		//Se recorre la lista de temporadas buscando la temporada con la misma id que la modificada.
		for(int i = 0; i < tablaTemporadas.getItems().size() && indiceTempListaTabla == -1; i++) {
			
			//Se comprueba que los id coincidan y se almacena el �ncide.
			if(tablaTemporadas.getItems().get(i).getId() == temporada.getId() ) {
				indiceTempListaTabla = i;
			}
			
		}
		
		return indiceTempListaTabla;
	}
	
	/**
	 * M�todo encargado de rellenar los Capitulos Vistos dependiendo del estado
	 * seleccionado.
	 * @param tablaTemporadas
	 * @param estado
	 */
	public static void rellenarCapV(TableView<Temporada> tablaTemporadas, TextField tempV, Estados estado) {

		//Se obtiene la lista de las temporadas de la tabla
		ObservableList<Temporada> temporadas = tablaTemporadas.getItems();

		//Se comprueba el estado seleccionado.
		if (estado.equals(Estados.VISTO)) {

			/*
			 * Se recorre todas las temporadas, se le cambia el valor igualandolo a los capitulos
			 * totales.
			 */
			for (int i = 0; i < temporadas.size(); i++) {
				Temporada temporada = temporadas.get(i);
				temporada.setCapitulosVistos(temporada.getCapitulosTotales());
				temporadas.set(i, temporada);
			}

		} else if (estado.equals(Estados.PENDIENTE)) {

			// Se recorre todas las temporadas, se le cambia el valor a 0.
			 
			for (int i = 0; i < temporadas.size(); i++) {
				Temporada temporada = temporadas.get(i);
				temporada.setCapitulosVistos(0);
				temporadas.set(i, temporada);
			}

		} else {
			
			if(!tempV.getText().isEmpty()) {
				
				for (int i = 0; i < Integer.parseInt(tempV.getText()); i++) {
					Temporada temporada = temporadas.get(i);
					temporada.setCapitulosVistos(temporada.getCapitulosTotales());
					temporadas.set(i, temporada);
				}
				
			}else {
				tempV.setText("0");
			}
			
		}
		
		//Se actualiza la lista de elementos.
		tablaTemporadas.setItems(temporadas);
	}
	
	/**
	 * M�todo encargado de crear y devolver un objeto de la clase correspondiente a cada tipo
	 * de Pieza Audiovisual.
	 * @param tipo
	 * @return
	 */
	public static PiezaAudiovisual crearClaseMedianteTipo(TiposPiezasAudiovisuales tipo) {
		
		PiezaAudiovisual elemento = null;
		
		if(TiposPiezasAudiovisuales.ANIMES.equals(tipo)) {
			
			elemento = new Anime();
			
		}else if(TiposPiezasAudiovisuales.SERIES.equals(tipo)) {
			
			elemento = new Serie();
			
		}else if(TiposPiezasAudiovisuales.PELICULAS.equals(tipo)) {
			
			elemento = new Pelicula();
			
		}else if(TiposPiezasAudiovisuales.PELICULAS_ANIMES.equals(tipo)) {
			
			elemento = new PeliculaAnime();
			
		}
		
		return elemento;
		
	}
	
	private static void sumarTempVConCapV(TableView<Temporada> tablaTemporadas, TextField tempV, ComboBox<Estados> estado) {
		
		ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
		temporadas.addAll(tablaTemporadas.getItems());
		int contadorTempV = 0;
		for(int i = 0; i < temporadas.size(); i++) {
			Temporada temporada = temporadas.get(i);
			if(temporada.getCapitulosVistos() == temporada.getCapitulosTotales()) {
				contadorTempV++;
			}
			
		}
		
		if(contadorTempV > Integer.parseInt(tempV.getText())) {
			tempV.setText(contadorTempV + "");
			FuncionesApoyoControladores.introduccionTempV(tempV, tempV, tablaTemporadas, estado);
		}
		
	}
	
	
	
	/**
	 * Verifica que el elemento obtenido por par�metro tenga todos los valores necesarios rellenos
	 * y con valores válidos.
	 * @param pieza
	 * @return
	 * @throws CampoInvalidoException 
	 * @throws ObraYaRegistradaException 
	 */
	public static void verificacionCampos(PiezaAudiovisual pieza, TiposPiezasAudiovisuales tipo) throws CampoInvalidoException, ObraYaRegistradaException {
		
		if(pieza.getTitulo().isEmpty()) {
			
			throw new CampoInvalidoException(MensajesAlertas.M_TITULO_VACIO);
			
		}
		
		if(pieza.getId() == 0) {
			
			comprobarTituloExistente(pieza.getTitulo(), tipo);
			
		}
		
		
		
		//TODO Añadir que si el título introducido ya se enctuentra que avise y no lo registre.
		
		//Comprueba si el objeto es Serializable.
		if(pieza instanceof Serializable) {
			
			Serializable serializable = (Serializable) pieza;
			
			if(serializable.getTemporadasTotales() < 1) {
				
				throw new CampoInvalidoException(MensajesAlertas.M_ERROR_TT);
				
			}
			
			if(serializable.getTemporadasVistas() > serializable.getTemporadasTotales()) {
				
				throw new CampoInvalidoException(MensajesAlertas.M_INTRO_TV);
				
			}
			
			comprobacionesCapitulos(serializable.getTemporadas(), serializable.getTemporadasVistas());
			
		}
		
		
	}
	
	public static void verificacionCampos (PiezaAudiovisual pieza) throws CampoInvalidoException {
		
		try {
			verificacionCampos(pieza, null);
		
		} catch (ObraYaRegistradaException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private static void comprobarTituloExistente(String titulo, TiposPiezasAudiovisuales tipo) throws ObraYaRegistradaException {
		
		HashSet<PiezaAudiovisual> lista = ObtencionDatosXML.obtenerListaElementosTipo(tipo);
		
		for(PiezaAudiovisual pieza : lista) {
			
			if(titulo.equals(pieza.getTitulo())) {
				
				throw new ObraYaRegistradaException(MensajesAlertas.M_TITULO_REPETIDO);
				
			}
			
		}
		
	}
	
	/**
	 * Verifica que los valores introducidos en los Capitulos sean válidos.
	 * @param temporadas
	 * @param cantidadTempV
	 * @return
	 * @throws CampoInvalidoException 
	 */
	private static void comprobacionesCapitulos(ArrayList<Temporada> temporadas, int cantidadTempV) throws CampoInvalidoException {
		
		//Recorremos todas las temporadas.
		for(int i = 0; i < temporadas.size(); i++) {
			//Obtenemos la temporada en la que estamos.
			Temporada temporada = temporadas.get(i);
			//Comprobamos si el número de temporada que estamos recorriendo es menor que el total de Temporadas Vistas.
			if(i < cantidadTempV) {
				
				/*
				 * Comprobamos si la temporada en la que estamos no tiene todos los Capitulos Vistos, pues si tenemos, por ejemplo,
				 * 2 temporadas Vistas y la segunda temporada que recorremos no tiene todos los capitulos vistos, no estaría la 
				 * temporada vista, por lo que habrá un error.
				 */
				if(temporada.getCapitulosVistos() != temporada.getCapitulosTotales()) {
					
					throw new CampoInvalidoException(MensajesAlertas.M_MENOS_CAPS);
					
				}
				
			//Comprobamos si el número de temporada que estamos recorriendo es mayor que el total de Temporadas Vistas.
			}else if(i > cantidadTempV) {
				
				/*
				 * Se comprueba que no haya los mismos capitulos vistos que totales, cuando el número de la temporada
				 * que estamos reccorriendo es mayor que el total de temporadas vistas, pues en este caso significaría que 
				 * hay más temporadas vistas de las que hemos indicado.
				 */
				if(temporada.getCapitulosVistos() == temporada.getCapitulosTotales()) {
					
					throw new CampoInvalidoException(MensajesAlertas.M_MAS_CAPS);
					
				}
				
			}
			
			// Se comprueba que no haya menos de un capitulo total.
			if(temporada.getCapitulosTotales() < 1) {
				
				throw new CampoInvalidoException(MensajesAlertas.M_MIN_CAPST);
				
			}
			
			//Se comprueba que no haya más capitulos vistos que totales.
			if(temporada.getCapitulosVistos() > temporada.getCapitulosTotales()) {
				
				throw new CampoInvalidoException(MensajesAlertas.M_MAX_CAPSV);
				
			}
		}
		
	}
	
	
	/**
	 * Almacena los valores de los campos en su clase correspondiente para preparar los datos para 
	 * registrarlo en el base de datos.
	 * @param tipos
	 * @param titulo
	 * @param estado
	 * @param sinopsis
	 * @param tempT
	 * @param tempV
	 * @param tablaTemp 
	 */
	public static PiezaAudiovisual crearPiezaRegistro(ComboBox<TiposPiezasAudiovisuales> tipos,TextInputControl titulo, ComboBox<Estados> estado, TextArea sinopsis, TextField tempT,
			TextField tempV, TableView<Temporada> tablaTemp) {
		
		PiezaAudiovisual pieza = FuncionesApoyoControladores.crearClaseMedianteTipo(tipos.getSelectionModel().getSelectedItem());
		
		pieza.setTitulo(titulo.getText().toUpperCase());
		pieza.setEstado(estado.getSelectionModel().getSelectedItem());
		pieza.setSinopsis(sinopsis.getText());
	
		if(pieza instanceof Serializable) {
			
			Serializable serializable = (Serializable) pieza;
			
			if(!tempT.getText().isEmpty() && !tempV.getText().isEmpty()) {
				
				if(!tempT.getText().isEmpty()) {
					serializable.setTemporadasTotales(Integer.parseInt(tempT.getText()));
				}
				if(!tempV.getText().isEmpty()) {
					serializable.setTemporadasVistas(Integer.parseInt(tempV.getText()));
				}
				
				ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
				temporadas.addAll(tablaTemp.getItems());
				serializable.setTemporadas(temporadas);
			}
			
		}
		
		return pieza;
		
	}
	
	/**
	 * Método para eliminar el foco de un campo.
	 */
	public static void quitarFoco(Pane raiz) {
		raiz.requestFocus();
	}
	
}
