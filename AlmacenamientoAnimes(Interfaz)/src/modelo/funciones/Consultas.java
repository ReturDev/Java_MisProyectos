package modelo.funciones;

import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.clases.*;
import modelo.converters.EstadosConverter;
import modelo.enums.*;
import modelo.funcionesXML.ObtencionDatosXML;
import modelo.listas.ListasObservables;


public class Consultas {

	//Variables que almacenan los datos introducidos por el usuario en los campos de la interfaz.
	private static TiposPiezasAudiovisuales tipo;
	private static HashSet<PiezaAudiovisual> listaElementosBase;
	private static Estados estado;
	private static String titulo;
	
	
	/**
	 * Metodo que maneja el evento que se activa al seleccionar en el ComboBox un
	 * elemento en el que se elige el tipo de Pieza Audiovisual que se buscará.
	 * @param comboTiposConsulta
	 * @param tituloConsulta
	 * @param comboEstadosConsulta
	 * @param listaElementosObtenidos
	 */
	public static void elegirTipo(ComboBox<TiposPiezasAudiovisuales> comboTiposConsulta, TextField tituloConsulta, ComboBox<Estados> comboEstadosConsulta,
			ListView<PiezaAudiovisual> listaElementosObtenidos) {
			if(!ComprobacionesCampos.tipoSerializable(comboTiposConsulta.getSelectionModel().getSelectedItem())) {
				comboEstadosConsulta.getItems().remove(Estados.SIGUIENDO);
			}else {
				if(!comboEstadosConsulta.getItems().contains(Estados.SIGUIENDO)) {
					comboEstadosConsulta.getItems().add(1, Estados.SIGUIENDO);
				}
			}
		
		
			// Activamos los campos para que sea posible rellenarlos.
			tituloConsulta.setDisable(false);
			comboEstadosConsulta.setDisable(false);
			// Borramos los datos de los campos si ya estaban activos.
			tituloConsulta.setText(null);
			// Colocamos el combobox de estados por defecto a por defecto.
			comboEstadosConsulta.valueProperty().set(null);
			//Borramos la variable que almacena los estados.
			Consultas.setEstado(null);
					
			// Almacenamos el tipo elegido en la variable que lo almacenar�.
			Consultas.setTipo(comboTiposConsulta.getSelectionModel().getSelectedItem());
			// Hacemos que se almacene tambi�n la lista de los elementos del tipo elegido.
			Consultas.setListaElementosBase();
			// Actualizamos la ListView con los nuevos elementos.
			Consultas.actualizarLista(listaElementosObtenidos, ListasObservables.listaPiezasTipos());
				
	}
	
	/**
	 * Metodo que maneja el evento que se inicia al hacer un cambio en el ComboBox
	 * para elegir el tipo de de estado en el que se encuentra la Pieza Audiovisual.
	 * @param comboEstadosConsulta
	 * @param listaElementosObtenidos
	 * @param tituloConsulta
	 */
	public static void eleccionEstado(ComboBox<Estados> comboEstadosConsulta, ListView<PiezaAudiovisual> listaElementosObtenidos,
			TextField tituloConsulta) {

		// Establecemos el estado eleg�do a la variable que lo almacenará.
		Consultas.setEstado(comboEstadosConsulta.getSelectionModel().getSelectedItem());
		// Actualizamos la ListView con los nuevos elementos.
		Consultas.actualizarLista(listaElementosObtenidos, ListasObservables.listaBusquedaEstado());
		//Reseteamos el campo de texto.
		tituloConsulta.setText(null);

	}
	
	/**
	 * Metodo encargado de obtener el texto introducido en el TextField para luego
	 * filtrar los elementos que contengan esos titulos.
	 * @param tituloConsulta
	 * @param listaElementosObtenidos
	 */
	public static void comprobacionTitulo(TextField tituloConsulta, ListView<PiezaAudiovisual> listaElementosObtenidos) {
		
		//Se obtiene el texto del titulo.
		String titulo = tituloConsulta.getText();
		//Se asigna a la variable para guardar el titulo.
		Consultas.setTitulo(titulo);
		//Se actualiza la ListView con los elementos flitrados.
		Consultas.actualizarLista(listaElementosObtenidos, ListasObservables.listaBusquedaTitulo());
		
	}
	
	
	/**
	 * Metodo encargado de imprimir los campos correspondientes.
	 * @param listaElementosObtenidos
	 * @param botonModificador
	 * @param titulo
	 * @param id
	 * @param estado
	 * @param sinopsis
	 * @param tempT
	 * @param tempV
	 * @param tablaTemporadas
	 * @param colTemporadas
	 * @param colCapT
	 * @param colCapV
	 */
	public static void obtenerElementoLista(ListView<PiezaAudiovisual> listaElementosObtenidos, Button botonModificador,
			TextField titulo, TextField id,TextField estado, TextArea sinopsis, TextField tempT, TextField tempV,
			TableView<Temporada> tablaTemporadas, TableColumn<Temporada, Integer> colTemporadas,
			TableColumn<Temporada, Integer> colCapT, TableColumn<Temporada, Integer> colCapV) {
		
		// Obtenemos el elemento seleccionado en la lista.
				PiezaAudiovisual pieza = listaElementosObtenidos.getSelectionModel().getSelectedItem();
				// Comprobamos que el objeto no sea null, es decir, que haya alg�n elemento
				// seleccionado.
				if (pieza != null) {
					mostrarElementoSeleccionado(pieza, titulo, id, estado, sinopsis, tempT, tempV, tablaTemporadas, colTemporadas, colCapT, colCapV);
					botonModificador.setDisable(false);
				}
		
	}
	
	
	/**
	 * M�todo que busca el elemento por el titulo almacenado en las variables.
	 * @return Devuelve una lista de los elementos.
	 */
	public static HashSet<PiezaAudiovisual> busquedaPorNombre(){
		//Se crea la variable que luego se devolver�.
		HashSet<PiezaAudiovisual> lista = new HashSet<PiezaAudiovisual>();
		/*
		 * Se añade todos los elementos que corresponden con el tipo elegido y que hay que
		 * recoger.
		 */
		lista.addAll(listaElementosBase);
		/*
		 * Se comprueba hay alg�n estado guardado, si lo hay se filtrar� primero por el estado
		 * llamando al m�todo.
		 */
		
		if(estado != null) {
			lista = busquedaPorEstado();
		}
		/*
		 * Se recorre la lista y se elimina too elemento que no contengan el t�tulo introducido
		 * por el usuario.
		 */
		HashSet<PiezaAudiovisual> listaFiltrada = new HashSet<PiezaAudiovisual>();
		for(PiezaAudiovisual elemento : lista) {
			if(elemento.getTitulo().contains(titulo.toUpperCase())) {
				listaFiltrada.add(elemento);
			}
		}
		
		return listaFiltrada;
		
	}
	
	/**
	 * M�todo encargado de obtener los elementos que tienen el mismo estado que el alamcenado.
	 * @return Devuelve una lista con los elementos que tengan el mismo estado.
	 */
	public static HashSet<PiezaAudiovisual> busquedaPorEstado(){
		HashSet<PiezaAudiovisual> lista = new HashSet<PiezaAudiovisual>();
		/*
		 * Recorre la lista que se almacen� en la variable local con los elementos que correponden
		 * con el tipo.
		 */
		for(PiezaAudiovisual pieza : listaElementosBase) {
			if(pieza.getEstado().equals(estado)) {
				lista.add(pieza);
			}
		}
		
		return lista;
	}
	
	/**
	 * M�todo encargado de dar el valor a los campos que mostraran los datos del
	 * elemento seleccionado, y si el elemento lo necesita, rellenara tambi�n la
	 * tabla.
	 * @param pieza
	 * @param titulo
	 * @param id
	 * @param estado
	 * @param sinopsis
	 * @param tempT
	 * @param tempV
	 * @param tablaTemporadas
	 * @param colTemporadas
	 * @param colCapT
	 * @param colCapV
	 */
	private static void mostrarElementoSeleccionado(PiezaAudiovisual pieza, TextField titulo, TextField id,
			TextField estado, TextArea sinopsis, TextField tempT, TextField tempV,
			TableView<Temporada> tablaTemporadas, TableColumn<Temporada, Integer> colTemporadas,
			TableColumn<Temporada, Integer> colCapT, TableColumn<Temporada, Integer> colCapV) {
		
		// Le da el valor al t�tulo.
		titulo.setText(pieza.getTitulo());
		// Le da el valor a la id.
		id.setText(pieza.getId() + "");
		// Le da el valor al estado.
		estado.setText(new EstadosConverter().toString(pieza.getEstado()));
		// Se le da valor a la sinopsis.
		sinopsis.setText(pieza.getSinopsis());
		// Se comprueba si el elemento es serializable.
		if (pieza instanceof Serializable) {
			// Casteamos el objeto a serializable para poder obtener los datos que
			// necesitamos.
			Serializable serializable = (Serializable) pieza;
			// Da el valor al campo que muestra las temporadas totales.
			tempT.setText(serializable.getTemporadasTotales() + "");
			// Da el valor al campo de las temporadas vistas.
			tempV.setText(serializable.getTemporadasVistas() + "");
			// A�ade las temporadas que tiene el elemento a la tabla, para que esta pueda
			// mostrarlas.
			tablaTemporadas.setItems(ListasObservables.listaTemporadas(serializable.getTemporadas()));
			/*
			 * Se le asigna a las celdas de las columnas las propiedades que tienen que
			 * imprimir, pasandole el nombre de la variable que almacena el valor a obtener.
			 */
			colTemporadas.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("id"));
			colCapT.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosTotales"));
			colCapV.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosVistos"));

		} else {
			/*
			 * En el caso de que no fuera serializable, se establecen todos los campos
			 * serializables y la tabla a null, puesto que si anteriormente se mostr� un
			 * elemento serializable, quedar�an los campos mostrados y se mezclarian con los
			 * siguientes datos que se introduzcan.
			 */
			tablaTemporadas.setItems(null);
			tempV.setText(null);
			tempT.setText(null);
		}
	}
	
	
	/**
	 * M�todo para actualizar la lista con los elementos a mostrar.
	 * @param listaInterfaz La lista que se quiere actualizar.
	 * @param elementos Los elementos con los que actualizar la lista.
	 */
	public static void actualizarLista(ListView<PiezaAudiovisual> listaInterfaz,
			ObservableList<PiezaAudiovisual> elementos) {
		
		//Comprobamos si la lista de los elementos recibidos no est� vac�a.
		if(!elementos.isEmpty()) {
			
			listaInterfaz.setItems(elementos);
		
		}else {
			/*
			 * Si est� vac�a se le da el valor nulo para que se borren los elementos
			 * anteriores que de hubieran mostrado.
			 */
			
			listaInterfaz.setItems(null);
			
		}
		
	}
	
	

	public static TiposPiezasAudiovisuales getTipo() {
		return tipo;
	}


	public static void setTipo(TiposPiezasAudiovisuales tipo) {
		Consultas.tipo = tipo;
	}


	public static Estados getEstado() {
		return estado;
	}


	public static void setEstado(Estados estado) {
		Consultas.estado = estado;
	}


	public static String getTitulo() {
		return titulo;
	}


	public static void setTitulo(String titulo) {
		Consultas.titulo = titulo;
	}

	
	public static HashSet<PiezaAudiovisual> getListaElementosBase() {
		return listaElementosBase;
	}


	public static void setListaElementosBase() {
		Consultas.listaElementosBase = ObtencionDatosXML.obtenerListaElementosTipo(tipo);
	}
	
}
