package com.retur.main.modelo.funciones;

import java.util.HashSet;

import com.retur.main.controlador.ControladorPrincipal;
import com.retur.main.modelo.convertidores.EstadosConverter;
import com.retur.main.modelo.elementos.*;
import com.retur.main.modelo.enums.*;
import com.retur.main.modelo.funciones.xml.ObtencionDatosXML;
import com.retur.main.modelo.listas.ListasObservables;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Clase funcional para apoyar al {@link ControladorPrincipal} con todo lo relacionado con consultas.
 * @author Sergio
 *
 */
public class Consultas {

	/**
	 * Almacena el tipo de Pieza Audiovisual seleccionado en este momento.
	 */
	private static TiposPiezasAudiovisuales tipo;
	/**
	 * Almacena la lista de todos los elementos que corresponde al tipo seleccionado.
	 */
	private static HashSet<PiezaAudiovisual> listaElementosBase;
	/**
	 * Almacena el estado seleccionado en este momento.
	 */
	private static Estados estado;
	/**
	 * Almacena el titulo por el que se filtra en este momento.
	 */
	private static String titulo;
	
	
	/**
	 * A partir del tipo seleccionado en el ComboBox, se obtendran los elementos de la base de datos y
	 * se almacenará en la lista de elementos base. Se cambiarán los valores de los atributos de estado  y titulo
	 * estableciendolo a nulo.
	 * @param comboTiposConsulta
	 * @param comboEstadosConsulta
	 * @param listaElementosObtenidos
	 */
	public static void elegirTipo(ComboBox<TiposPiezasAudiovisuales> comboTiposConsulta, ComboBox<Estados> comboEstadosConsulta,
			ListView<PiezaAudiovisual> listaElementosObtenidos) {
		
			if(!ComprobacionesCampos.tipoSerializable(comboTiposConsulta.getSelectionModel().getSelectedItem())) {
				
				comboEstadosConsulta.getItems().remove(Estados.SIGUIENDO);
				
			}else {
				
				if(!comboEstadosConsulta.getItems().contains(Estados.SIGUIENDO)) {
					
					comboEstadosConsulta.getItems().add(1, Estados.SIGUIENDO);
					
				}
			}
		
		
			//Borramos la variable que almacena los estados.
			Consultas.setEstado(null);
			//Borramos la variable que almacena el título.
			Consultas.setTitulo(null);
			// Almacenamos el tipo elegido en la variable que lo almacenará.
			Consultas.setTipo(comboTiposConsulta.getSelectionModel().getSelectedItem());
			// Hacemos que se almacene también la lista de los elementos del tipo elegido.
			Consultas.setListaElementosBase();
			// Actualizamos la ListView con los nuevos elementos.
			Consultas.actualizarLista(listaElementosObtenidos, ListasObservables.listaPiezasTipos());
				
	}
	
	/**
	 * Almacena el estado seleccionado en el ComboBox y actualiza la Listview con los elementos filtrados.
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
	 * Busca el elemento por el nombre almacenado en el atributo.
	 * @return Devuelve una lista de los elementos.
	 */
	public static HashSet<PiezaAudiovisual> busquedaPorNombre(){
		
		//Se crea la variable que luego se devolverá.
		HashSet<PiezaAudiovisual> lista = new HashSet<PiezaAudiovisual>();
		/*
		 * Se añade todos los elementos que corresponden con el tipo elegido y que hay que
		 * recoger.
		 */
		lista.addAll(listaElementosBase);
		/*
		 * Se comprueba si hay algún estado guardado, si lo hay se filtrará primero por el estado
		 * llamando al método.
		 */
		
		if(estado != null) {
			
			lista = busquedaPorEstado();
			
		}
		
		/*
		 * Se recorre la lista y se elimina too elemento que no contengan el título introducido
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
	 * Obtiene los elementos de la lista de elementos base que tienen el mismo estado que el almacenado.
	 * @return Devuelve una lista con los elementos que tengan el mismo estado.
	 */
	public static HashSet<PiezaAudiovisual> busquedaPorEstado(){
		HashSet<PiezaAudiovisual> lista = new HashSet<PiezaAudiovisual>();
		/*
		 * Recorre la lista que se almacena en la variable local con los elementos que correponden
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
	 * Mostrará los datos del elemento seleccionado en la zona de visualización rellenando los campos 
	 * necesarios dependiendo si el elemento es {@link Serializable}.
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
	public static void mostrarElementoSeleccionado(PiezaAudiovisual pieza, TextField titulo, TextField id,
			TextField estado, TextArea sinopsis, TextField tempT, TextField tempV,
			TableView<Temporada> tablaTemporadas, TableColumn<Temporada, Integer> colTemporadas,
			TableColumn<Temporada, Integer> colCapT, TableColumn<Temporada, Integer> colCapV) {
		
		// Le da el valor al título.
		titulo.setText(pieza.getTitulo());
		// Le da el valor a la id.
		id.setText(pieza.getId() + "");
		// Le da el valor al estado.
		estado.setText(new EstadosConverter().toString(pieza.getEstado()));
		// Se le da valor a la sinopsis.
		sinopsis.setText(pieza.getSinopsis());
		// Se comprueba si el elemento es serializable.
		if (pieza instanceof Serializable) {
			
			// Casteamos el objeto a serializable para poder obtener los datos que necesitamos.
			Serializable serializable = (Serializable) pieza;
			// Da el valor al campo que muestra las temporadas totales.
			tempT.setText(serializable.getTemporadasTotales() + "");
			// Da el valor al campo de las temporadas vistas.
			tempV.setText(serializable.getTemporadasVistas() + "");
			// Añade las temporadas que tiene el elemento a la tabla, para que esta pueda mostrarlas.
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
			 * En el caso de que no fuera serializable, se establecen todos los campos serializables 
			 * y la tabla a nulo, puesto que si anteriormente se mostró un elemento serializable,
			 * quedarían los campos mostrados y se mezclarian con los siguientes datos que se introduzcan.
			 */
			tablaTemporadas.setItems(null);
			tempV.setText(null);
			tempT.setText(null);
			
		}
		
		//Se refresca la tabla para que todo se muestre adecuadamente.
		tablaTemporadas.refresh();
		
	}
	
	
	/**
	 * Actualiza la Listview con los elementos a mostrar.
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
			 * Si está vacía se le da el valor nulo para que se borren los elementos
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


	/**
	 * Establece el valor de la lista de elementos a través del método obtenerListaElementosTipo
	 * de la clase {@link ObtencionDatosXML}.
	 */
	public static void setListaElementosBase() {
		
		Consultas.listaElementosBase = ObtencionDatosXML.obtenerListaElementosTipo(tipo);
		
	}
	
}
