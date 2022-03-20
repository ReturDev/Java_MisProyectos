package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.transform.TransformerException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.alertas.Alertas;
import modelo.clases.*;
import modelo.converters.*;
import modelo.enums.Estados;
import modelo.envioDatos.EnvioDatos;
import modelo.funciones.*;
import modelo.funcionesXML.RegistroDatosXML;
import modelo.listas.ListasObservables;
import modelo.textoAlertas.MensajesAlertas;

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

	/*
	 * TODO AÑADIR UN EVENTO AL QUITAR EL FOCO DE TITULO SI ESTE SE DEJA VACIO.
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Se obtiene los datos de la ventana principal de los datos que se quieren modificar.
		PiezaAudiovisual pieza = EnvioDatos.getInstance().getDatosTransferencia();

		//Se le da los valores a mostrar al ComboBox.
		estado.setItems(ListasObservables.listaEstados());
		estado.setConverter(new EstadosConverter());
		
		//Se comprueba si el elemento que se ha pasado no sea serializable para eliminar el estado Siguiendo.
		if(!ComprobacionesCampos.tipoSerializable(EnvioDatos.getInstance().getTipoTransferencia())) {
			estado.getItems().remove(Estados.SIGUIENDO);
		}

		/*
		 * Se da los valores a cada elemento de la interfaz con los datos del elemento a modificar para mostrar
		 * al usuario.
		 */
		titulo.setText(pieza.getTitulo());
		id.setText(pieza.getId() + "");
		estado.getSelectionModel().select(pieza.getEstado());
		sinopsis.setText(pieza.getSinopsis());

		
		//Comprueba que el elemento es Serializable para rellenar los campos, en caso de no serlo se bloquearán los campos oportunos.
		if (pieza instanceof Serializable) {

			Serializable serializable = (Serializable) pieza;
			
			
			tempVistas.setText(serializable.getTemporadasVistas() + "");
			
			//Añadimos un evento para cuando el campo Temporadas Vistas pierda el foco.
			tempVistas.focusedProperty().addListener(new ChangeListener<Boolean>() {
				
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
					//Comprobamos si antes tenia el foco y ahora lo ha perdido.
					if(oldProperty && !newProperty) {
						//Si el campo una vez pierda el foco está vacáo, se le dara un valor por defecto de 0.
						if(tempVistas.getText().isEmpty()) {
							tempVistas.setText("0");
						}
					}
					
				}
			});
			tempTotales.setText(serializable.getTemporadasTotales() + "");
			
			//Añadimos un evento cuando el campo Temporadas Totales pierda el foco.
			tempTotales.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldProperty, Boolean newProperty) {
					//Comprobamos si antes tenia el foco y ahora lo ha perdido.
					if(oldProperty&& !newProperty) {
						//Si el campo una vez pierda el foco está vacío, se le dara un valor por defecto de 0.
						if(tempTotales.getText().isEmpty()) {
							tempTotales.setText("1");
						}
					}
					
				}
			});
			
			tablaTemporadas.setItems(ListasObservables.listaTemporadas(serializable.getTemporadas()));
			
			columnaTemporada.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("id"));
			columnaCapT.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosTotales"));
			columnaCapV.setCellValueFactory(new PropertyValueFactory<Temporada, Integer>("capitulosVistos"));
			
			columnaCapT.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
			columnaCapV.setCellFactory(TextFieldTableCell.forTableColumn(new CapitulosConverter()));
			
			ComprobacionesCampos.temporadasTextField(tempTotales);
			ComprobacionesCampos.temporadasTextField(tempVistas);

			
		} else {

			//Se bloquean los campos que pertenecen a serializables.
			tempTotales.setDisable(true);
			tempVistas.setDisable(true);
			tablaTemporadas.setDisable(true);
		}

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
			
			
			
			//Comprueba llamando al método que los datos almacenados son válidos.
			if(FuncionesApoyoControladores.verificacionCampos(pieza)) {
				
				/**
				 * Se intentará introducir los datos obtenidos al XML que los almacena, se controlará si ocurre algún error
				 * en el proceso, si este ocurriera aparecería una ventana emergente avisando al usuario.
				 * Si se introducen los datos con exito, se actualizaran los elementos en la ventana de Consultas y se 
				 * mostrará una ventana emergente avisando al usuario que los cambios se realizaron con exitos.
				 */
				try {
					RegistroDatosXML.introducirDatosPieza(pieza, EnvioDatos.getInstance().getTipoTransferencia());
					Modificaciones.actualizarConsultasModificado(pieza);
					Alertas.alertaInformativa(MensajesAlertas.T_MODIFICADO,MensajesAlertas.M_MODIFICADO);
					
				} catch (TransformerException e) {
					Alertas.alertaError(MensajesAlertas.T_ERROR_GUARDAR_DATOS, MensajesAlertas.M_ERROR_GUARDAR_DATOS + e.getMessage());
				}
		
				
				
				
				
				//Obtiene la ventana y se cierra.
				Stage stage = (Stage) modificar.getScene().getWindow();
				
				stage.close();
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
			FuncionesApoyoControladores.introduccionTempT(tempTotales, tempVistas, tablaTemporadas,
					estado.getSelectionModel().getSelectedItem());
			
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
			FuncionesApoyoControladores.introduccionTempV(tempVistas, tempTotales, tablaTemporadas,estado);
			
		}

	}
	


	/**
	 * Controlador de la modificación de las celdas de la tabla de Capitulos Totales.
	 * @param e
	 */
	@FXML
	private void modificarCapT(CellEditEvent<Temporada, Integer> e) {
		
		/*
		 * Se llama al método para que realice las actualizaciones necesarias en los campos a partir del valor introducido.
		 * Devolverá un booleano dependiendo de si la información introducida es válida. Si lo es quitara el foco del campo.
		 */
		boolean valorValido = FuncionesApoyoControladores.modificarCapT(e, tablaTemporadas, tempVistas, estado.getSelectionModel().getSelectedItem());

		if (valorValido) {

			quitarFocoModificacion();

		}
		
		
	}

	/**
	 * Controlador de la modificaci�n de las celdas de la tabla de Capitulos Vistas.
	 * @param e
	 */
	@FXML
	private void modificarCapV(CellEditEvent<Temporada, Integer> e) {
	
		/*
		 * Se llama al método para que realice las actualizaciones necesarias en los campos a partir del valor introducido.
		 * Devolverá un booleano dependiendo de si la información introducida es válida. Si lo es quitara el foco del campo.
		 */
		boolean valorValido = FuncionesApoyoControladores.modificarCapV(e, tablaTemporadas,tempVistas,estado);

		if (valorValido) {
	
			//Se actualizan los capitulos vistos con la misma cantidad que los capitulos totales.
			FuncionesApoyoControladores.rellenarCapV(tablaTemporadas, tempVistas, estado.getSelectionModel().getSelectedItem());
			
			quitarFocoModificacion();
	
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
		String titulo = this.titulo.getText();
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
