package com.retur.main.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.transform.TransformerException;

import com.retur.main.modelo.alertas.Alertas;
import com.retur.main.modelo.alertas.texto.MensajesAlertas;
import com.retur.main.modelo.convertidores.*;
import com.retur.main.modelo.elementos.*;
import com.retur.main.modelo.enums.Estados;
import com.retur.main.modelo.envio.datos.EnvioDatos;
import com.retur.main.modelo.excepciones.CampoInvalidoException;
import com.retur.main.modelo.funciones.*;
import com.retur.main.modelo.funciones.xml.RegistroDatosXML;
import com.retur.main.modelo.listas.ListasObservables;


import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controla los valores de los campos y los eventos de la Vista de Modificación.
 * @author Sergio
 *
 */
public class ControladorModificacion implements Initializable {

	
	@FXML
	private AnchorPane raiz;
	@FXML
	private TextField titulo;
	@FXML
	private TextField id;
	@FXML
	private TextField tempTotales;
	@FXML
	private TextField tempVistas;
	@FXML
	private TextArea sinopsis;
	@FXML
	private ComboBox<Estados> estado;
	@FXML
	private TableView<Temporada> tablaTemporadas;
	@FXML
	private TableColumn<Temporada, Integer> columnaTemporada;
	@FXML
	private TableColumn<Temporada, Integer> columnaCapT;
	@FXML
	private TableColumn<Temporada, Integer> columnaCapV;
	@FXML
	private Button modificar;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Se obtiene los datos de la ventana principal de los datos que se quieren modificar.
		PiezaAudiovisual pieza = EnvioDatos.getInstance().getDatosTransferencia();

		asignarValoresGenerales(pieza);
		//Comprueba que el elemento es Serializable para rellenar los campos, en caso de no serlo se bloquearán los campos oportunos.
		if (pieza instanceof Serializable) {

			asignarValoresSerializables(pieza);

			
		} else {

			//Se bloquean los campos que pertenecen a serializables.
			tempTotales.setDisable(true);
			tempVistas.setDisable(true);
			tablaTemporadas.setDisable(true);
			
		}

	}
	
	
	/**
	 * Asigna valores y los listeners necesarios a los campos generales que tienen todos los elementos.
	 * @param pieza
	 */
	private void asignarValoresGenerales(PiezaAudiovisual pieza) {
		
		//Se le da los valores a mostrar al ComboBox.
		estado.setItems(ListasObservables.listaEstados());
		estado.setConverter(new EstadosConverter());
		
		/*
		 * Se da los valores a cada elemento de la interfaz con los datos del elemento a modificar para mostrar
		 * al usuario.
		 */
		titulo.setText(pieza.getTitulo());
		
		//Se añade un evento para cuando el campo título pierda el foco.
		ComprobacionesCampos.listenerCambioFocoTitulo(titulo, pieza.getTitulo());
		
		
		id.setText(pieza.getId() + "");
		estado.getSelectionModel().select(pieza.getEstado());
		sinopsis.setText(pieza.getSinopsis());
		
	}
	
	/**
	 * Asigna valores y los listeners necesarios a los campos que pertenecen solo a los elementos serializables.
	 * @param pieza
	 */
	private void asignarValoresSerializables(PiezaAudiovisual pieza) {
		
		Serializable serializable = (Serializable) pieza;
		
		tempVistas.setText(serializable.getTemporadasVistas() + "");
		
		//Añadimos un evento para cuando el campo Temporadas Vistas pierda el foco.
		ComprobacionesCampos.listenerCambioFocoTempVistas(tempVistas);
		
		tempTotales.setText(serializable.getTemporadasTotales() + "");
		
		//Añadimos un evento cuando el campo Temporadas Totales pierda el foco.
		ComprobacionesCampos.listenerCambioFocoTempTotales(tempTotales);
		
		tablaTemporadas.setItems(ListasObservables.listaTemporadas(serializable.getTemporadas()));
		
		//Se asigna a las celdas de las columnas los atributos de los que mostrará los valores.
		columnaTemporada.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("id"));
		columnaCapT.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosTotales"));
		columnaCapV.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosVistos"));
		
		//Se añade un campo a las celdas de la columna donde poder modificar el valor.
		columnaCapT.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
		columnaCapV.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
		
		ComprobacionesCampos.listenerTextoTemporadasTextField(tempTotales);
		ComprobacionesCampos.listenerTextoTemporadasTextField(tempVistas);	
		
	}

	/**
	 * Controlador del evento del botón modificar.
	 */
	@FXML
	private void modificarElemento() {
		
		/**
		 * Se mostrará una ventana emergente que preguntará si se está seguro de modificar los elementos. Si el usuario
		 * acepta, se procedera a guardar las modificaciones.
		 */
		if(Alertas.alertaEleccion(MensajesAlertas.T_CONFIRM_MODIF, MensajesAlertas.M_CONFIRM_MODIF)) {
			
			//Se llama al método para obtener todos los valores de los campos y almacenarlos.
			PiezaAudiovisual pieza = obtenerElementos();
			
			/**
			 * Se intentará introducir los datos obtenidos al XML que los almacena, se controlará si ocurre algún error
			 * en el proceso, si este ocurriera aparecería una ventana emergente avisando al usuario.
			 * Si se introducen los datos con exito, se actualizaran los elementos en la ventana de Consultas y se 
			 * mostrará una ventana emergente avisando al usuario que los cambios se realizaron con exitos.
			 */
			try {
				
				FuncionesApoyoControladores.verificacionCampos(pieza);
				RegistroDatosXML.introducirDatosPieza(pieza, EnvioDatos.getInstance().getTipoTransferencia());
				Modificaciones.actualizarConsultasModificado(pieza);
				Alertas.alertaInformativa(MensajesAlertas.T_MODIFICADO,MensajesAlertas.M_MODIFICADO);
				
				//Obtiene la ventana y se cierra.
				Stage stage = (Stage) modificar.getScene().getWindow();
				
				stage.close();
			} catch (TransformerException e) {
				
				Alertas.alertaError(MensajesAlertas.T_ERROR_GUARDAR_DATOS, MensajesAlertas.M_ERROR_GUARDAR_DATOS + e.getMessage());

			} catch (CampoInvalidoException e) {
				
				Alertas.alertaError(MensajesAlertas.T_ERROR_CAMPO, e.getMessage());
				
			}
		}
	
		
	}

	
	/**
	 * Controlador del ComboBox de Estados.
	 */
	@FXML
	private void seleccionEstado() {
		
		//Realiza las comprobaciones pertinentes a través del método.
		Modificaciones.comprobarEstado(estado.getSelectionModel().getSelectedItem(), tempVistas, tempTotales, tablaTemporadas, columnaCapV);
	
	}

	/**
	 * Controlador de evento del TextField de las Temporadas Totales.
	 */
	@FXML
	private void introduccionTempT() {
		
		//Comprueba que el campo no se encuentre vacío.
		if(!tempTotales.getText().isEmpty()) {
			
			//Se hacen las comprobaciones pertinentes a través del método.
			try {
				
				FuncionesApoyoControladores.introduccionTempT(tempTotales, tempVistas, tablaTemporadas,
						estado.getSelectionModel().getSelectedItem());
				
			} catch (CampoInvalidoException e) {

				Alertas.alertaError(MensajesAlertas.T_ERROR_CAMPO, e.getMessage());
				
			}
			
		}

	}
	
	
	/**
	 * Controlador para quitar el foco de los campos al pulsar intro.
	 * @param e
	 */
	@FXML
	private void quitarFocoCampos(ActionEvent e) {
		
		//Almacena el objeto que ha activado el evento.
		Object obj = e.getSource();
		
		//Comprueba si el objeto es el Textfield de Temporada totales
		if(obj == tempTotales) {
			//Si el campo está vacío quitará el foco, si no avisará al usuario.
			if(!tempTotales.getText().isEmpty()) {
				
				quitarFocoModificacion();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TT);
				
			}
			
		//Comprueba si el objeto es el Textfield de Temporada Vistas	
		}else if (obj == tempVistas) {
			
			//Si el campo está vacío quitará el foco, si no avisará al usuario.
			if(!tempVistas.getText().isEmpty()) {
				
				quitarFocoModificacion();
				
			}else {
				
				Alertas.alertaError(MensajesAlertas.T_QUITAR_F_C, MensajesAlertas.M_QUITAR_F_C_TV);
				
			}
			
		//Comprueba si el objeto es el Textfield de Titulo	
		}else if (obj == titulo) {
			
			//Si no está vacío quitará el foco.
			if(!titulo.getText().isEmpty()) {
				
				quitarFocoModificacion();
				
			}
			
		}
	}

	
	
	/**
	 * Controlador de evento del TextField de las Temporadas Vistas.
	 */
	@FXML
	private void introduccionTempV() {
		
		//Se comprueba que el campo no esté vacío.
		if(!tempVistas.getText().isEmpty()) {
			
			//Se llama al método para que realice las actualizaciones necesarias en los campos a partir del valor introducido.
			try {
				
				FuncionesApoyoControladores.introduccionTempV(tempVistas, tempTotales, tablaTemporadas,estado);
				
			} catch (CampoInvalidoException e) {
				
				Alertas.alertaError(MensajesAlertas.T_ERROR_CAMPO, e.getMessage());
				
			}
			
		}

	}
	


	/**
	 * Controlador de la modificación de las celdas de la tabla de Capitulos Totales.
	 * @param event
	 */
	@FXML
	private void modificarCapT(CellEditEvent<Temporada, Integer> event) {
		
		/*
		 * Se llama al método para que realice las actualizaciones necesarias en los campos a partir del valor introducido.
		 * Si la información introducida es válida quitara el foco del campo.
		 */
		
		try {
			
			FuncionesApoyoControladores.modificarCapT(event, tablaTemporadas, tempVistas, estado.getSelectionModel().getSelectedItem());
			quitarFocoModificacion();
			
		} catch (CampoInvalidoException e) {
			
			Alertas.alertaError(MensajesAlertas.T_ERROR_CAMPO, e.getMessage());
			
		}


		
		
	}

	/**
	 * Controlador de la modificaci�n de las celdas de la tabla de Capitulos Vistas.
	 * @param event
	 */
	@FXML
	private void modificarCapV(CellEditEvent<Temporada, Integer> event) {
	
		/*
		 * Se llama al método para que realice las actualizaciones necesarias en los campos a partir del valor introducido.
		 * Si el valor introducido no es válido, se mostrará una ventana al usuario.
		 */
		try {
			
			FuncionesApoyoControladores.modificarCapV(event, tablaTemporadas, tempVistas, tempTotales, estado);
			
			//Se actualizan los capitulos vistos con la misma cantidad que los capitulos totales.
			FuncionesApoyoControladores.rellenarCapV(tablaTemporadas, tempVistas, estado.getSelectionModel().getSelectedItem());
			
			quitarFocoModificacion();
			
		} catch (CampoInvalidoException e) {
			
			Alertas.alertaError(MensajesAlertas.T_ERROR_CAMPO, e.getMessage());
			
		}

		
		
	}

	
	/**
	 * Controlador para quitar el foco de los elementos en la ventana de modificaciones.
	 */
	@FXML
	private void quitarFocoModificacion() {
		
		
		FuncionesApoyoControladores.quitarFoco(raiz);
		
	}

	
	/**
	 * Método encargado de obtener los valores de los campos y almacenarlos en un objeto de {@link PiezaAudiovisual}
	 * @return Devuelve el objeto en el que se almacenaron los valores.
	 */
	private PiezaAudiovisual obtenerElementos() {

		//Se guarda en una variable los datos base a los que se les hicieron las modificaciones.
		PiezaAudiovisual pieza = EnvioDatos.getInstance().getDatosTransferencia();
		//Se crea un objeto que almacenará los nuevos valores ya modificados.
		PiezaAudiovisual nuevaPieza = null;

		//Se obtiene los valores de los campos y se almacenan en variables.
		
		int id = Integer.parseInt(this.id.getText());
		String titulo = this.titulo.getText().toUpperCase();
		Estados estado = this.estado.getSelectionModel().getSelectedItem();
		String sinopsis = this.sinopsis.getText();

		//Se comprueba si es un tipo serializable el elemento a modificar.
		if (pieza instanceof Serializable) {
			int tempTotales = Integer.parseInt(this.tempTotales.getText());
			int tempVistas = Integer.parseInt(this.tempVistas.getText());
			ArrayList<Temporada> temporadas = new ArrayList<Temporada>();
			temporadas.addAll(tablaTemporadas.getItems());

			/*
			 * Comprueba de que clase es el elemento a modificar, para crear una instancia de esta y almacenar
			 * los datos modificados.
			 */
			if (pieza instanceof Anime) {
				nuevaPieza = new Anime(id, titulo, sinopsis, estado, tempTotales, tempVistas, temporadas);
			} else {
				nuevaPieza = new Serie(id, titulo, sinopsis, estado, tempTotales, tempVistas, temporadas);
			}
			
		} else {
			/*
			 * Comprueba de que clase es el elemento a modificar, para crear una instancia de esta y almacenar
			 * los datos modificados.
			 */
			if (pieza instanceof PeliculaAnime) {
				nuevaPieza = new PeliculaAnime(id, titulo, sinopsis, estado);
			} else {
				nuevaPieza = new Pelicula(id, titulo, sinopsis, estado);
			}
		}
		
		return nuevaPieza;
		
	}

	
	
}
