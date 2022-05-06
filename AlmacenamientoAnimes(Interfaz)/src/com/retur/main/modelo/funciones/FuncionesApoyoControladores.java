package com.retur.main.modelo.funciones;

import java.util.ArrayList;
import java.util.HashSet;

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

	
	
	/**
	 * Comprueba los valores introducidos en el campo de Temporadas Totales y crea o borra la cantidad de temporadas dependiendo 
	 * del la cantidad de Temporadas Totales. Si se deja el campo vacío o se , se asignará 1 
	 * @param tempTotales Campo de Temporadas Totales.
	 * @param tempVistas Campo de Temporadas Vistas.
	 * @param tablaTemporadas Tabla que almacena las temporadas.
	 * @param estado Estado en el que se encuentra la obra.
	 * @throws CampoInvalidoException 
	 */
	public static void introduccionTempT(TextField tempTotales, TextField tempVistas, TableView<Temporada> tablaTemporadas,
			Estados estado) throws CampoInvalidoException {
		
		if(Integer.parseInt(tempTotales.getText()) == 0) {
			
			tempTotales.setText("1");
			
			throw new CampoInvalidoException(MensajesAlertas.M_INTRO_TT);
			
		}

		/*
		 *  Se comprueba la diferencia de temporadas con el valor introducido y se crean
		 *  o borran las necesarias.
		 */
		ComprobacionesCampos.crearBorrarTemporadas(tablaTemporadas.getItems(),
				Integer.parseInt(tempTotales.getText()));
		/*
		 * Comprobamos que si el estado es visto, se le da el mismo valor a las
		 * temporadas vistas que las totales.
		 */
		if (estado.equals(Estados.VISTO)) {
			
			tempVistas.setText(tempTotales.getText());
			
		}else {
			
			tempVistas.setText("0");
			
		}

		

	}
	
	/**
	 * Comprueba los valores introducidos en el campo de Temporadas Vistas y, comprobando que no haya más
	 * Temporadas Vistas que Totales, igualará los capitulos vistos a los totales en la cantidad de temporadas
	 * que coincidan con el número de temporadas vistas. En el caso que las temporadas vistas sean las mismas que las
	 * temporadas totales, se cambiará el {@link Estado} a VISTO.
	 * @param tempVistas Campo Temporadas Vistas.
	 * @param tempTotales Campo Temporadas Totales.
	 * @param tablaTemporadas Tabla que almacena las temporadas.
	 * @param estado Estado en el que se encuentra la obra.
	 * @throws CampoInvalidoException 
	 */
	public static void introduccionTempV(TextField tempVistas, TextField tempTotales, TableView<Temporada> tablaTemporadas,
			ComboBox<Estados> estado) throws CampoInvalidoException {
		
		if(Integer.parseInt(tempVistas.getText()) > Integer.parseInt(tempTotales.getText())) {
			
			//Se coloca el valor de las temporadas a 0 si el número introducido es mayor que las temporadas totales.
			tempVistas.setText("0");
			
			throw new CampoInvalidoException(MensajesAlertas.M_INTRO_TV);
			
		}
		
		//Se comprueba si el valor de temporadas vistas es el mismo que totales.
		if(Integer.parseInt(tempVistas.getText()) == Integer.parseInt(tempTotales.getText())) {
			//Si los valores son iguales, se establece el estado en visto.
			estado.getSelectionModel().select(Estados.VISTO);
			
		}
		
		autoRellenarCapV(tempVistas, tablaTemporadas);
		
	}
	
	/**
	 * Verifica que el valor introducido en la celda de Capitulos Totales sea un número y modifica si fuera necesario
	 * los Capitulos Vistos de esa temporada. Para más información sobre el método que llama para la modificación de capitulos
	 * consulta el método rellenarCapV.
	 * @param event Evento que se dispara al modificar la celda de Capitulos Totales.
	 * @param tablaTemporadas Tabla que almacena las temporadas.
	 * @param tempV Campo de Temporadas Vistas.
	 * @param estado Estado en el que se encuentra la obra.
	 * @throws CampoInvalidoException 
	 */
	public static void modificarCapT (CellEditEvent<Temporada, Integer> event, TableView<Temporada> tablaTemporadas,
			TextField tempV, Estados estado) throws CampoInvalidoException {
		
		//Se obtiene el nuevo valor de la celda.
		Integer nuevoValor = event.getNewValue();
		
		//Si el nuevo valor es nulo, es decir, no es un número, lanzará una excepción.
		if(nuevoValor == null) {
			
			//Se refresca los valores de la tabla para que no aparezca el valor introducido.
			tablaTemporadas.refresh();
			
			throw new CampoInvalidoException(MensajesAlertas.M_VALOR_INV_CAPS);
			
		}
		
		//Se obtiene el valor de toda la fila, es decir, la temporada de esas celdas.
		Temporada temporada = event.getRowValue();
		//Se le asigna el nuevo valor de las temporadas totales a la temporada obtenida antes.
		temporada.setCapitulosTotales(nuevoValor);
	
		//Se obtiene el indice que ocupa la temporada obtenida en la lista de temporadas.
		int indiceTempListaTabla = indiceTempTabla(tablaTemporadas, temporada);
		
		//Se actualiza la temporada modificada en la lista de temporadas de la tabla.
		tablaTemporadas.getItems().set(indiceTempListaTabla, temporada);
		//Se acualizan los capitulos vistos dependiendo del estado en el que se encuentre la obra.
		rellenarCapV(tablaTemporadas,tempV, estado);

		
	}
	
	/**
	 * Verifica que el valor introducido en la celda de Capitulos Vistos sea un número y modifica si fuera necesario
	 * los Capitulos Vistos de esa temporada.
	 * @param event Evento que se dispara al modificar la celda de Capitulos Totales.
	 * @param tablaTemporadas Tabla que almacena las temporadas.
	 * @param tempV Campo de Temporadas Vistas.
	 * @param estado Estado en el que se encuentra la obra.
	 * @throws CampoInvalidoException 
	 */
	public static void modificarCapV(CellEditEvent<Temporada, Integer> e, TableView<Temporada> tablaTemporadas, TextField tempV,
			TextField tempT, ComboBox<Estados> estado) throws CampoInvalidoException {
	
		//Se obtiene el nuevo valor de la celda.
		Integer nuevoValor = e.getNewValue();
		
		//Si el nuevo valor es nulo, es decir, no es un número, lanzará una excepción.
		if(nuevoValor == null) {
			
			//Se refresca los valores de la tabla para que no aparezca el valor introducido.
			tablaTemporadas.refresh();
			
			throw new CampoInvalidoException(MensajesAlertas.M_VALOR_INV_CAPS);
			
		}
		
		//Se obtiene el valor de toda la fila, es decir, la temporada de esas celdas.
		Temporada temporada = e.getRowValue();
		
		//Si el nuevo valor es mayor que el número de capitulos totales, lanzará una excepción.
		if (temporada.getCapitulosTotales() < nuevoValor) {
			
			//Se refresca los valores de la tabla para que no aparezca el valor introducido.
			tablaTemporadas.refresh();
			
			throw new CampoInvalidoException(MensajesAlertas.M_INTRO_CAPV);
			
		}
		
		//Se le asigna a la temporada obtenida el nuevo valor de los capitulos vistos.
		temporada.setCapitulosVistos(nuevoValor);
		
		//Se obtiene el indice que ocupa la temporada obtenida en la lista de temporadas.
		int indiceTempListaTabla = indiceTempTabla(tablaTemporadas, temporada);
		
		//Se actualiza la temporada modificada en la lista de temporadas de la tabla.
		tablaTemporadas.getItems().set(indiceTempListaTabla, temporada);
		
		sumarTempVConCapV(tablaTemporadas, tempV, tempT, estado);

	}
	
	
	/**
	 * Método encargado de rellenar los Capitulos Vistos en todas las temporadas en las que los Capitulos Totales
	 * esten definidos, teniendo en cuenta la cantidad de Temporadas Vistas indicadas en el campo.
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
			 * Si el valor de los Capitulos Totales está definido(es distinto a 0), se iguala el valor de los Capitulos Vistos
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
	 * Busca el índice en la lista de elementos de la tabla de temporadas que corresponda 
	 * con la temporada que se quiere encontrar.
	 * @param tablaTemporadas
	 * @param temporada
	 * @return devuelve un entero.
	 */
	private static int indiceTempTabla(TableView<Temporada> tablaTemporadas, Temporada temporada) {
		
		int indiceTempListaTabla = -1;
		
		//Se recorre la lista de temporadas buscando la temporada con la misma id que la modificada.
		for(int i = 0; i < tablaTemporadas.getItems().size() && indiceTempListaTabla == -1; i++) {
			
			//Se comprueba que los id coincidan y se almacena el índice.
			if(tablaTemporadas.getItems().get(i).getId() == temporada.getId() ) {
				
				indiceTempListaTabla = i;
				
			}
			
		}
		
		return indiceTempListaTabla;
	}
	
	/**
	 * Establece la cantidad de Capitulos Vistos de cada temporada dependiendo del estado seleccionado y en caso de 
	 * que el Estado sea siguiendo, trendrá en cuenta la cantidad de Temporadas Vistas.
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
			
			autoRellenarCapV(tempV, tablaTemporadas);
			
		}
		
		//Se actualiza la lista de elementos.
		tablaTemporadas.setItems(temporadas);
	}
	
	/**
	 * Crea la clase necesaria dependiendo del tipo de {@link TiposPiezasAudiovisuales} recibido.
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
	
	/**
	 * Recorre todas las temporadas contando todas las que tengan los mismos Capitulos Vistos que Totales,
	 * si son más que las indicadas en el campo de Temporadas Vistas, se modifica el campo. Si llega a haber
	 * la misma cantidad de Temporadas Vistas que Totales, se cambiará también el estado a VISTO. 
	 * @param tablaTemporadas
	 * @param tempV
	 * @param temoT
	 * @param estado
	 * @throws CampoInvalidoException 
	 */
	private static void sumarTempVConCapV(TableView<Temporada> tablaTemporadas, TextField tempV, TextField tempT,
			ComboBox<Estados> estado) throws CampoInvalidoException {
		
		//Se almacenan todas las temporadas para recorrerlas.
		ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
		temporadas.addAll(tablaTemporadas.getItems());
		
		
		int contadorTempV = 0;
		
		
		for(int i = 0; i < temporadas.size(); i++) {
			
			Temporada temporada = temporadas.get(i);
			
			//Si los Capitulos Vistos son los mismos que los Totales si estos no son 0, se suma el contador.
			if(temporada.getCapitulosVistos() == temporada.getCapitulosTotales() && temporada.getCapitulosTotales() != 0) {
				
				contadorTempV++;
				
			}
			
		}
		
		/*
		 * Si hay más temporadas contadas que las del campo, se cambia el valor del campo.
		 */
		if(contadorTempV > Integer.parseInt(tempV.getText())) {
			
			
			tempV.setText(contadorTempV + "");
			FuncionesApoyoControladores.introduccionTempV(tempV, tempT, tablaTemporadas, estado);
			
		}
		
	}
	
	
	
	/**
	 * Verifica que el elemento obtenido por parámetro tenga todos los valores requeridos rellenos con valores
	 * válidos.
	 * @param pieza
	 * @param tipo
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
	
	/**
	 * Hace lo mismo que el método con el mismo nombre, pero recibiendo solo un parámetro y controlando
	 * la excepcion ObraYaRegistradaException.
	 * @param pieza
	 * @throws CampoInvalidoException
	 */
	public static void verificacionCampos (PiezaAudiovisual pieza) throws CampoInvalidoException {
		
		try {
			
			verificacionCampos(pieza, null);
		
		} catch (ObraYaRegistradaException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Comprueba si el título recibido se encuentra ya en la base de datos, si es así lanzará una excepción.
	 * @param titulo Titulo de la obra
	 * @param tipo Tipo de la obra para la sección en la que buscar.
	 * @throws ObraYaRegistradaException
	 */
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
